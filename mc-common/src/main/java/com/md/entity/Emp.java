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
public class Emp implements Serializable {
    /** 主键 */
    private Long id;
    /** 登录账号 */
    private String username;
    /** 登录密码 */
    private String password;
    /** 头像地址 */
    private String avatar;
    /** 手机号码 */
    private String phone;
    /** 微信号码 */
    private String wechat;
    /** 邮箱地址 */
    private String email;
    /** 员工性别：0女1男2保密 */
    private Integer gender;
    /** 员工年龄 */
    private Integer age;
    /** 籍贯省份 */
    private String province;
    /** 现居住地 */
    private String address;
    /** 真实姓名 */
    private String realname;
    /** 身份证号 */
    private String idcard;
    /** 部门ID，部门表外键 */
    private Long fkDeptId;
    /** 员工描述 */
    private String info;
    /** 入职时间 */
    private LocalDateTime hiredate;
    /** 数据版本 */
    private Long version;
    /** 逻辑删除，0未删除，1已删除 */
    private Integer deleted;
    /** 创建时间 */
    private LocalDateTime created;
    /** 修改时间 */
    private LocalDateTime updated;

    private Dept dept;
}