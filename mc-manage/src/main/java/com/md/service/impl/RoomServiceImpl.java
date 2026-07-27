package com.md.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.dto.RoomInsertDTO;
import com.md.dto.RoomPageDto;
import com.md.dto.RoomUpdateDTO;
import com.md.entity.Room;
import com.md.excel.RoomExcel;
import com.md.exception.ServerErrorException;
import com.md.mapper.RoomMapper;
import com.md.service.RoomService;
import com.md.vo.RoomVo;
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
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;
    @Override
    public List<RoomVo> selectAllRoom() {
        return roomMapper.selectAllRoom();
    }

    @Override
    public Room select(Long id) {
        return roomMapper.select(id);
    }

    @Override
    public PageInfo<Room> page(RoomPageDto dto) {
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        List<Room> list = roomMapper.list(dto);
        return new PageInfo<>(list);
    }

    @Override
    public int insert(RoomInsertDTO dto) {
        Room room = BeanUtil.copyProperties(dto, Room.class);
        room.setVersion(0L);
        room.setDeleted(0);
        room.setCreated(LocalDateTime.now());
        room.setUpdated(LocalDateTime.now());
        return roomMapper.insert(room);
    }

    @Override
    public int delete(Long id) {
        return roomMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
       return roomMapper.deleteBatch(ids);
    }

    @Override
    public int update(RoomUpdateDTO dto) {
        Room room =roomMapper.select(dto.getId());
        if (ObjectUtil.isNull(room)){
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto,room);
        room.setUpdated(LocalDateTime.now());
        return roomMapper.update(room);
    }

    @Override
    public List<RoomExcel> getExcelData() {
        List<Room> list = roomMapper.list(new RoomPageDto());
        List<RoomExcel> collect = list.stream().map(room -> BeanUtil.copyProperties(room, RoomExcel.class)).collect(Collectors.toList());
        return collect;
    }
}