package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.StudentInsertDTO;
import com.md.dto.StudentPageDTO;
import com.md.dto.StudentUpdateDTO;
import com.md.entity.Student;
import com.md.excel.StudentExcel;
import com.md.vo.StudentVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {
    int insert(StudentInsertDTO dto);
    Student select(Long id);
    List<StudentVO> list();
    PageInfo<Student> page(StudentPageDTO dto);
    int update(StudentUpdateDTO dto);
    int delete(Long id);
    int deleteBatch(List<Long> ids);
    /**
     * 导出学生记录的Excel数据
     *
     * @return 学生记录的Excel数据列表
     */
    List<StudentExcel> getExcelData();

    String uploadAvatar(MultipartFile newFile, Long id);

}
