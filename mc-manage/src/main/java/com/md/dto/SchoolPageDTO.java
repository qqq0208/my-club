package com.md.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/** @author  */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索学校DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolPageDTO extends PageDTO {

    @Schema(description = "学校名称")
    private String title;
}