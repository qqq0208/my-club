package com.md.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.constant.MC;
import com.md.dto.*;
import com.md.entity.Assets;
import com.md.entity.Club;
import com.md.entity.Direction;
import com.md.excel.AssetsExcel;
import com.md.excel.DirectionExcel;
import com.md.exception.ServerErrorException;

import com.md.mapper.DirectionMapper;
import com.md.service.DirectionService;
import com.md.util.MinioUtil;
import com.md.vo.ClubVO;
import com.md.vo.DirectionVO;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DirectionServiceImpl implements DirectionService {


    @Autowired
    private DirectionMapper directionMapper;


    @Override
    public int insert(DirectionInsertDTO dto) {
        String info = dto.getInfo();
        // 拷贝属性
        Direction direction = BeanUtil.copyProperties(dto, Direction.class);
        // 设置默认值
        direction.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        direction.setVersion(0L);
        direction.setDeleted(0);
        direction.setCreated(LocalDateTime.now());
        direction.setUpdated(LocalDateTime.now());
        // DB添加
        int result = directionMapper.insert(direction);
        if (result <= 0) {
            throw new ServerErrorException("DB添加失败");
        }
        return result;
    }


    @Override
    public Direction select(Long id) {
        Direction result = directionMapper.select(id);
        if (ObjectUtil.isNull(result)) {
            throw new ServerErrorException("记录不存在");
        }
        return result;
    }


    @Override
    public List<DirectionVO> list() {
        return directionMapper.list(new DirectionPageDTO())
                .stream()
                .map(direction -> BeanUtil.copyProperties(direction, DirectionVO.class))
                .collect(Collectors.toList());
    }


    @Override
    public PageInfo<Direction> page(DirectionPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(directionMapper.list(dto));
    }


    @Transactional
    @Override
    public int update(DirectionUpdateDTO dto) {
        Direction direction = directionMapper.select(dto.getId());
        if (ObjectUtil.isNull(direction)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, direction);
        // 设置默认值
        direction.setUpdated(LocalDateTime.now());
        // DB修改
        int result = directionMapper.update(direction);
        if (result <= 0) {
            throw new ServerErrorException("DB修改失败");
        }
        return result;
    }


    @Override
    public int delete(Long id) {
        int result = directionMapper.delete(id);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑删除失败");
        }
        return result;
    }


    @Override
    public int deleteBatch(List<Long> ids) {
        int result = directionMapper.deleteBatch(ids);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑批删失败");
        }
        return result;
    }

    @Override
    public List<DirectionExcel> getExcelData() {
        return directionMapper.list(new DirectionPageDTO())
                .stream()
                .map(dept -> BeanUtil.copyProperties(dept, DirectionExcel.class))
                .collect(Collectors.toList());
    }
}