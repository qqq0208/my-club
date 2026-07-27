package com.md.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** @author  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsVO implements Serializable {
    /** 主键 */
    private Long id;
    /** 资产名称 */
    private String title;
}