package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.School;
import com.md.excel.SchoolExcel;
import com.md.vo.SchoolVO;

import java.util.List;

public interface SchoolService {
    List<SchoolVO> list();

    //查询--单条
    School select(Long id);

    //查询--条件分页
    PageInfo<School> page(SchoolPageDTO dto);

    //添加--单条记录
    int insert(SchoolInsertDTO dto);

    //删除--单条记录
    int delete(Long id);
    //删除--多条
    int deleteBatch(List<Long> ids);

    //修改
    int update(SchoolUpdateDTO dto);

    List<SchoolExcel> getExcelData();
}
