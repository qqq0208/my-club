package com.md.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.md.dto.AssetsBorrowInsertDTO;
import com.md.dto.AssetsBorrowPageDTO;
import com.md.dto.AssetsBorrowUpdateDTO;
import com.md.entity.Assets;
import com.md.entity.AssetsBorrow;
import com.md.excel.AssetsBorrowExcel;
import com.md.exception.IllegalParamException;
import com.md.exception.ServerErrorException;
import com.md.mapper.AssetsBorrowMapper;
import com.md.mapper.AssetsMapper;
import com.md.service.AssetsBorrowService;
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
public class AssetsBorrowServiceImpl implements AssetsBorrowService {

    @Autowired
    private AssetsBorrowMapper assetsBorrowMapper;
    @Autowired
    private AssetsMapper assetsMapper;

    @Override
    public PageInfo<AssetsBorrow> page(AssetsBorrowPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        return new PageInfo<>(assetsBorrowMapper.list(dto));
    }

    @Override
    public List<AssetsBorrow> selectAll() {
        return assetsBorrowMapper.list(new AssetsBorrowPageDTO());
    }

    @Override
    public AssetsBorrow select(Long id) {
        return assetsBorrowMapper.select(id);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(AssetsBorrowInsertDTO dto) {
        String info = dto.getInfo();
        Long fkAssetsId = dto.getFkAssetsId();
        Integer count = dto.getCount();
        //判断申请时间和预计归还时间
        if (dto.getBorrowTime().isAfter(dto.getExpectedReturnTime())) {
            throw new IllegalParamException("申请时间不能晚于预计归还时间");
        }
        //查询资产信息
        Assets assets = assetsMapper.select(fkAssetsId);
        if (ObjectUtil.isNull(assets)) {
            throw new ServerErrorException("资产不存在");
        }
        //判断剩余库存
        Integer stock = assets.getStock();
        if (count > stock) {
            throw new IllegalParamException("库存不足，仅剩" + stock + assets.getStockUnit());
        }
        //DB添加：添加申请记录
        AssetsBorrow assetsBorrow = BeanUtil.copyProperties(dto, AssetsBorrow.class);
        assetsBorrow.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        assetsBorrow.setVersion(0L);
        assetsBorrow.setDeleted(0);
        assetsBorrow.setCreated(LocalDateTime.now());
        assetsBorrow.setUpdated(LocalDateTime.now());
        int insert = assetsBorrowMapper.insert(assetsBorrow);
        if(insert <= 0){
            throw new ServerErrorException("添加申请记录失败");
        }
        //DB修改：修改资产表库存
        stock -= count;
        assets.setStock(stock);
        Integer update = assetsMapper.update(assets);
        if(update <=0 ){
            throw new ServerErrorException("资产库存扣减失败");
        }
        return insert + update;
    }

    @Override
    public int update(AssetsBorrowUpdateDTO dto) {
        AssetsBorrow assetsBorrow = assetsBorrowMapper.select(dto.getId());
        if (ObjectUtil.isNull(assetsBorrow)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, assetsBorrow);
        // 设置默认值
        assetsBorrow.setUpdated(LocalDateTime.now());
        return assetsBorrowMapper.update(assetsBorrow);
    }

    @Override
    public int delete(Long id) {

        return assetsBorrowMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {

        return assetsBorrowMapper.deleteBatch(ids);
    }

    @Override
    public List<AssetsBorrowExcel> getExcelData() {
        return assetsBorrowMapper.list(new AssetsBorrowPageDTO()).stream().map(assetsBorrow -> {
                    AssetsBorrowExcel assetsBorrowExcel = new AssetsBorrowExcel();
                    BeanUtil.copyProperties(assetsBorrow, assetsBorrowExcel);
                    //给资产名称赋值
                    if (ObjectUtil.isNotNull(assetsBorrow.getAssets())) {
                        assetsBorrowExcel.setAssetsTitle(assetsBorrow.getAssets().getTitle());
                    }
                    //给员工姓名赋值
                    if (ObjectUtil.isNotNull(assetsBorrow.getEmp())) {
                        assetsBorrowExcel.setEmpName(assetsBorrow.getEmp().getRealname());
                    }
                    //设置是否已归还
                    assetsBorrowExcel.setIsReturn(ObjectUtil.isNotNull(assetsBorrow.getReturnTime()) ? "已归还" : "未归还");
                    //返回当前处理后的excel
                    return assetsBorrowExcel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public int returnAssets(Long id) {
        // 查询资产申请记录
        AssetsBorrow assetsBorrow = assetsBorrowMapper.select(id);
        if (ObjectUtil.isNull(assetsBorrow)) {
            throw new ServerErrorException("资产记录不存在");
        }
        // 查询资产信息
        Assets assets = assetsMapper.select(assetsBorrow.getFkAssetsId());
        if (ObjectUtil.isNull(assets)) {
            throw new ServerErrorException("资产不存在");
        }
        // 判断是否已归还
        if (ObjectUtil.isNotNull(assetsBorrow.getReturnTime())) {
            throw new IllegalParamException("资产已归还");
        }
        // DB修改：修改资产申请记录
        assetsBorrow.setReturnTime(LocalDateTime.now());
        int update = assetsBorrowMapper.update(assetsBorrow);
        if (update <= 0) {
            throw new ServerErrorException("修改资产申请记录失败");
        }
        // DB修改：修改资产库存
        assets.setStock(assets.getStock() + assetsBorrow.getCount());
        int update1 = assetsMapper.update(assets);
        if (update1 <= 0) {
            throw new ServerErrorException("修改资产库存失败");
        }
        return update + update1;
    }

}
