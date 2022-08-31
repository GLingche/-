package entity.dto;

import entity.BaseAccount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;

@Data
@Accessors(chain = true)
@ApiModel(value="UserInfoDTO对象")
public class UserInfoDTO {
    @ApiModelProperty(value = "基础-账户")
    @Valid
    private BaseAccountDTO baseAccountDTO;

    @ApiModelProperty(value = "基础-用户")
    @Valid
    private BaseUserDTO baseUserDTO;


}

