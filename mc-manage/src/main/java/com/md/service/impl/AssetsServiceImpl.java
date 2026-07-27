package com.md.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.constant.MC;
import com.md.dto.AssetsInsertDTO;
import com.md.dto.AssetsPageDTO;
import com.md.dto.AssetsUpdateDTO;
import com.md.entity.Assets;
import com.md.entity.School;
import com.md.excel.AssetsExcel;
import com.md.exception.ServerErrorException;
import com.md.mapper.AssetsMapper;
import com.md.service.AssetsService;
import com.md.util.MinioUtil;
import com.md.vo.AssetsVO;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: CM
 * @Description TODO
 */
@Service
public class AssetsServiceImpl implements AssetsService {

    @Autowired
    private AssetsMapper assetsMapper;

    @Override
    public List<AssetsVO> list() {
        return assetsMapper.list();
    }

    @Override
    public Assets select(Long id) {
        return assetsMapper.select(id);
    }

    @Override
    public PageInfo<Assets> page(AssetsPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        List<Assets> list = assetsMapper.page(dto);
        return new PageInfo<>(list);
    }

    @Override
    public int insert(AssetsInsertDTO dto) {
        String info = dto.getInfo();
        Assets assets = BeanUtil.copyProperties(dto, Assets.class);
        assets.setPicture(MC.Assets.DEFAULT_ASSETS);
        assets.setInfo(StrUtil.isBlank(info)?"暂无描述":info);
        assets.setStock(assets.getTotal());
        assets.setVersion(0L);
        assets.setDeleted(0);
        assets.setCreated(LocalDateTime.now());
        assets.setUpdated(LocalDateTime.now());
        return assetsMapper.insert(assets);
    }

    @Override
    public int delete(Long id) {
        return assetsMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return assetsMapper.deleteBatch(ids);
    }

    @Override
    public int update(AssetsUpdateDTO dto) {
        Assets assets =assetsMapper.select(dto.getId());
        if (ObjectUtil.isNull(assets)){
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto,assets);
        assets.setUpdated(LocalDateTime.now());
        return assetsMapper.update(assets);
    }

    @Override
    public List<AssetsExcel> getExcelData() {
        List<Assets> list = assetsMapper.page(new AssetsPageDTO());
        List<AssetsExcel> collect = list.stream().map(assets -> BeanUtil.copyProperties(assets, AssetsExcel.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public String uploadPicture(MultipartFile newFile, Long id) {
        //根据id查询资产记录
        Assets assets = assetsMapper.select(id);
        if (ObjectUtil.isNull(assets)){
            throw new ServerErrorException("记录不存在");
        }
        //获取原有的文件名
        String oldFile=assets.getPicture();
        //生成新的文件名
        String newFileName = MinioUtil.randomFilename(newFile);
        //更新DB中文件名字
        assets.setPicture(newFileName);
        if (assetsMapper.update(assets)<=0){
            throw new ServerErrorException("DB更新失败");
        }
        try {
            //删除Minio旧文件(默认文件不删除)
            if (MC.Assets.DEFAULT_ASSETS.equals(oldFile)){
                MinioUtil.delete(oldFile,MC.MinIO.ASSETS_DIR,MC.MinIO.BUCKET_NAME);
            }
            //上传新文件
            MinioUtil.upload(newFile,newFileName,MC.MinIO.ASSETS_DIR,MC.MinIO.BUCKET_NAME);
        }catch (Exception e){
            throw new ServerErrorException("Minio操作失败"+e.getMessage());

        }
        return newFileName;
    }
}