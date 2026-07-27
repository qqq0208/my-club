package com.md.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Serializable {
    /** 主键 */
    private Long id;
    /** 房间名称 */
    private String title;
    /** 房间地址 */
    private String address;
    /** 房间描述 */
    private String info;
    /** 房间容量，单位人 */
    private Integer capacity;
    /** 数据版本 */
    private Long version;
    /** 逻辑删除，0未删除，1已删除 */
    private Integer deleted;
    /** 创建时间 */
    private LocalDateTime created;
    /** 修改时间 */
    private LocalDateTime updated;
}