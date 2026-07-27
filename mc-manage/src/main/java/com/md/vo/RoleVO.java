package com.md.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** @author  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVO implements Serializable {
    /** 主键 */
    private Long id;
    /** 角色名称 */
    private String title;
}