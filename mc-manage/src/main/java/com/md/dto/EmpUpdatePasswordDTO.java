package com.md.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: CM
 * @Description TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpUpdatePasswordDTO implements Serializable {
    @NotNull(message = "员工主键必须不为空")
    @Schema(description = "员工主键")
    private Long id;

    @NotEmpty(message = "旧密码不能为空")
    @Schema(description = "旧密码")
    private String oldPassword;

    @NotEmpty(message = "新密码不能为空")
    @Schema(description = "新密码")
    private String newPassword;
}