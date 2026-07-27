package com.md.controller;

import com.github.pagehelper.PageInfo;
import com.md.dto.StudentInsertDTO;
import com.md.dto.StudentPageDTO;
import com.md.dto.StudentUpdateDTO;
import com.md.entity.Student;
import com.md.result.Result;
import com.md.service.StudentService;
import com.md.util.EasyExcelUtil;
import com.md.vo.StudentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
@Tag(name = "学生模块")
@RestController
@RequestMapping("/api/v1/student")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @Operation(summary = "新增 - 单条新增")
    @PostMapping("insert")
    public Result<Integer> insert(@RequestBody @Validated StudentInsertDTO dto) {
        return new Result<>(studentService.insert(dto));
    }
    @Operation(summary = "查询 - 单条查询")
    @GetMapping("select/{id}")
    public Result<Student> select(@PathVariable("id") Long id) {
        return new Result<>(studentService.select(id));
    }
    @Operation(summary = "查询 - 全部记录")
    @GetMapping("list")
    public Result<List<StudentVO>> list() {
        return new Result<>(studentService.list());
    }
    @Operation(summary = "查询 - 分页查询")
    @GetMapping("page")
    public Result<PageInfo<Student>> page(@Validated StudentPageDTO dto) {
        return new Result<>(studentService.page(dto));
    }
    @Operation(summary = "修改 - 单条修改")
    @PutMapping("update")
    public Result<Integer> update(@RequestBody @Validated StudentUpdateDTO dto) {
        return new Result<>(studentService.update(dto));
    }
    @Operation(summary = "删除 - 单条删除")
    @DeleteMapping("delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id) {
        return new Result<>(studentService.delete(id));
    }
    @Operation(summary = "删除 - 批量删除")
    @DeleteMapping("deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        return new Result<>(studentService.deleteBatch(ids));
    }
    @Operation(summary = "上传 - 学生头像")
    @PostMapping("/uploadAvatar/{id}")
    public Result<String> uploadAvatar(@RequestParam("avatarFile") MultipartFile avatarFile,
                                       @PathVariable("id") Long id) {
        return new Result<>(studentService.uploadAvatar(avatarFile, id));
    }
    @Operation(summary = "查询 - 报表打印")
    @SneakyThrows
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "学生统计表", studentService.getExcelData());
    }
}