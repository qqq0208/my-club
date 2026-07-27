package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.AssetsBorrowInsertDTO;
import com.md.dto.AssetsBorrowPageDTO;
import com.md.dto.AssetsBorrowUpdateDTO;
import com.md.entity.AssetsBorrow;
import com.md.excel.AssetsBorrowExcel;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
public interface AssetsBorrowService {

    PageInfo<AssetsBorrow> page(AssetsBorrowPageDTO dto);

    List<AssetsBorrow> selectAll();

    AssetsBorrow select(Long id);

    int insert(AssetsBorrowInsertDTO dto);

    int update(AssetsBorrowUpdateDTO dto);


    //删除--单条
    int delete(Long id);


    //删除--多条

    int deleteBatch(List<Long> ids);
    /**
     * 获取资产申请记录的Excel数据
     *
     * @return 资产申请记录的Excel数据列表
     */
    List<AssetsBorrowExcel> getExcelData();

    /**
     * 资产归还
     *
     * @param id 资产申请记录主键
     * @return 影响条目数
     */
    int returnAssets(Long id);
}