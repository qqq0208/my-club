package com.md.dto;

import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/** @author  */
@Schema(description = "资产申请添加DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsBorrowInsertDTO implements Serializable {

    @Schema(description = "资产ID")
    @NotNull(message = "资产ID不能为空")
    private Long fkAssetsId;

    @Schema(description = "员工ID")
    @NotNull(message = "员工ID不能为空")
    private Long fkEmpId;

    @Schema(description = "申请数量")
    @NotNull(message = "申请数量不能为空")
    @Min(value = 0, message = "申请数量不能小于0")
    private Integer count;

    @Schema(description = "申请时间")
    @NotNull(message = "申请时间不能为空")
    @Future(message = "申请时间不能早于今天")
    private LocalDateTime borrowTime;

    @Schema(description = "预计归还时间")
    @NotNull(message = "预计归还时间不能为空")
    @Future(message = "预计归还时间不能早于今天")
    private LocalDateTime expectedReturnTime;

    @Schema(description = "资产申请描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}