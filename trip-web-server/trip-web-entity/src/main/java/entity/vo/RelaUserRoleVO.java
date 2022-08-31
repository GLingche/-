package entity.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("rela_role")
@ApiModel(value="RelaUserRoleVO对象", description="权限关联表")
public class RelaUserRoleVO {
    @ApiModelProperty(value = "权限标识")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "用户id")

    private Long userId;
    @ApiModelProperty(value = "权限id")

    private Long roleId;
}
