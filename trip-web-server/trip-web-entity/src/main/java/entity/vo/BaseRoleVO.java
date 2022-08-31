package entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("base_role")
@ApiModel(value="BaseRoleVO对象", description="基础权限表")
public class BaseRoleVO {
        @ApiModelProperty(value = "权限标识")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;
        @ApiModelProperty(value = "权限名称")
        private String name;

}
