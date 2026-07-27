package com.md.dto;
import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** @author  */
@Schema(description = "部门修改DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptUpdateDTO implements Serializable {

    @Schema(description = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;
    
    @Schema(description = "部门名称")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;
    
    @Schema(description = "部门描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
    
    @Schema(description = "房间外键")
    private Long fkRoomId;
}