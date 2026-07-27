package com.md.controller;

import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.Dept;
import com.md.result.Result;
import com.md.service.DeptService;
import com.md.util.EasyExcelUtil;
import com.md.vo.DeptVO;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
@Tag(name = "部门模块")
@RestController
@RequestMapping("/api/v1/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @Operation(summary = "查询 - 全部记录")
    @GetMapping("/list")
    public Result<List<DeptVO>> list() {
        return new Result<>(deptService.list());
    }

    @Operation(summary = "查询 - 分页查询")
    @GetMapping("/page")
    public Result<PageInfo<Dept>> page(@Validated @ParameterObject DeptPageDTO dto) {
        return new Result<>(deptService.page(dto));
    }

    @Operation(summary = "查询 - 单条记录")
    @GetMapping("/select/{id}")
    public Result<Dept> select(@PathVariable("id") Long id) {
        return new Result<>(deptService.select(id));
    }

    @Operation(summary = "新增 - 单条新增")
    @PostMapping("insert")
    public Result<Integer> insert(@RequestBody @Validated DeptInsertDTO dto) {
        return new Result<>(deptService.insert(dto));
    }

    @Operation(summary = "修改 - 单条修改")
    @PutMapping("update")
    public Result<Integer> update(@RequestBody @Validated DeptUpdateDTO dto) {
        return new Result<>(deptService.update(dto));
    }

    @Operation(summary = "删除 - 单条删除")
    @DeleteMapping("delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id) {
        return new Result<>(deptService.delete(id));
    }

    @Operation(summary = "删除 - 批量删除")
    @DeleteMapping("deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        return new Result<>(deptService.deleteBatch(ids));
    }

    @Operation(summary = "查询 - 报表打印")
    @SneakyThrows
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "部门统计表", deptService.getExcelData());
    }

}