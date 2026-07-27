package com.md.dto;
import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
/** @author  */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索课程DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePageDTO extends PageDTO {
    @Schema(description = "课程名称")
    private String title;
    @Schema(description = "课程分类")
    private String category;
}