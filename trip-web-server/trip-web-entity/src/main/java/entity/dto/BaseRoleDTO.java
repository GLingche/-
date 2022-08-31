package entity.dto;

import cn.zm.mybatis.utils.ObjectConvert;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import entity.BaseRole;
import entity.BaseUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("base_role")
@ApiModel(value="BaseRoleDTO对象", description="基础权限表")
public class BaseRoleDTO extends ObjectConvert<BaseRole> {
        @ApiModelProperty(value = "权限标识")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;
        @ApiModelProperty(value = "权限名称")
        private String name;

}
