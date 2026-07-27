package com.md.excel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/** @author  */
@HeadStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER)  
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpExcel implements Serializable {
    @ExcelProperty(value = {"员工数据统计表", "工作信息", "真实姓名"})
    private String realname;
	@ExcelProperty(value = {"员工数据统计表", "工作信息", "所在部门"})
    private String deptName;
    @ExcelProperty(value = {"员工数据统计表", "工作信息", "入职时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime hiredate;
    @ExcelProperty(value = {"员工数据统计表", "个人信息", "手机号码"})
    private String phone;
    @ExcelProperty(value = {"员工数据统计表", "个人信息", "微信号码"})
    private String wechat;
    @ExcelProperty(value = {"员工数据统计表", "个人信息", "邮箱地址"})
    private String email;
    @ExcelProperty(value = {"员工数据统计表", "个人信息", "员工性别"})
    private String gender;
    @ExcelProperty(value = {"员工数据统计表", "个人信息", "员工年龄"})
    private Integer age;
    @ExcelProperty(value = {"员工数据统计表", "个人信息", "籍贯省份"})
    private String province;
    @ExcelProperty(value = {"员工数据统计表", "个人信息", "现居住地"})
    private String address;
    @ExcelProperty(value = {"员工数据统计表", "个人信息", "身份证号"})
    private String idcard;
    @ExcelProperty(value = {"员工数据统计表", "个人信息", "描述信息"})
    private String info;
    @ExcelProperty(value = {"员工数据统计表", "账号信息", "员工账号"})
    private String username;
    @ExcelProperty(value = {"员工数据统计表", "账号信息", "创建时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime created;
    @ExcelProperty(value = {"员工数据统计表", "账号信息", "修改时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updated;
}