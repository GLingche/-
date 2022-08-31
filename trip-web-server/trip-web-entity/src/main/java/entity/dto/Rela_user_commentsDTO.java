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
@TableName("rela_user_comments")
@ApiModel(value="rela_user_commentsDTO对象", description="用户评论关系表")
public class Rela_user_commentsDTO extends ObjectConvert<BaseAccount>{
    @ApiModelProperty(value = "账户标识")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "用户id")
    private Long user_id;
    @ApiModelProperty(value = "评论id")
    private Long comments_id;
}

