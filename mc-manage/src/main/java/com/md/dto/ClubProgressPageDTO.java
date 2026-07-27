package com.md.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索班级进度DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubProgressPageDTO extends PageDTO {

    @Schema(description = "班级ID")
    private String fkClubId;
    @Schema(description = "房间ID")
    private Long fkRoomId;
    @Schema(description = "班主任ID")
    private Long fkManagerId;
    @Schema(description = "助教老师ID")
    private Long fkAssistantId;
    @Schema(description = "主讲老师ID")
    private Long fkTeacherId;
    @Schema(description = "课程ID")
    private Long fkCourseId;
}