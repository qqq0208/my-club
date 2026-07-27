package com.md.controller;

import com.github.pagehelper.PageInfo;
import com.md.dto.AssetsBorrowInsertDTO;
import com.md.dto.AssetsBorrowPageDTO;
import com.md.dto.AssetsBorrowUpdateDTO;
import com.md.entity.AssetsBorrow;
import com.md.result.Result;
import com.md.service.AssetsBorrowService;
import com.md.util.EasyExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
@Tag(name = "资产申请模块")
@RestController
@RequestMapping("/api/v1/assetsBorrow")
public class AssetsBorrowController {
    @Autowired
    private AssetsBorrowService assetsBorrowService;


    @Operation(summary = "查询--条件分页")
    @GetMapping("/page")
    public Result<PageInfo<AssetsBorrow>> page(@Validated @ParameterObject AssetsBorrowPageDTO dto) {
        return new Result<>(assetsBorrowService.page(dto));
    }

    @Operation(summary = "查询--全部记录")
    @GetMapping("/list")
    public Result<List<AssetsBorrow>> list() {
        return new Result<>(assetsBorrowService.selectAll());
    }

    @Operation(summary = "查询--单条记录")
    @GetMapping("/select/{id}")
    public Result<AssetsBorrow> select(@PathVariable("id") Long id) {
        return new Result<>(assetsBorrowService.select(id));
    }

    @Operation(summary = "添加--单个记录")
    @PostMapping("/insert")
    public Result<Integer> insert(@RequestBody @Validated AssetsBorrowInsertDTO dto) {
        return new Result<>(assetsBorrowService.insert(dto));
    }

    @Operation(summary = "修改--单个记录")
    @PutMapping("/update")
    public Result<Integer> update(@RequestBody @Validated AssetsBorrowUpdateDTO dto) {
        return new Result<>(assetsBorrowService.update(dto));
    }

    @Operation(summary = "删除--单个记录")
    @DeleteMapping("/deleted/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id) {
        return new Result<>(assetsBorrowService.delete(id));
    }

    @Operation(summary = "删除--多个记录")
    @DeleteMapping("/deletedBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        return new Result<>(assetsBorrowService.deleteBatch(ids));
    }


    @Operation(summary = "查询--报表打印")
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "资产申请统计表", assetsBorrowService.getExcelData());
    }

    @Operation(summary = "修改--归还资产")
    @PutMapping("returnAssets/{id}")
    public Result<Integer> returnAssets(@PathVariable("id") Long id) {
        return new Result<>(assetsBorrowService.returnAssets(id));
    }
}