package com.md.dto;
import com.md   .constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
/** @author  */
@Schema(description = "学生修改DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateDTO implements Serializable {

    @NotNull(message = "主键不能为空")
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "真实姓名")
    @Pattern(regexp = MC.Regex.REALNAME_RE, message = MC.Regex.REALNAME_RE_MSG)
    private String realname;

    @Schema(description = "手机号码")
    @Pattern(regexp = MC.Regex.PHONE_RE, message = MC.Regex.PHONE_RE_MSG)
    private String phone;
    
    @Schema(description = "学生性别")
    @Min(value = 0, message = "性别必须为0、1或2")
    @Max(value = 2, message = "性别必须为0、1或2")
    private Integer gender;

    @Schema(description = "学生年龄")
    @Min(value = 18, message = "年龄不能小于18岁")
    @Max(value = 60, message = "年龄不能大于60岁")
    private Integer age;

    @Schema(description = "所在省份")
    @Pattern(regexp = MC.Regex.PROVINCE_RE, message = MC.Regex.PROVINCE_RE_MSG)
    private String province;
    
    @Schema(description = "微信号码")
    private String wechat;

    @Schema(description = "现居住地")
    @Pattern(regexp = MC.Regex.ADDRESS_RE, message = MC.Regex.ADDRESS_RE_MSG)
    private String address;

    @Schema(description = "学校ID")
    private Long fkSchoolId;

    @Schema(description = "销售ID")
    private Long fkSalesmanId;

    @Schema(description = "班级ID")
    private Long fkClubId;

    @Schema(description = "身份证号")
    @Pattern(regexp = MC.Regex.ID_CARD_RE, message = MC.Regex.ID_CARD_RE_MSG)
    private String idcard;

    @Schema(description = "入学日期", example = "2023-10-05T12:12:12Z")
    private LocalDateTime studyDate;

    @Schema(description = "紧急联系人姓名")
    @Pattern(regexp = MC.Regex.REALNAME_RE, message = MC.Regex.REALNAME_RE_MSG)
    private String emergencyName;

    @Schema(description = "紧急联系人电话")
    @Pattern(regexp = MC.Regex.PHONE_RE, message = MC.Regex.PHONE_RE_MSG)
    private String emergencyPhone;

    @Schema(description = "学生状态")
    @Min(value = 0, message = "状态必须为0~4")
    @Max(value = 4, message = "状态必须为0~4")
    private Integer status;
    
    @Schema(description = "学生描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}