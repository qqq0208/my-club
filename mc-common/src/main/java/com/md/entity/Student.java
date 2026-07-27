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
public class Student implements Serializable {
    /** 主键 */
    private Long id;
    /** 学生编号 */
    private String number;
    /** 学生姓名 */
    private String realname;
    /** 头像地址 */
    private String avatar;
    /** 手机号码 */
    private String phone;
    /** 学生性别：0女1男2保密 */
    private Integer gender;
    /** 微信号码 */
    private String wechat;
    /** 学生年龄 */
    private Integer age;
    /** 籍贯省份 */
    private String province;
    /** 现居住地 */
    private String address;
    /** 学校ID，学校表外键 */
    private Long fkSchoolId;
    /** 咨询ID，员工表外键 */
    private Long fkSalesmanId;
    /** 班级ID，班级表外键 */
    private Long fkClubId;
    /** 身份证号 */
    private String idcard;
    /** 入学时间 */
    private LocalDateTime studyDate;
    /** 紧急联系人姓名 */
    private String emergencyName;
    /** 紧急联系人电话 */
    private String emergencyPhone;
    /** 学生状态：0在培1结课2就业3休学4其它 */
    private Integer status;
    /** 学生描述 */
    private String info;
    /** 数据版本 */
    private Long version;
    /** 逻辑删除，0未删除，1已删除 */
    private Integer deleted;
    /** 创建时间 */
    private LocalDateTime created;
    /** 修改时间 */
    private LocalDateTime updated;
    /** 每条学生记录对应 1 条学校记录 */
    private School school;
    /** 每条学生记录对应 1 条销售人员记录 */
    private Emp salesman;
    /** 每条学生记录对应 1 条班级记录 */
    private Club club;
}