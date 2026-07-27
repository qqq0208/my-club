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
public class AssetsExcel implements Serializable {
    @ExcelProperty(value = {"资产数据统计表", "资产标题"})
    private String title;
    @ExcelProperty(value = {"资产数据统计表", "资产图片"})
    private String picture;
    @ExcelProperty(value = {"资产数据统计表", "资产单价"})
    private Double price;
    @ExcelProperty(value = {"资产数据统计表", "单价单位"})
    private String priceUnit;
    @ExcelProperty(value = {"资产数据统计表", "剩余库存"})
    private Integer stock;
    @ExcelProperty(value = {"资产数据统计表", "总计库存"})
    private Integer total;
    @ExcelProperty(value = {"资产数据统计表", "库存单位"})
    private String stockUnit;
    @ExcelProperty(value = {"资产数据统计表", "资产描述"})
    private String info;
    @ExcelProperty(value = {"资产数据统计表", "首次创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime created;
    @ExcelProperty(value = {"资产数据统计表", "最后创建日期"})
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updated;
}