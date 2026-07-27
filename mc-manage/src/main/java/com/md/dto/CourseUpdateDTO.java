package com.md.dto;
import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
/** @author  */
@Schema(description = "课程修改DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseUpdateDTO implements Serializable {

    @Schema(description = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;
    
    @Schema(description = "课程名称")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;

    @Schema(description = "课程分类")
    private String category;

    @Schema(description = "课程顺序")
    @Min(value = 0, message = "课程顺序不能小于0")
    private Integer idx;

    @Schema(description = "课程学时")
    @Min(value = 0, message = "课程学时不能小于0")
    private Integer hours;

    @Schema(description = "课程描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}