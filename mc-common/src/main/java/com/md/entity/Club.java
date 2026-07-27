package com.md.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/** @author 周航宇 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Club implements Serializable {
    /** 主键 */
    private Long id;
    /** 班级名称 */
    private String title;
    /** 班级标签 */
    private String tag;
    /** 方向ID，方向表外键 */
    private Long fkDirectionId;
    /** 开班时间 */
    private LocalDateTime startTime;
    /** 预计结班时间 */
    private LocalDateTime expectedEndTime;
    /** 实际结班时间 */
    private LocalDateTime endTime;
    /** 班级描述 */
    private String info;
    /** 数据版本 */
    private Long version;
    /** 逻辑删除，0未删除，1已删除 */
    private Integer deleted;
    /** 创建时间 */
    private LocalDateTime created;
    /** 修改时间 */
    private LocalDateTime updated;

    /** 每条班级记录对应 1 条方向记录 */
    private Direction direction;
}

