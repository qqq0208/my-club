package com.md.dto;

import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: CM
 * @Description TODO
 */
@Schema(description = "房间修改DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomUpdateDTO implements Serializable {

    @Schema(description = "主键")
    @NotNull(message = "房间id不能为空")
    private Long id;

    @Schema(description = "房间名称")

    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;

    @Schema(description = "房间地址")

    @Pattern(regexp = MC.Regex.ADDRESS_RE, message = MC.Regex.ADDRESS_RE_MSG)
    private String address;

    @Schema(description = "房间描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;

    @Schema(description = "房间容量")
    @Min(value = 0, message = "房间容量必须大于0")
    private Integer capacity;
}