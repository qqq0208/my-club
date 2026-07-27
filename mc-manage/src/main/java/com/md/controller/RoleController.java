package com.md.controller;

import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.Role;
import com.md.result.Result;
import com.md.service.RoleService;
import com.md.util.EasyExcelUtil;
import com.md.vo.RoleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/** @author  */
@Tag(name = "角色模块")
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "新增 - 单条新增")
    @PostMapping("insert")
    public Result<Integer> insert(@RequestBody @Validated RoleInsertDTO dto) {
        return new Result<>(roleService.insert(dto));
    }

    @Operation(summary = "查询 - 单条查询")
    @GetMapping("select/{id}")
    public Result<Role> select(@PathVariable("id") Long id) {
        return new Result<>(roleService.select(id));
    }

    @Operation(summary = "查询 - 全部记录")
    @GetMapping("list")
    public Result<List<RoleVO>> list() {
        return new Result<>(roleService.list());
    }

    @Operation(summary = "查询 - 分页查询")
    @GetMapping("page")
    public Result<PageInfo<Role>> page(@Validated @ParameterObject RolePageDTO dto) {
        return new Result<>(roleService.page(dto));
    }

    @Operation(summary = "修改 - 单条修改")
    @PutMapping("update")
    public Result<Integer> update(@RequestBody @Validated RoleUpdateDTO dto) {
        return new Result<>(roleService.update(dto));
    }

    @Operation(summary = "删除 - 单条删除")
    @DeleteMapping("delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id) {
        return new Result<>(roleService.delete(id));
    }
    @Operation(summary = "删除 - 批量删除")
    @DeleteMapping("deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        return new Result<>(roleService.deleteBatch(ids));
    }
    @Operation(summary = "查询 - 报表打印")
    @SneakyThrows
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "角色统计表", roleService.getExcelData());
    }

    @Operation(summary = "查询 - 员工角色")
    @GetMapping("listByEmpId/{empId}")
    public Result<List<Role>> listByEmpId(@PathVariable("empId") Long empId) {
        return new Result<>(roleService.listByEmpId(empId));
    }
    @Operation(summary = "修改 - 员工角色")
    @PutMapping("updateByEmpId")
    public Result<Integer> updateByEmpId(@RequestParam("empId") Long empId,
                                         @RequestParam("roleIds") List<Long> roleIds) {
        return new Result<>(roleService.updateByEmpId(empId, roleIds));
    }
}