package com.md.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.School;
import com.md.excel.SchoolExcel;
import com.md.exception.ServerErrorException;
import com.md.mapper.SchoolMapper;
import com.md.service.SchoolService;
import com.md.vo.SchoolVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: CM
 * @Description TODO
 */
@Service
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    private SchoolMapper schoolMapper;


    @Override
    public List<SchoolVO> list() {
        return schoolMapper.list();
    }

    @Override
    public School select(Long id) {
        return schoolMapper.select(id);
    }

    @Override
    public PageInfo<School> page(SchoolPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        List<School> list = schoolMapper.page(dto);
        return new PageInfo<>(list);
    }

    @Override
    public int insert(SchoolInsertDTO dto) {
        School school = BeanUtil.copyProperties(dto, School.class);
        school.setVersion(0L);
        school.setDeleted(0);
        school.setCreated(LocalDateTime.now());
        school.setUpdated(LocalDateTime.now());
        return schoolMapper.insert(school);
    }

    @Override
    public int delete(Long id) {
        return schoolMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return schoolMapper.deleteBatch(ids);
    }

    @Override
    public int update(SchoolUpdateDTO dto) {
        School school = schoolMapper.select(dto.getId());
        if (ObjectUtil.isNull(school)){
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto,school);
        school.setUpdated(LocalDateTime.now());
        return schoolMapper.update(school);
    }

    @Override
    public List<SchoolExcel> getExcelData() {
        List<School> page = schoolMapper.page(new SchoolPageDTO());
        List<SchoolExcel> collect = page.stream().map(room -> BeanUtil.copyProperties(room, SchoolExcel.class)).collect(Collectors.toList());

        return collect;
    }


}