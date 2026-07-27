package com.md.controller;

import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.Assets;
import com.md.result.Result;
import com.md.service.AssetsService;
import com.md.util.EasyExcelUtil;
import com.md.vo.AssetsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.joda.time.field.MillisDurationField;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
@Tag(name = "资产模块")
@RestController
@RequestMapping("api/v1/assets")
public class AssetsController {
    @Autowired
    private AssetsService assetsService;


    @Operation(summary = "查询--全部记录")
    @GetMapping("list")
    public Result<List<AssetsVO>> list(){
        return new Result<>(assetsService.list());
    }
    @Operation(summary = "查询-单条记录")
    @GetMapping("/select/{id}")
    public Result<Assets> select(@PathVariable("id") Long id){
        return new Result<>(assetsService.select(id));
    }

    @Operation(summary = "查询-条件分页")
    @GetMapping("/page")
    public Result<PageInfo<Assets>> page(@Validated @ParameterObject AssetsPageDTO dto){
        return new Result<>(assetsService.page(dto));
    }

    @Operation(summary = "添加-单条记录")
    @PostMapping("/insert")
    public Result<Integer> insert(@RequestBody @Validated AssetsInsertDTO dto){
        return new Result<>(assetsService.insert(dto));
    }
    @Operation(summary = "删除-单条记录")
    @DeleteMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id){
        return new Result<>(assetsService.delete(id));
    }


    @Operation(summary = "删除-多条记录")
    @DeleteMapping("/deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids){
        return new Result<>(assetsService.deleteBatch(ids));
    }
    @Operation(summary = "修改-单条记录")
    @PutMapping("/update")
    public Result<Integer> update(@RequestBody @Validated AssetsUpdateDTO dto){
        return new Result<>(assetsService.update(dto));
    }
    @Operation(summary = "查询--报表打印")
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp){
        EasyExcelUtil.download(resp,"资产统计表",assetsService.getExcelData());
    }

    @Operation(summary = "上传--资产图片")
    @PostMapping("/uploadPicture/{id}")
    public Result<String >uploadPicture(@RequestParam("pictureFile") MultipartFile pictureFile,
                                        @PathVariable("id") Long id){
        return new Result<>(assetsService.uploadPicture(pictureFile,id));
    }
}