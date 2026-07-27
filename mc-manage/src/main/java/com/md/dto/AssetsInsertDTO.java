package com.md.dto;
import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/** @author  */
@Schema(description = "资产添加DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsInsertDTO implements Serializable {

	@Schema(description = "资产名称")
    @NotEmpty(message = "资产名称不能为空")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;

    @Schema(description = "资产单价")
    @NotNull(message = "资产单价不能为空")
    @DecimalMin(value = "0.01", message = "资产单价不能小于0.01元")
    private Double price;

    @Schema(description = "单价单位")
    @NotEmpty(message = "单价单位不能为空")
    private String priceUnit;

    @Schema(description = "总计库存")
    @NotNull(message = "总计库存不能为空")
    @Min(value = 0, message = "总计库存不能小于0")
    private Integer total;

    @Schema(description = "库存单位")
    @NotEmpty(message = "库存单位不能为空")
    private String stockUnit;
    
    @Schema(description = "资产描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}