package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.DirectionInsertDTO;
import com.md.dto.DirectionPageDTO;
import com.md.dto.DirectionUpdateDTO;
import com.md.entity.Direction;
import com.md.excel.DirectionExcel;
import com.md.vo.DirectionVO;

import java.util.List;

public interface DirectionService {


    int insert(DirectionInsertDTO dto);
    Direction select(Long id);
    List<DirectionVO> list();
    PageInfo<Direction> page(DirectionPageDTO dto);
    int update(DirectionUpdateDTO dto);
    int delete(Long id);
    int deleteBatch(List<Long> ids);
    /**
     * 导出方向记录的Excel数据
     *
     * @return 方向记录的Excel数据列表
     */
    List<DirectionExcel> getExcelData();
}
