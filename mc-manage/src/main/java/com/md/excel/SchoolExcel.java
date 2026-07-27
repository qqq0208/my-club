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
public class SchoolExcel implements Serializable {
    @ExcelProperty(value = {"学校数据统计表", "学校标题"})
    private String title;
    @ExcelProperty(value = {"学校数据统计表", "学院名称"})
    private String college;
    @ExcelProperty(value = {"学校数据统计表", "专业名称"})
    private String major;
    @ExcelProperty(value = {"学校数据统计表", "班级名称"})
    private String clazz;
    @ExcelProperty(value = {"学校数据统计表", "学校地址"})
    private String address;
    @ExcelProperty(value = {"学校数据统计表", "学校描述"})
    private String info;
    @ExcelProperty(value = {"学校数据统计表", "首次创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime created;
    @ExcelProperty(value = {"学校数据统计表", "最后创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updated;
}