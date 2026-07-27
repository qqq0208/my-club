package com.md.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/** @author  */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索资产申请DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsBorrowPageDTO extends PageDTO { 
 
    @Schema(description = "资产ID") 
    private Long fkAssetsId; 
 
    @Schema(description = "员工ID") 
    private Long fkEmpId; 
} 