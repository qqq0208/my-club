package com.md.dto;
import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/** @author  */
@Schema(description = "菜单修改DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuUpdateDTO implements Serializable {

    @NotNull(message = "主键不能为空")
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "菜单名称")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;

    @Schema(description = "菜单地址")
    @Pattern(regexp = MC.Regex.MENU_URL_RE, message = MC.Regex.MENU_URL_RE_MSG)
    private String url;

    @Schema(description = "菜单图标")
    @Pattern(regexp = MC.Regex.MENU_ICON_RE, message = MC.Regex.MENU_ICON_RE_MSG)
    private String icon;

    @Schema(description = "父菜单ID")
    @Min(value = 0, message = "父菜单ID不能小于0")
    private Long pid;

    @Schema(description = "菜单描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}