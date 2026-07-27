package com.md.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/** @author  */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索员工DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpPageDTO extends PageDTO {
    @Schema(description = "登录账号")
    private String username;
    @Schema(description = "手机号码")
    private String phone;
    @Schema(description = "身份证号")
    private String idcard;
    @Schema(description = "真实姓名")
    private String realname;
    @Schema(description = "部门外键")
    private Long fkDeptId;
}