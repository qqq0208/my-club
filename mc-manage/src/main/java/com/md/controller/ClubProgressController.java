package com.md.controller;

import com.md.dto.*;
import com.md.entity.ClubProgress;
import com.md.service.ClubProgressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.md.entity.Club;
import com.md.result.Result;
import com.md.service.ClubService;
import com.md.util.EasyExcelUtil;
import com.md.vo.ClubVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @Author: CM
 * @Description TODO
 */
@Tag(name = "资产模块")
@RestController
@RequestMapping("api/v1/clubProgress")
public class ClubProgressController {
    @Autowired
    private ClubProgressService clubProgressService;
    @Operation(summary = "新增 - 单条新增")
    @PostMapping("insert")
    public Result<Integer> insert(@RequestBody @Validated ClubProgressInsertDTO dto) {
        return new Result<>(clubProgressService.insert(dto));
    }
    @Operation(summary = "查询 - 单条查询")
    @GetMapping("select/{id}")
    public Result<ClubProgress> select(@PathVariable("id") Long id) {
        return new Result<>(clubProgressService.select(id));
    }
    @Operation(summary = "查询 - 分页查询")
    @GetMapping("page")
    public Result<PageInfo<ClubProgress>> page(@Validated ClubProgressPageDTO dto) {
        return new Result<>(clubProgressService.page(dto));
    }
    @Operation(summary = "修改 - 单条修改")
    @PutMapping("update")
    public Result<Integer> update(@RequestBody @Validated ClubProgressUpdateDTO dto) {
        return new Result<>(clubProgressService.update(dto));
    }
    @Operation(summary = "删除 - 单条删除")
    @DeleteMapping("delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id) {
        return new Result<>(clubProgressService.delete(id));
    }
    @Operation(summary = "删除 - 批量删除")
    @DeleteMapping("deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        return new Result<>(clubProgressService.deleteBatch(ids));
    }
    @Operation(summary = "查询 - 报表打印")
    @SneakyThrows
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "班级进度统计表", clubProgressService.getExcelData());
    }
    @Operation(summary = "修改 - 结束进度")
    @PutMapping("endClubProgress/{id}")
    public Result<Integer> endClubProgress(@PathVariable("id") Long id) {
        return new Result<>(clubProgressService.endClubProgress(id));
    }
}