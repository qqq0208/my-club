package com.md.controller;

import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.School;
import com.md.result.Result;
import com.md.service.SchoolService;
import com.md.util.EasyExcelUtil;
import com.md.vo.SchoolVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
@Tag(name = "学校模块")
@RestController
@RequestMapping("/api/v1/school")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @GetMapping("/list")
    @Operation(summary = "查询-全部记录")
    public Result<List<SchoolVO>> list(){
        return new Result<>(schoolService.list());
    }
    @Operation(summary = "查询-单个记录")
    @GetMapping("/select/{id}")
    public Result<School> select(@PathVariable("id") Long id) {
        return new Result<>(schoolService.select(id));
    }

    @Operation(summary = "查询-条件分页")
    @GetMapping("/page")
    public Result<PageInfo<School>> page(@Validated @ParameterObject SchoolPageDTO dto){
        return new Result<>(schoolService.page(dto));
    }

    @Operation(summary = "添加--单条记录")
    @PostMapping("/insert")
    public Result<Integer> insert(@RequestBody @Validated SchoolInsertDTO dto){
        return new Result<>(schoolService.insert(dto));
    }
    @Operation(summary = "删除--单条记录")
    @DeleteMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id){
        return new Result<>(schoolService.delete(id));
    }

    @Operation(summary = "删除-多条记录")
    @DeleteMapping("/deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids){
        return new Result<>(schoolService.deleteBatch(ids));
    }
    @Operation(summary = "修改-单条记录")
    @PutMapping("/update")
    public Result<Integer> update(@RequestBody @Validated SchoolUpdateDTO dto){
        return new Result<>(schoolService.update(dto));
    }
    @Operation(summary = "查询--报表打印")
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp){
        EasyExcelUtil.download(resp,"学校统计表",schoolService.getExcelData());
    }
}