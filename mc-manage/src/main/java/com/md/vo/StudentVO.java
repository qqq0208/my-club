package com.md.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** @author  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO implements Serializable {
/** 主键 */ 
private Long id; 
/** 学生姓名 */ 
private String realname; 
/** 学生编号 */ 
private String number; 
} 