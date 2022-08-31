package entity.dto;


import cn.zm.mybatis.utils.ObjectConvert;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import entity.BaseUser;
import entity.RelaUserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@TableName("rela_role")
@ApiModel(value="RelaUserRoleDTO对象", description="权限关联表")
public class RelaUserRoleDTO  extends ObjectConvert<RelaUserRole> {
    @ApiModelProperty(value = "权限标识")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "用户id")

    private Long userId;
    @ApiModelProperty(value = "权限id")

    private Long roleId;
}
