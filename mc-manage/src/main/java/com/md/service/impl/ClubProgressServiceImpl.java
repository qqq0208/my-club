package com.md.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.dto.ClubProgressInsertDTO;
import com.md.dto.ClubProgressPageDTO;
import com.md.dto.ClubProgressUpdateDTO;
import com.md.entity.ClubProgress;
import com.md.excel.ClubProgressExcel;
import com.md.exception.IllegalParamException;
import com.md.exception.ServerErrorException;
import com.md.mapper.ClubProgressMapper;
import com.md.service.ClubProgressService;
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
public class ClubProgressServiceImpl implements ClubProgressService {

    @Autowired
    private ClubProgressMapper clubProgressMapper;


    @Override
    public int insert(ClubProgressInsertDTO dto) {
        String info = dto.getInfo();
        // 拷贝属性
        ClubProgress clubProgress = BeanUtil.copyProperties(dto, ClubProgress.class);
        // 设置默认值
        clubProgress.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        clubProgress.setVersion(0L);
        clubProgress.setDeleted(0);
        clubProgress.setCreated(LocalDateTime.now());
        clubProgress.setUpdated(LocalDateTime.now());
        // DB添加
        int result = clubProgressMapper.insert(clubProgress);
        if (result <= 0) {
            throw new ServerErrorException("DB添加失败");
        }
        return result;
    }


    @Override
    public ClubProgress select(Long id) {
        ClubProgress result = clubProgressMapper.select(id);
        if (ObjectUtil.isNull(result)) {
            throw new ServerErrorException("记录不存在");
        }
        return result;
    }


    @Override
    public PageInfo<ClubProgress> page(ClubProgressPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(clubProgressMapper.list(dto));
    }


    @Transactional
    @Override
    public int update(ClubProgressUpdateDTO dto) {
        ClubProgress clubProgress = clubProgressMapper.select(dto.getId());
        if (ObjectUtil.isNull(clubProgress)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, clubProgress);
        // 设置默认值
        clubProgress.setUpdated(LocalDateTime.now());
        // DB修改
        int result = clubProgressMapper.update(clubProgress);
        if (result <= 0) {
            throw new ServerErrorException("DB修改失败");
        }
        return result;
    }


    @Override
    public int delete(Long id) {
        int result = clubProgressMapper.delete(id);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑删除失败");
        }
        return result;
    }


    @Override
    public int deleteBatch(List<Long> ids) {
        int result = clubProgressMapper.deleteBatch(ids);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑批删失败");
        }
        return result;
    }

    @Override
    public List<ClubProgressExcel> getExcelData() {
        return clubProgressMapper.list(new ClubProgressPageDTO())
                .stream()
                .map(clubProgress -> {
                    ClubProgressExcel clubProgressExcel =
                            BeanUtil.copyProperties(clubProgress, ClubProgressExcel.class);
                    if (ObjectUtil.isNotNull(clubProgress.getClub())) {
                        clubProgressExcel.setClubTitle(clubProgress.getClub().getTitle());
                    }
                    if (ObjectUtil.isNotNull(clubProgress.getRoom())) {
                        clubProgressExcel.setRoomTitle(clubProgress.getRoom().getTitle());
                    }
                    if (ObjectUtil.isNotNull(clubProgress.getCourse())) {
                        clubProgressExcel.setCourseTitle(clubProgress.getCourse().getTitle());
                    }
                    if (ObjectUtil.isNotNull(clubProgress.getManager())) {
                        clubProgressExcel.setManagerName(clubProgress.getManager().getRealname());
                    }
                    if (ObjectUtil.isNotNull(clubProgress.getAssistant())) {
                        clubProgressExcel.setAssistantName(clubProgress.getAssistant().getRealname());
                    }
                    if (ObjectUtil.isNotNull(clubProgress.getTeacher())) {
                        clubProgressExcel.setTeacherName(clubProgress.getTeacher().getRealname());
                    }
                    return clubProgressExcel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public int endClubProgress(Long id) {
        // 查询班级进度记录
        ClubProgress clubProgress = clubProgressMapper.select(id);
        if (ObjectUtil.isNull(clubProgress)) {
            throw new ServerErrorException("班级进度记录不存在");
        }
// 判断是否已结束
        if (ObjectUtil.isNotNull(clubProgress.getEndTime())) {
            throw new IllegalParamException("班级进度已结束");
        }
// DB修改：修改班级进度记录
        clubProgress.setEndTime(LocalDateTime.now());
        int result = clubProgressMapper.update(clubProgress);
        if (result <= 0) {
            throw new ServerErrorException("DB班级进度记录修改失败");
        }
        return result;
    }
}