package com.md.dto;

import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/** @author  */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索权限DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuPageDTO extends PageDTO {
    @Schema(description = "菜单名称")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;

    @Schema(description = "父菜单ID")
    @Min(value = 0, message = "父菜单ID必须大于0")
    private Long pid;
}