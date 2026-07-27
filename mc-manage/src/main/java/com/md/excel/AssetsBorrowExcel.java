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
public class AssetsBorrowExcel implements Serializable {
	@ExcelProperty(value = {"资产申请数据统计表", "资产标题"})
    private String assetsTitle;
    @ExcelProperty(value = {"资产申请数据统计表", "员工姓名"})
    private String empName;
    @ExcelProperty(value = {"资产申请数据统计表", "申请数量"})
    private Integer count;
    @ExcelProperty(value = {"资产申请数据统计表", "申请时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime borrowTime;
    @ExcelProperty(value = {"资产申请数据统计表", "预计归还时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime expectedReturnTime;
    @ExcelProperty(value = {"资产申请数据统计表", "实际归还时间"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime returnTime;
    @ExcelProperty(value = {"资产申请数据统计表", "是否已归还"})
    private String isReturn;
    @ExcelProperty(value = {"资产申请数据统计表", "资产申请描述"})
    private String info;
    @ExcelProperty(value = {"资产申请数据统计表", "首次创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime created;
    @ExcelProperty(value = {"资产申请数据统计表", "最后创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updated;
}