package com.md.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.dto.ClubInsertDTO;
import com.md.dto.ClubPageDTO;
import com.md.dto.ClubUpdateDTO;
import com.md.entity.Club;
import com.md.excel.ClubExcel;
import com.md.exception.IllegalParamException;
import com.md.exception.ServerErrorException;
import com.md.mapper.ClubMapper;
import com.md.service.ClubService;
import com.md.vo.ClubVO;
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
public class ClubServiceImpl implements ClubService {
    @Autowired
    private ClubMapper clubMapper;


    @Override
    public int insert(ClubInsertDTO dto) {
        String info = dto.getInfo();
        List<String> tag = dto.getTag();
        // 拷贝属性
        Club club = BeanUtil.copyProperties(dto, Club.class);
        // 设置默认值
        club.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        club.setTag(CollUtil.isEmpty(tag) ? "暂无标签" : String.join(", ", dto.getTag()));
        club.setVersion(0L);
        club.setDeleted(0);
        club.setCreated(LocalDateTime.now());
        club.setUpdated(LocalDateTime.now());
        // DB添加
        int result = clubMapper.insert(club);
        if (result <= 0) {
            throw new ServerErrorException("DB添加失败");
        }
        return result;
    }


    @Override
    public Club select(Long id) {
        Club result = clubMapper.select(id);
        if (ObjectUtil.isNull(result)) {
            throw new ServerErrorException("记录不存在");
        }
        return result;
    }


    @Override
    public List<ClubVO> list() {
        return clubMapper.list(new ClubPageDTO())
                .stream()
                .map(club -> BeanUtil.copyProperties(club, ClubVO.class))
                .collect(Collectors.toList());
    }


    @Override
    public PageInfo<Club> page(ClubPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(clubMapper.list(dto));
    }


    @Transactional

    @Override
    public int update(ClubUpdateDTO dto) {
        Club club = clubMapper.select(dto.getId());
        if (ObjectUtil.isNull(club)) {
            throw new ServerErrorException("记录不存在");
        }
        // 设置默认值
        BeanUtil.copyProperties(dto, club);
        club.setTag(CollUtil.isEmpty(dto.getTag()) ? "暂无标签" : String.join(", ",
                dto.getTag()));
        club.setUpdated(LocalDateTime.now());
        // DB修改
        int result = clubMapper.update(club);
        if (result <= 0) {
            throw new ServerErrorException("DB修改失败");
        }
        return result;
    }


    @Override
    public int delete(Long id) {
        int result = clubMapper.delete(id);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑删除失败");
        }
        return result;
    }



    @Override
    public int deleteBatch(List<Long> ids) {
        int result = clubMapper.deleteBatch(ids);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑批删失败");
        }
        return result;
    }

    @Override
    public List<ClubExcel> getExcelData() {
        return clubMapper.list(new ClubPageDTO())
                .stream()
                .map(club -> {
                    ClubExcel clubExcel = BeanUtil.copyProperties(club, ClubExcel.class);
                    if (ObjectUtil.isNotNull(club.getDirection())) {
                        clubExcel.setDirectionTitle(club.getDirection().getTitle());
                    }
                    return clubExcel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public int endClub(Long id) {
        // 查询班级记录
        Club club = clubMapper.select(id);
        if (ObjectUtil.isNull(club)) {
            throw new ServerErrorException("班级记录不存在");
        }
        // 判断是否已结班
        if (ObjectUtil.isNotNull(club.getEndTime())) {
            throw new IllegalParamException("班级已结班");
        }
        // DB修改：修改班级记录
        club.setEndTime(LocalDateTime.now());
        int result = clubMapper.update(club);
        if (result <= 0) {
            throw new ServerErrorException("DB班级记录修改失败");
        }
        return result;
    }
}