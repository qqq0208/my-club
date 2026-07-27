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
import java.time.LocalDateTime;

/** @author  */
@Schema(description = "员工添加DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpInsertDTO implements Serializable {

    @Schema(description = "登录账号")
    @NotEmpty(message = "登录账号不能为空")
    @Pattern(regexp = MC.Regex.USERNAME_RE, message = MC.Regex.USERNAME_RE_MSG)
    private String username;

    @Schema(description = "登录密码")
    @NotEmpty(message = "登录密码不能为空")
    @Pattern(regexp = MC.Regex.PASSWORD_RE, message = MC.Regex.PASSWORD_RE_MSG)
    private String password;

    @Schema(description = "手机号码")
    @NotEmpty(message = "手机号码不能为空")
    @Pattern(regexp = MC.Regex.PHONE_RE, message = MC.Regex.PHONE_RE_MSG)
    private String phone;

    @Schema(description = "微信号码")
    @NotEmpty(message = "微信号码不能为空")
    private String wechat;

    @Schema(description = "电子邮箱")
    @NotEmpty(message = "电子邮箱不能为空")
    @Pattern(regexp = MC.Regex.EMAIL_RE, message = MC.Regex.EMAIL_RE_MSG)
    private String email;

    @Schema(description = "真实姓名")
    @NotEmpty(message = "真实姓名不能为空")
    @Pattern(regexp = MC.Regex.REALNAME_RE, message = MC.Regex.REALNAME_RE_MSG)
    private String realname;

    @Schema(description = "身份证号")
    @Pattern(regexp = MC.Regex.ID_CARD_RE, message = MC.Regex.ID_CARD_RE_MSG)
    @NotEmpty(message = "身份证号不能为空")
    private String idcard;

    @Schema(description = "部门外键")
    @NotNull(message = "部门外键不能为空")
    private Long fkDeptId;

    @Schema(description = "员工描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;

    @Schema(description = "入职日期", example = "2023-10-05T12:12:12Z")
    @NotNull(message = "入职日期不能为空")
    private LocalDateTime hiredate;
}