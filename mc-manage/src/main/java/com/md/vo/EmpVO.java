package com.md.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: CM
 * @Description TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpVO implements Serializable {
    private Long id;
    private String realname;
}