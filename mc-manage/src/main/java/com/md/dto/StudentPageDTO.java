package com.md.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/** @author  */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索学生DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPageDTO extends PageDTO {
    @Schema(description = "学生编号")
    private String number;
    @Schema(description = "学生姓名")
    private String realname;
    @Schema(description = "学校ID")
    private Long fkSchoolId;
    @Schema(description = "销售ID")
    private Long fkSalesmanId;
    @Schema(description = "班级ID")
    private Long fkClubId;
    @Schema(description = "学生状态")
    private Integer status;
}