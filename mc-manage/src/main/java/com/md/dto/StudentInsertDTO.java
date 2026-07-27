package com.md.dto;

import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/** @author  */
@Schema(description = "学生添加DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInsertDTO implements Serializable {

    @Schema(description = "真实姓名")
    @NotEmpty(message = "真实姓名不能为空")
    @Pattern(regexp = MC.Regex.REALNAME_RE, message = MC.Regex.REALNAME_RE_MSG)
    private String realname;

    @Schema(description = "手机号码")
    @NotEmpty(message = "手机号码不能为空")
    @Pattern(regexp = MC.Regex.PHONE_RE, message = MC.Regex.PHONE_RE_MSG)
    private String phone;
    
    @Schema(description = "微信号码")
    @NotEmpty(message = "微信号码不能为空")
    private String wechat;

    @Schema(description = "学校ID")
    @NotNull(message = "学校ID不能为空")
    private Long fkSchoolId;

    @Schema(description = "销售ID")
    @NotNull(message = "销售ID不能为空")
    private Long fkSalesmanId;

    @Schema(description = "班级ID")
    @NotNull(message = "班级ID不能为空")
    private Long fkClubId;

    @Schema(description = "身份证号")
    @Pattern(regexp = MC.Regex.ID_CARD_RE, message = MC.Regex.ID_CARD_RE_MSG)
    @NotEmpty(message = "身份证号不能为空")
    private String idcard;

    @Schema(description = "入学日期", example = "2023-10-05T12:12:12Z")
    @NotNull(message = "入学日期不能为空")
    private LocalDateTime studyDate;

    @Schema(description = "紧急联系人姓名")
    @NotEmpty(message = "紧急联系人姓名不能为空")
    @Pattern(regexp = MC.Regex.REALNAME_RE, message = MC.Regex.REALNAME_RE_MSG)
    private String emergencyName;
    
    @Schema(description = "紧急联系人电话")
    @NotEmpty(message = "紧急联系人电话不能为空")
    @Pattern(regexp = MC.Regex.PHONE_RE, message = MC.Regex.PHONE_RE_MSG)
    private String emergencyPhone;

    @Schema(description = "学生描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}