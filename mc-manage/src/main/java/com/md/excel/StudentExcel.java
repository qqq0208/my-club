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
public class StudentExcel implements Serializable {
    @ExcelProperty(value = {"学生数据统计表", "学生编号"})
    private String number;
    @ExcelProperty(value = {"学生数据统计表", "学生姓名"})
    private String realname;
    @ExcelProperty(value = {"学生数据统计表", "手机号码"})
    private String phone;
    @ExcelProperty(value = {"学生数据统计表", "学生性别"})
    private String gender;
    @ExcelProperty(value = {"学生数据统计表", "微信号码"})
    private String wechat;
    @ExcelProperty(value = {"学生数据统计表", "学生年龄"})
    private Integer age;
    @ExcelProperty(value = {"学生数据统计表", "籍贯省份"})
    private String province;
    @ExcelProperty(value = {"学生数据统计表", "现居住地"})
    private String address;
    @ExcelProperty(value = {"学生数据统计表", "所在学校"})
    private String schoolTitle;
    @ExcelProperty(value = {"学生数据统计表", "销售姓名"})
    private String salesmanName;
    @ExcelProperty(value = {"学生数据统计表", "班级名称"})
    private String clubTitle;
    @ExcelProperty(value = {"学生数据统计表", "身份证号"})
    private String idcard;
    @ExcelProperty(value = {"学生数据统计表", "入学时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime studyDate;
    @ExcelProperty(value = {"学生数据统计表", "紧急联系人"})
    private String emergencyName;
    @ExcelProperty(value = {"学生数据统计表", "紧急联系人手机号"})
    private String emergencyPhone;
    @ExcelProperty(value = {"学生数据统计表", "学生状态"})
    private String status;
    @ExcelProperty(value = {"学生数据统计表", "学生描述"})
    private String info;
    @ExcelProperty(value = {"学生数据统计表", "创建时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime created;
    @ExcelProperty(value = {"学生数据统计表", "修改时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updated;
}