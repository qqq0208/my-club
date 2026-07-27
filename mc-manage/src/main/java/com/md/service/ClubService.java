package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.ClubInsertDTO;
import com.md.dto.ClubPageDTO;
import com.md.dto.ClubUpdateDTO;
import com.md.entity.Club;
import com.md.excel.ClubExcel;
import com.md.vo.ClubVO;

import java.util.List;

public interface ClubService {
    int insert(ClubInsertDTO dto);
    Club select(Long id);
    List<ClubVO> list();
    PageInfo<Club> page(ClubPageDTO dto);
    int update(ClubUpdateDTO dto);
    int delete(Long id);
    int deleteBatch(List<Long> ids);
    /**
     * 获取班级记录的Excel数据
     *
     * @return 班级记录的Excel数据列表
     */
    List<ClubExcel> getExcelData();
    /**
     * 班级结班
     *
     * @param id 班级主键
     * @return 影响条目数
     */
    int endClub(Long id);
}
