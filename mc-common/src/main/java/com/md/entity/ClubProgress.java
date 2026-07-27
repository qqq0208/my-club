package com.md.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
/** @author  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubProgress implements Serializable {
    /** 主键 */
    private Long id;
    /** 班级进度顺序 */
    private Integer idx;
    /** 班级ID，班级表外键 */
    private Long fkClubId;
    /** 房间ID，房间表外键 */
    private Long fkRoomId;
    /** 班主任ID，员工表外键 */
    private Long fkManagerId;
    /** 助理老师ID，员工表外键 */
    private Long fkAssistantId;
    /** 主讲老师ID，员工表外键 */
    private Long fkTeacherId;
    /** 课程ID，课程表外键 */
    private Long fkCourseId;
    /** 进度开始时间 */
    private LocalDateTime startTime;
    /** 预计进度结束时间 */
    private LocalDateTime expectedEndTime;
    /** 实际进度结束时间 */
    private LocalDateTime endTime;
    /** 班级进度描述 */
    private String info;
    /** 数据版本 */
    private Long version;
    /** 逻辑删除，0未删除，1已删除 */
    private Integer deleted;
    /** 创建时间 */
    private LocalDateTime created;
    /** 修改时间 */
    private LocalDateTime updated;
    /** 关联的数据*/
    private Club club;
    private Room room;
    private Emp manager;
    private Emp teacher;
    private Emp assistant;
    private Course course;
}