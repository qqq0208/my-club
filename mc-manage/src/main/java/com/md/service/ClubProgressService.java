package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.ClubProgressInsertDTO;
import com.md.dto.ClubProgressPageDTO;
import com.md.dto.ClubProgressUpdateDTO;
import com.md.entity.ClubProgress;
import com.md.excel.ClubProgressExcel;

import java.util.List;

public interface ClubProgressService {
    int insert(ClubProgressInsertDTO dto);
    ClubProgress select(Long id);
    PageInfo<ClubProgress> page(ClubProgressPageDTO dto);
    int update(ClubProgressUpdateDTO dto);
    int delete(Long id);
    int deleteBatch(List<Long> ids);
    /**
     * 获取班级记录的Excel数据
     *
     * @return 班级记录的Excel数据列表
     */
    List<ClubProgressExcel> getExcelData();
    /**
     * 结束进度
     *
     * @param id 班级进度主键
     * @return 影响条目数
     */
    int endClubProgress(Long id);
}
