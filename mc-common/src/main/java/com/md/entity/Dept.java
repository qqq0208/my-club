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
public class Dept implements Serializable {
    /** 主键 */
    private Long id;
    /** 部门名称 */
    private String title;
    /** 部门描述 */
    private String info;
    /** 房间ID，房间表外键 */
    private Long fkRoomId;
    /** 数据版本 */
    private Long version;
    /** 逻辑删除，0未删除，1已删除 */
    private Integer deleted;
    /** 创建时间 */
    private LocalDateTime created;
    /** 修改时间 */
    private LocalDateTime updated;
    /**部门(1)：房间(1)*/
    private Room room;
}