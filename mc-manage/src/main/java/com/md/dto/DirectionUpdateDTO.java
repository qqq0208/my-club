package com.md.dto;
import com.md.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/** @author  */
@Schema(description = "方向修改DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectionUpdateDTO implements Serializable {

    @Schema(description = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    @Schema(description = "方向名称")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;

    @Schema(description = "方向描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}