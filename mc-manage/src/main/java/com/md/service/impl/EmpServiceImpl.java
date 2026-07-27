package com.md.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.constant.MC;
import com.md.dto.*;
import com.md.entity.Dept;
import com.md.entity.Emp;
import com.md.excel.DeptExcel;
import com.md.excel.EmpExcel;
import com.md.exception.IllegalParamException;
import com.md.exception.ServerErrorException;
import com.md.mapper.EmpMapper;
import com.md.service.EmpService;
import com.md.util.JwtUtil;
import com.md.util.MinioUtil;
import com.md.vo.EmpVO;
import com.md.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: CM
 * @Description TODO
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<EmpVO> list() {
        return empMapper.list(new EmpPageDTO())
                .stream()
                .map(emp -> BeanUtil.copyProperties(emp, EmpVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo<Emp> page(EmpPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(empMapper.list(dto));
    }

    @Override
    public Emp select(Long id) {
        return empMapper.select(id);
    }

    @Override
    public int insert(EmpInsertDTO dto) {
        String info = dto.getInfo();
        String idcard = dto.getIdcard();
        // 拷贝属性
        Emp emp = BeanUtil.copyProperties(dto, Emp.class);
        // 密码加密
        emp.setPassword(SecureUtil.md5(emp.getPassword()));
        // 设置默认值
        emp.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        emp.setAvatar(MC.Emp.DEFAULT_AVATAR);
        emp.setGender(IdcardUtil.getGenderByIdCard(idcard));
        emp.setAge(IdcardUtil.getAgeByIdCard(idcard));
        emp.setProvince(IdcardUtil.getProvinceByIdCard(idcard));
        emp.setAddress("暂未添加详细住址");
        emp.setVersion(0L);
        emp.setDeleted(0);
        emp.setCreated(LocalDateTime.now());
        emp.setUpdated(LocalDateTime.now());
        // DB添加
        int result = empMapper.insert(emp);
        if (result <= 0) {
            throw new ServerErrorException("DB添加失败");
        }
        return result;
    }

    @Override
    public int update(EmpUpdateDTO dto) {
        Emp emp = empMapper.select(dto.getId());
        if (ObjectUtil.isNull(emp)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, emp);
        // 设置默认值
        emp.setUpdated(LocalDateTime.now());
        // DB修改
        int result = empMapper.update(emp);
        if (result <= 0) {
            throw new ServerErrorException("DB修改失败");
        }
        return result;
    }

    @Override
    public int delete(Long id) {
        return empMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return empMapper.deleteBatch(ids);
    }

    @Override
    public List<EmpExcel> getExcelData() {
        return empMapper.list(new EmpPageDTO())
                .stream()
                .map(emp -> {
                    EmpExcel empExcel = BeanUtil.copyProperties(emp, EmpExcel.class);
                    if (ObjectUtil.isNotNull(emp.getDept())) {
                        empExcel.setDeptName(emp.getDept().getTitle());
                    }
                    return empExcel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String uploadAvatar(MultipartFile newFile, Long id) {
        // 按主键查询记录
        Emp emp = empMapper.select(id);
        if (ObjectUtil.isNull(emp)) {
            throw new ServerErrorException("记录不存在");
        }
        // 备份旧文件
        String oldFile = emp.getAvatar();
        // 生成新文件名
        String newFileName = MinioUtil.randomFilename(newFile);
        // DB更新文件名
        emp.setAvatar(newFileName);
        if (empMapper.update(emp) <= 0) {
            throw new ServerErrorException("DB更新失败");
        }
        try {
            // MinIO删除旧文件（默认文件不删除）
            if (!MC.Emp.DEFAULT_AVATAR.equals(oldFile)) {
                MinioUtil.delete(oldFile, MC.MinIO.AVATAR_DIR, MC.MinIO.BUCKET_NAME);
            }
            // MinIO上传新文件
            MinioUtil.upload(newFile, newFileName, MC.MinIO.AVATAR_DIR, MC.MinIO.BUCKET_NAME);
        } catch (Exception e) {
            throw new ServerErrorException("MinIO操作失败：" + e.getMessage());
        }
        // 返回新文件名
        return newFileName;
    }

    @Override
    public int updatePassword(EmpUpdatePasswordDTO dto) {
        String newPassword = dto.getNewPassword();
        String oldPassword = dto.getOldPassword();

        // 判断记录
        Emp emp = empMapper.select(dto.getId());
        if (ObjectUtil.isNull(emp)) {
            throw new ServerErrorException("记录不存在");
        }
        // 判断密码
        if (!oldPassword.equals(emp.getPassword())) {
            throw new IllegalParamException("原密码有误");
        }
        // 更新密码
        int i = empMapper.updatePassword(dto);
        if (i <= 0) {
            throw new ServerErrorException("修改密码失败");
        }
        return i;
    }

    @Override
    public LoginVO loginBuAccount(LoginDTO dto) {
        //密码加密
        String password = SecureUtil.md5(dto.getPassword());
        //设置密码
        dto.setPassword(password);
        Emp emp = empMapper.selectByAccount(dto);
        //非空判断
        if (ObjectUtil.isNull(emp)){
            throw new IllegalParamException("账号或密码错误");
        }
        //生成token令牌
        String token = JwtUtil.build(emp.getId(), emp.getRealname(), emp.getAvatar());
        LoginVO loginVO=new LoginVO();
        loginVO.setToken(token);
        loginVO.setEmp(emp);
        return loginVO;
    }
}