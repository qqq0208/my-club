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
@Schema(description = "班级添加DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubInsertDTO implements Serializable {

    @Schema(description = "班级名称")
    @NotEmpty(message = "班级名称不能为空")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;

    @Schema(description = "班级标签")
    private List<String> tag;

    @Schema(description = "班级方向ID")
    @NotNull(message = "班级方向ID不能为空")
    private Long fkDirectionId;

    @Schema(description = "开班时间", example = "2023-10-05T12:12:12Z")
    @NotNull(message = "开班时间不能为空")
    private LocalDateTime startTime;

    @Schema(description = "预计结班时间", example = "2023-10-05T12:12:12Z")
    @NotNull(message = "预计结班时间不能为空")
    private LocalDateTime expectedEndTime;

    @Schema(description = "实际结班时间", example = "2023-10-05T12:12:12Z")
    private LocalDateTime endTime;

    @Schema(description = "班级描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}