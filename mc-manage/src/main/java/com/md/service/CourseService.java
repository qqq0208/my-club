package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.Course;
import com.md.entity.Emp;
import com.md.excel.CourseExcel;
import com.md.excel.EmpExcel;
import com.md.vo.CourseVO;
import com.md.vo.EmpVO;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
public interface CourseService {
    int insert(CourseInsertDTO dto);
    Course select(Long id);
    List<CourseVO> list();
    PageInfo<Course> page(CoursePageDTO dto);
    int update(CourseUpdateDTO dto);
    int delete(Long id);
    int deleteBatch(List<Long> ids);
    /**
     * 获取课程记录的Excel数据
     *
     * @return 课程记录的Excel数据列表
     */
    List<CourseExcel> getExcelData();
}