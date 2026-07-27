package com.md.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @Author: CM
 * @Description TODO
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "按条件分页搜索房间DTO")
public class RoomPageDto extends PageDTO{
    @Schema(description = "房间名")
    private String title;
}