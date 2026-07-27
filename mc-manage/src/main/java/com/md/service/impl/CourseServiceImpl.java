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
import com.md.entity.Course;
import com.md.entity.Emp;
import com.md.excel.CourseExcel;
import com.md.excel.EmpExcel;
import com.md.exception.ServerErrorException;
import com.md.mapper.CourseMapper;
import com.md.service.CourseService;
import com.md.vo.CourseVO;
import com.md.vo.DeptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: CM
 * @Description TODO
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;




    @Override
    public int insert(CourseInsertDTO dto) {
        String info = dto.getInfo();
        // 拷贝属性
        Course course = BeanUtil.copyProperties(dto, Course.class);
        // 设置默认值
        course.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        course.setVersion(0L);
        course.setDeleted(0);
        course.setCreated(LocalDateTime.now());
        course.setUpdated(LocalDateTime.now());
        // DB添加
        int result = courseMapper.insert(course);
        if (result <= 0) {
            throw new ServerErrorException("DB添加失败");
        }
        return result;
    }


    @Override
    public Course select(Long id) {
        Course result = courseMapper.select(id);
        if (ObjectUtil.isNull(result)) {
            throw new ServerErrorException("记录不存在");
        }
        return result;
    }


    @Override
    public List<CourseVO> list() {
        return courseMapper.list(new CoursePageDTO())
                .stream()
                .map(course -> BeanUtil.copyProperties(course, CourseVO.class))
                .collect(Collectors.toList());
    }


    @Override
    public PageInfo<Course> page(CoursePageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(courseMapper.list(dto));
    }


    @Transactional
    @Override
    public int update(CourseUpdateDTO dto) {
        Course course = courseMapper.select(dto.getId());
        if (ObjectUtil.isNull(course)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, course);
        // 设置默认值
        course.setUpdated(LocalDateTime.now());
        // DB修改
        int result = courseMapper.update(course);
        if (result <= 0) {
            throw new ServerErrorException("DB修改失败");
        }
        return result;
    }


    @Override
    public int delete(Long id) {
        int result = courseMapper.delete(id);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑删除失败");
        }
        return result;
    }


    @Override
    public int deleteBatch(List<Long> ids) {
        int result = courseMapper.deleteBatch(ids);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑批删失败");
        }
        return result;
    }

    @Override
    public List<CourseExcel> getExcelData() {
        return courseMapper.list(new CoursePageDTO())
                .stream()
                .map(course -> BeanUtil.copyProperties(course, CourseExcel.class))
                .collect(Collectors.toList());
    }
}