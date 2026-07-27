package com.md.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** @author  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO implements Serializable {
    /** 主键 */
    private Long id;
    /** 菜单名称 */
    private String title;
    /** 父菜单ID */
    private Long pid;
    /** 父菜单名称 */
    private String parentTitle;
}