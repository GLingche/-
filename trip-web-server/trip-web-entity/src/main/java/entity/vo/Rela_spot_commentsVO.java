package entity.vo;

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
@Accessors(chain = true)
@TableName("rela_user_comments")
@ApiModel(value="Rela_spot_commentsDTO对象", description="景点评论关系表")
public class Rela_spot_commentsVO extends ObjectConvert<BaseAccount>{
    @ApiModelProperty(value = "账户标识")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "实体id")
    private Long entity_id;
    @ApiModelProperty(value = "评论id")
    private Long comments_id;
    @ApiModelProperty(value = "评论与实体关联类型{景点:SCENIC_SPOT}")
    private String type;
}

