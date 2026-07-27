package com.md.controller;

import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.Emp;
import com.md.result.Result;
import com.md.service.EmpService;
import com.md.util.EasyExcelUtil;
import com.md.vo.EmpVO;
import com.md.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 油桃老师
 **/
@Tag(name = "员工模块")
@RestController
@RequestMapping("/api/v1/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @Operation(summary = "查询--全部")
    @GetMapping("/list")
    public Result<List<EmpVO>> list() {
        return new Result<>(empService.list());
    }

    @Operation(summary = "查询--分页")
    @GetMapping("/page")
    public Result<PageInfo<Emp>> page(@Validated @ParameterObject EmpPageDTO dto) {
        return new Result<>(empService.page(dto));
    }

    @Operation(summary = "查询--单条")
    @GetMapping("/select/{id}")
    public Result<Emp> page(@PathVariable("id") Long id) {
        return new Result<>(empService.select(id));
    }

    @Operation(summary = "新增 - 单条新增")
    @PostMapping("insert")
    public Result<Integer> insert(@RequestBody @Validated EmpInsertDTO dto) {
        return new Result<>(empService.insert(dto));

    }


    @Operation(summary = "修改 - 单条修改")
    @PutMapping("update")
    public Result<Integer> update(@RequestBody @Validated EmpUpdateDTO dto) {
        return new Result<>(empService.update(dto));
    }

    @Operation(summary = "删除 - 单条删除")
    @DeleteMapping("delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id) {
        return new Result<>(empService.delete(id));
    }

    @Operation(summary = "删除 - 批量删除")
    @DeleteMapping("deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        return new Result<>(empService.deleteBatch(ids));
    }

    @Operation(summary = "查询 - 报表打印")
    @SneakyThrows
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "员工统计表", empService.getExcelData());
    }

    @Operation(summary = "上传 - 员工头像")
    @PostMapping("/uploadAvatar/{id}")
    public Result<String> uploadAvatar(@RequestParam("avatarFile") MultipartFile avatarFile,
                                       @PathVariable("id") Long id) {
        return new Result<>(empService.uploadAvatar(avatarFile, id));
    }
    @Operation(summary = "修改 - 员工密码")
    @PutMapping("updatePassword")
    public Result<Integer> updatePassword(@Validated @RequestBody EmpUpdatePasswordDTO dto) {
        return new Result<>(empService.updatePassword(dto));
    }

    @Operation(summary = "登录 - 账号密码")
    @PostMapping("loginByAccount")
    public Result<LoginVO> loginByAccount(@RequestBody @Validated LoginDTO dto) {
        return new Result<>(empService.loginBuAccount(dto));
    }

}

