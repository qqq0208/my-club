package com.md.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.dto.DeptInsertDTO;
import com.md.dto.DeptPageDTO;
import com.md.dto.DeptUpdateDTO;
import com.md.dto.RoomPageDto;
import com.md.entity.Dept;
import com.md.entity.Room;
import com.md.excel.DeptExcel;
import com.md.excel.RoomExcel;
import com.md.exception.ServerErrorException;
import com.md.mapper.DeptMapper;
import com.md.service.DeptService;
import com.md.vo.DeptVO;
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
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Override
    public List<DeptVO> list() {
        return deptMapper.list(new DeptPageDTO())
                .stream()
                .map(dept -> BeanUtil.copyProperties(dept,DeptVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo<Dept> page(DeptPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        return new PageInfo<>(deptMapper.list(dto));
    }

    @Override
    public Dept select(Long id) {
        return deptMapper.select(id);
    }

    @Override
    public int insert(DeptInsertDTO dto) {
        String info = dto.getInfo();
        // 拷贝属性
        Dept dept = BeanUtil.copyProperties(dto, Dept.class);
        // 设置默认值
        dept.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        dept.setVersion(0L);
        dept.setDeleted(0);
        dept.setCreated(LocalDateTime.now());
        dept.setUpdated(LocalDateTime.now());
        // DB添加
        int result = deptMapper.insert(dept);
        if (result <= 0) {
            throw new ServerErrorException("DB添加失败");
        }
        return result;
    }

    @Override
    public int update(DeptUpdateDTO dto) {
        Dept dept = deptMapper.select(dto.getId());
        if (ObjectUtil.isNull(dept)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, dept);
        // 设置默认值
        dept.setUpdated(LocalDateTime.now());
        // DB修改
        int result = deptMapper.update(dept);
        if (result <= 0) {
            throw new ServerErrorException("DB修改失败");
        }
        return result;
    }

    @Override
    public int delete(Long id) {
        return deptMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return deptMapper.deleteBatch(ids);
    }

    @Override
    public List<DeptExcel> getExcelData() {
        return deptMapper.list(new DeptPageDTO())
                .stream()
                .map(dept -> {
                    DeptExcel deptExcel = BeanUtil.copyProperties(dept, DeptExcel.class);
                    if (ObjectUtil.isNotNull(dept.getRoom())) {
                        deptExcel.setRoomTitle(dept.getRoom().getTitle());
                    }
                    return deptExcel;
                })
                .collect(Collectors.toList());
    }
}