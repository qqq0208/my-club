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
public class ClubExcel implements Serializable {
    @ExcelProperty(value = {"班级数据统计表", "班级标题"})
    private String title;
    @ExcelProperty(value = {"班级数据统计表", "班级标签"})
    private String tag;
    @ExcelProperty(value = {"班级数据统计表", "班级方向"})
    private String directionTitle;
    @ExcelProperty(value = {"班级数据统计表", "开班时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime startTime;
    @ExcelProperty(value = {"班级数据统计表", "预计结班时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime expectedEndTime;
    @ExcelProperty(value = {"班级数据统计表", "实际结班时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime endTime;
    @ExcelProperty(value = {"班级数据统计表", "班级描述"})
    private String info;
    @ExcelProperty(value = {"班级数据统计表", "首次创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime created;
    @ExcelProperty(value = {"班级数据统计表", "最后创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updated;
}