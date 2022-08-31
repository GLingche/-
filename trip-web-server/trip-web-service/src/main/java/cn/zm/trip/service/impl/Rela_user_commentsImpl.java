package cn.zm.trip.service.impl;

import cn.zm.mybatis.utils.ConvertUtil;
import cn.zm.trip.mapper.BaseAccountMapper;
import cn.zm.trip.mapper.Rela_user_commentsMapper;
import cn.zm.trip.service.IBaseAccountService;
import cn.zm.trip.service.IRela_user_commentsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.BaseAccount;
import entity.Rela_user_comments;
import entity.dto.BaseAccountDTO;
import entity.vo.BaseAccountVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class Rela_user_commentsImpl extends ServiceImpl<Rela_user_commentsMapper, Rela_user_comments> implements IRela_user_commentsService {

}
