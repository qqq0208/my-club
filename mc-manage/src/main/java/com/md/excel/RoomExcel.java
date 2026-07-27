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
public class RoomExcel implements Serializable {
    @ExcelProperty(value = {"房间数据统计表", "房间标题"})
    private String title;
    @ExcelProperty(value = {"房间数据统计表", "房间容量（人）"})
    private Integer capacity;
    @ExcelProperty(value = {"房间数据统计表", "房间地址"})
    private String address;
    @ExcelProperty(value = {"房间数据统计表", "房间描述"})
    private String info;
    @ExcelProperty(value = {"房间数据统计表", "首次创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime created;
    @ExcelProperty(value = {"房间数据统计表", "最后创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updated;
}