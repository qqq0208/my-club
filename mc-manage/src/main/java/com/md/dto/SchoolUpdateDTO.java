package com.md.dto;

import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** @author  */
@Schema(description = "学校修改DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolUpdateDTO implements Serializable {

    @Schema(description = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;
    
    @Schema(description = "学校名称")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;
    
    @Schema(description = "学院名称")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String college;
    
    @Schema(description = "专业名称")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String major;
    
    @Schema(description = "班级名称")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String clazz;
    
    @Schema(description = "学校地址")
    @Pattern(regexp = MC.Regex.ADDRESS_RE, message = MC.Regex.ADDRESS_RE_MSG)
    private String address;
    
    @Schema(description = "学校描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}