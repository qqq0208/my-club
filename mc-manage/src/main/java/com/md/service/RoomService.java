package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.RoomInsertDTO;
import com.md.dto.RoomPageDto;
import com.md.dto.RoomUpdateDTO;
import com.md.entity.Room;
import com.md.excel.RoomExcel;
import com.md.vo.RoomVo;


import java.util.List;

public interface RoomService {
    List<RoomVo> selectAllRoom();

    //查询-单条
    Room select(Long id);
    //查询--条件分页
    PageInfo<Room> page(RoomPageDto dto);


    //添加--单条
    int insert(RoomInsertDTO dto);

    int delete(Long id);

    //删除--多条
    int deleteBatch(List<Long> ids);

    //修改
    int update(RoomUpdateDTO dto);

    List<RoomExcel> getExcelData();
}
