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
public class ClubProgressExcel implements Serializable {
    @ExcelProperty(value = {"班级进度数据统计表", "班级进度顺序"})
    private Integer idx;
    @ExcelProperty(value = {"班级进度数据统计表", "房间标题"})
    private String roomTitle;
    @ExcelProperty(value = {"班级进度数据统计表", "班级标题"})
    private String clubTitle;
    @ExcelProperty(value = {"班级进度数据统计表", "课程标题"})
    private String courseTitle;
    @ExcelProperty(value = {"班级进度数据统计表", "班主任姓名"})
    private String managerName;
    @ExcelProperty(value = {"班级进度数据统计表", "助教姓名"})
    private String assistantName;
    @ExcelProperty(value = {"班级进度数据统计表", "主讲老师姓名"})
    private String teacherName;
    @ExcelProperty(value = {"班级进度数据统计表", "进度开始时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime startTime;
    @ExcelProperty(value = {"班级进度数据统计表", "预计进度结束时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime expectedEndTime;
    @ExcelProperty(value = {"班级进度数据统计表", "实际进度结束时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime endTime;
    @ExcelProperty(value = {"班级进度数据统计表", "班级进度描述"})
    private String info;
    @ExcelProperty(value = {"班级进度数据统计表", "首次创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime created;
    @ExcelProperty(value = {"班级进度数据统计表", "最后创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updated;
}