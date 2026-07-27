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
public class MenuExcel implements Serializable {
    @ExcelProperty(value = {"菜单数据统计表", "菜单标题"})
    private String title;
    @ExcelProperty(value = {"菜单数据统计表", "上级标题"})
    private String parentTitle;
    @ExcelProperty(value = {"菜单数据统计表", "菜单图标"})
    private String icon;
    @ExcelProperty(value = {"菜单数据统计表", "菜单地址"})
    private String url;
    @ExcelProperty(value = {"菜单数据统计表", "菜单描述"})
    private String info;
    @ExcelProperty(value = {"菜单数据统计表", "首次创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime created;
    @ExcelProperty(value = {"菜单数据统计表", "最后创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updated;
}