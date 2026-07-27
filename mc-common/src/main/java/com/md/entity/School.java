package com.md.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class School implements Serializable {
    /** 主键 */
    private Long id;
    /** 学校名称 */
    private String title;
    /** 学院名称 */
    private String college;
    /** 专业名称 */
    private String major;
    /** 班级名称 */
    private String clazz;
    /** 学校地址 */
    private String address;
    /** 学校描述 */
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