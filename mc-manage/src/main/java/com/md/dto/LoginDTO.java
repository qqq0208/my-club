package com.md.dto;

import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: CM
 * @Description TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {
    @Schema(description = "登录账号")
    @NotEmpty(message = "登录账号不能为空")
    @Pattern(regexp = MC.Regex.USERNAME_RE, message = MC.Regex.USERNAME_RE_MSG)
    private String username;
    @Schema(description = "登录密码")
    @NotEmpty(message = "登录密码不能为空")
    @Pattern(regexp = MC.Regex.PASSWORD_RE, message = MC.Regex.PASSWORD_RE_MSG)
    private String password;
}