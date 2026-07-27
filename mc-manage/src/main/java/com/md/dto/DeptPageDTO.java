package com.md.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**  @author  */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索部门DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptPageDTO extends PageDTO {
    @Schema(description = "部门名称")
    private String title;
}