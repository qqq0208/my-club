package com.md.dto;
import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/** @author  */
@Schema(description = "资产修改DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsUpdateDTO implements Serializable {

    @Schema(description = "资产主键")
    @NotNull(message = "资产主键不能为空")
    private Long id;
    
    @Schema(description = "资产名称")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;
    
    @Schema(description = "资产单价")
    @DecimalMin(value = "0.01", message = "资产单价不能小于0.01元")
    private Double price;
    
    @Schema(description = "单价单位")
    private String priceUnit;
    
    @Schema(description = "剩余库存")
    @Min(value = 0, message = "剩余库存不能小于0")
    private Integer stock;
    
    @Schema(description = "总计库存")
    @Min(value = 0, message = "总计库存不能小于0")
    private Integer total;
    
    @Schema(description = "库存单位")
    private String stockUnit;
    
    @Schema(description = "资产描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}