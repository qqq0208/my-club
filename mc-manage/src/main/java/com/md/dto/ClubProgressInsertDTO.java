package com.md.dto;

import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "班级进度添加DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubProgressInsertDTO implements Serializable {

    @Schema(description = "班级进度顺序")
    @NotNull(message = "班级进度顺序不能为空")
    private Integer idx;

    @Schema(description = "班级ID")
    @NotNull(message = "班级ID不能为空")
    private Long fkClubId;

    @Schema(description = "房间ID")
    @NotNull(message = "房间ID不能为空")
    private Long fkRoomId;

    @Schema(description = "班主任ID")
    @NotNull(message = "班主任ID不能为空")
    private Long fkManagerId;

    @Schema(description = "助教老师ID")
    @NotNull(message = "助教老师ID不能为空")
    private Long fkAssistantId;

    @Schema(description = "主讲老师ID")
    @NotNull(message = "主讲老师ID不能为空")
    private Long fkTeacherId;

    @Schema(description = "课程ID")
    @NotNull(message = "课程ID不能为空")
    private Long fkCourseId;

    @Schema(description = "进度开始时间", example = "2023-10-05T12:12:12Z")
    @NotNull(message = "进度开始时间不能为空")
    private LocalDateTime startTime;

    @Schema(description = "预计进度结束时间", example = "2023-10-05T12:12:12Z")
    @NotNull(message = "预计进度结束时间不能为空")
    private LocalDateTime expectedEndTime;

    @Schema(description = "实际进度结束时间", example = "2023-10-05T12:12:12Z")
    private LocalDateTime endTime;

    @Schema(description = "班级进度描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}