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
public class RoleMenu implements Serializable {
    /** 主键 */
    private Long id;
    /** 角色ID，角色表外键 */
    private Long fkRoleId;
    /** 菜单ID，菜单表外键 */
    private Long fkMenuId;
    /** 数据版本 */
    private Long version;
    /** 逻辑删除，0未删除，1已删除 */
    private Integer deleted;
    /** 创建时间 */
    private LocalDateTime created;
    /** 修改时间 */
    private LocalDateTime updated;
}