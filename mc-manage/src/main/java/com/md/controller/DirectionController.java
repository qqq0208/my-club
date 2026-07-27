package com.md.controller;

import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.Assets;
import com.md.entity.Direction;
import com.md.result.Result;
import com.md.service.DirectionService;
import com.md.util.EasyExcelUtil;
import com.md.vo.AssetsVO;
import com.md.vo.DirectionVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
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
@RestController
@RequestMapping("api/v1/direction")
public class DirectionController {

    @Autowired
    private DirectionService directionService;
    @Operation(summary = "新增 - 单条新增")
    @PostMapping("insert")
    public Result<Integer> insert(@RequestBody @Validated DirectionInsertDTO dto) {
        return new Result<>(directionService.insert(dto));
    }
    @Operation(summary = "查询 - 单条查询")
    @GetMapping("select/{id}")
    public Result<Direction> select(@PathVariable("id") Long id) {
        return new Result<>(directionService.select(id));
    }
    @Operation(summary = "查询 - 全部记录")
    @GetMapping("list")
    public Result<List<DirectionVO>> list() {
        return new Result<>(directionService.list());
    }
    @Operation(summary = "查询 - 分页查询")
    @GetMapping("page")
    public Result<PageInfo<Direction>> page(@Validated DirectionPageDTO dto) {
        return new Result<>(directionService.page(dto));
    }
    @Operation(summary = "修改 - 单条修改")
    @PutMapping("update")
    public Result<Integer> update(@RequestBody @Validated DirectionUpdateDTO dto) {
        return new Result<>(directionService.update(dto));
    }
    @Operation(summary = "删除 - 单条删除")
    @DeleteMapping("delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id) {
        return new Result<>(directionService.delete(id));
    }

    @Operation(summary = "删除 - 批量删除")
    @DeleteMapping("deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        return new Result<>(directionService.deleteBatch(ids));
    }
    @Operation(summary = "查询 - 报表打印")
    @SneakyThrows
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "方向统计表", directionService.getExcelData());
    }

}