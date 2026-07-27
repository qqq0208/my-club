package com.md.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.constant.MC;
import com.md.dto.StudentInsertDTO;
import com.md.dto.StudentPageDTO;
import com.md.dto.StudentUpdateDTO;
import com.md.entity.School;
import com.md.entity.Student;
import com.md.excel.StudentExcel;
import com.md.exception.ServerErrorException;
import com.md.mapper.StudentMapper;
import com.md.service.StudentService;
import com.md.util.MinioUtil;
import com.md.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: CM
 * @Description TODO
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public int insert(StudentInsertDTO dto) {
        String info = dto.getInfo();
        String idcard = dto.getIdcard();
        // 拷贝属性
        Student student = BeanUtil.copyProperties(dto, Student.class);
        // 设置默认值：MC前缀 + 当前年份 + 全局编号
        student.setNumber("MC" + LocalDateTime.now().getYear() + generateStudentNumber());
        student.setAvatar(MC.Student.DEFAULT_AVATAR);
        student.setGender(IdcardUtil.getGenderByIdCard(idcard));
        student.setAge(IdcardUtil.getAgeByIdCard(idcard));
        student.setProvince(IdcardUtil.getProvinceByIdCard(idcard));
        student.setAddress("暂未添加现居住地");
        student.setStatus(MC.Student.STUDYING);
        student.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        student.setVersion(0L);
        student.setDeleted(0);
        student.setCreated(LocalDateTime.now());
        student.setUpdated(LocalDateTime.now());
        // DB添加
        int result = studentMapper.insert(student);
        if (result <= 0) {
            throw new ServerErrorException("DB添加失败");
        }
        return result;
    }

    @Override
    public Student select(Long id) {
        Student result = studentMapper.select(id);
        if (ObjectUtil.isNull(result)) {
            throw new ServerErrorException("记录不存在");
        }
        return result;
    }


    @Override
    public List<StudentVO> list() {
        return studentMapper.list(new StudentPageDTO())
                .stream()
                .map(student -> BeanUtil.copyProperties(student, StudentVO.class))
                .collect(Collectors.toList());
    }


    @Override
    public PageInfo<Student> page(StudentPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(studentMapper.list(dto));
    }


    @Transactional
    @Override
    public int update(StudentUpdateDTO dto) {
        Student student = studentMapper.select(dto.getId());
        if (ObjectUtil.isNull(student)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, student);
        // 设置默认值
        student.setUpdated(LocalDateTime.now());
        // DB修改
        int result = studentMapper.update(student);
        if (result <= 0) {
            throw new ServerErrorException("DB修改失败");
        }
        return result;
    }


    @Override
    public int delete(Long id) {
        int result = studentMapper.delete(id);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑删除失败");
        }
        return result;
    }


    @Override
    public int deleteBatch(List<Long> ids) {
        int result = studentMapper.deleteBatch(ids);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑批删失败");
        }
        return result;
    }

    @Override
    public List<StudentExcel> getExcelData() {
        return studentMapper.list(new StudentPageDTO())
                .stream()
                .map(student -> {
                    StudentExcel studentExcel = BeanUtil.copyProperties(student,
                            StudentExcel.class);
                    School school = student.getSchool();
                    if (ObjectUtil.isNotNull(school)) {
                        studentExcel.setSchoolTitle(school.getTitle() + school.getCollege() +
                                school.getMajor() + school.getClazz());
                    }
                    if (ObjectUtil.isNotNull(student.getSalesman())) {
                        studentExcel.setSalesmanName(student.getSalesman().getRealname());
                    }
                    if (ObjectUtil.isNotNull(student.getClub())) {
                        studentExcel.setClubTitle(student.getClub().getTitle());
                    }
                    studentExcel.setGender(MC.Student.genderFormat(student.getGender()));
                    studentExcel.setStatus(MC.Student.statusFormat(student.getStatus()));
                    return studentExcel;
                })
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = RuntimeException.class)

    @Override
    public String uploadAvatar(MultipartFile newFile, Long id) {
        // 按主键查询记录
        Student student = studentMapper.select(id);
        if (ObjectUtil.isNull(student)) {
            throw new ServerErrorException("记录不存在");
        }
// 备份旧文件
        String oldFile = student.getAvatar();
// 生成新文件名
        String newFileName = MinioUtil.randomFilename(newFile);
// DB更新文件名
        student.setAvatar(newFileName);
        if (studentMapper.update(student) <= 0) {
            throw new ServerErrorException("DB更新失败");
        }
        try {
// MinIO删除旧文件（默认文件不删除）
            if (!MC.Student.DEFAULT_AVATAR.equals(oldFile)) {
                MinioUtil.delete(oldFile, MC.MinIO.AVATAR_DIR, MC.MinIO.BUCKET_NAME);
            }
// MinIO上传新文件
            MinioUtil.upload(newFile, newFileName, MC.MinIO.AVATAR_DIR,
                    MC.MinIO.BUCKET_NAME);
        } catch (Exception e) {
            throw new ServerErrorException("MinIO操作失败：" + e.getMessage());
        }
// 返回新文件名
        return newFileName;
    }

    /**
     * 生成学生编号
     *
     * @return 学生编号
     */
    private String generateStudentNumber() {
        final String KEY = "student:globalNumber";
        // 自增
        Long increment = stringRedisTemplate.opsForValue().increment(KEY);
        if (ObjectUtil.isNull(increment)) {
            throw new ServerErrorException("Redis自增操作失败");
        }
        // 自增后的值直接使用（保持与原逻辑一致：首次生成00000，之后递增）
        return String.format("%05d", increment);
    }
}