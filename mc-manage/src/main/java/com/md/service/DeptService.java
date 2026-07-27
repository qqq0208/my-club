package com.md.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.Dept;
import com.md.excel.DeptExcel;
import com.md.excel.RoomExcel;
import com.md.vo.DeptVO;
import io.swagger.v3.oas.models.links.Link;

import java.util.List;

public interface DeptService {
    //查詢--全部
    List<DeptVO> list();

    //查詢--分頁條件
    PageInfo<Dept> page(DeptPageDTO dto);

    //查询--单条
    Dept select(Long id);

    //添加--单条
    int insert(DeptInsertDTO dto);

    //修改--单条
    int update(DeptUpdateDTO dto);

    //删除--单条
    int delete(Long id);

    //删除--多条
    int deleteBatch(List<Long> ids);

    //查询--获取Excel
    List<DeptExcel> getExcelData();
}
