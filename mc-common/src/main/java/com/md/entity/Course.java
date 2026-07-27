package com.md.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {
    /** 主键 */
    private Long id;
    /** 课程名称 */
    private String title;
    /** 课程类别 */
    private String category;
    /** 课程顺序 */
    private Integer idx;
    /** 课程学时 */
    private Integer hours;
    /** 课程描述 */
    private String info;
    /** 数据版本 */
    private Long version;
    /** 逻辑删除，0未删除，1已删除 */
    private Integer deleted;
    /** 创建时间 */
    private LocalDateTime created;
    /** 修改时间 */
    private LocalDateTime updated;
}