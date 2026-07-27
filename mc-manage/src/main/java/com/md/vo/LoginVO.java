package com.md.vo;

import com.md.entity.Emp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: CM
 * @Description TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
    /** Token令牌 */
    private String token;
    /** 该员工信息 */
    private Emp emp;
}