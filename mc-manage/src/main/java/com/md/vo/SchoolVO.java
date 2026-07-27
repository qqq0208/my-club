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
@NoArgsConstructor
@AllArgsConstructor
public class SchoolVO implements Serializable {
    private Long id;
    private String title;
    private String college;
    private String major;
    private String clazz;

}