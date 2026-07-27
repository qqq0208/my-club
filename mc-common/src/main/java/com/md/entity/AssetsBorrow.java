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
public class AssetsBorrow implements Serializable {
    /** 主键 */
    private Long id;
    /** 资产ID，资产表外键 */
    private Long fkAssetsId;
    /** 员工ID，员工表外键 */
    private Long fkEmpId;
    /** 申请数量 */
    private Integer count;
    /** 申请时间 */
    private LocalDateTime borrowTime;
    /** 预计归还时间 */
    private LocalDateTime expectedReturnTime;
    /** 实际归还时间 */
    private LocalDateTime returnTime;
    /** 申请描述 */
    private String info;
    /** 数据版本 */
    private Long version;
    /** 逻辑删除，0未删除，1已删除 */
    private Integer deleted;
    /** 创建时间 */
    private LocalDateTime created;
    /** 修改时间 */
    private LocalDateTime updated;

    //每条资产申请记录对应1条资产记录
    private Assets assets;
    //每条资产申请记录对应1条员工记录
    private Emp emp;
}