package entity.dto;

import cn.zm.mybatis.utils.ObjectConvert;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import entity.BaseAccount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@TableName("rela_user_spot")
@ApiModel(value="RelaUserSpotDTO对象", description="用户门票关系表")
public class RelaUserSpotDTO extends ObjectConvert<BaseAccount>{
    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "用户id")
    private Long user_id;
    @ApiModelProperty(value = "门票地点id")
    private Long spot_id;
}

