package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.Assets;
import com.md.entity.School;
import com.md.excel.AssetsExcel;
import com.md.vo.AssetsVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author CM
 */
public interface AssetsService {
    //查询--全部记录
    List<AssetsVO> list();

    //查询-单条
    Assets select(Long id);
    //查询--条件分页
    PageInfo<Assets> page(AssetsPageDTO dto);

    //添加--单条
    int insert(AssetsInsertDTO dto);

    int delete(Long id);

    //删除--多条
    int deleteBatch(List<Long> ids);

    //修改
    int update(AssetsUpdateDTO dto);

    List<AssetsExcel> getExcelData();
    //上传资产图片
    String  uploadPicture(MultipartFile newFile,Long id);
}
