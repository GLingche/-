package cn.zm.trip.service.impl;

import cn.zm.trip.mapper.Rela_spot_commentsMapper;
import cn.zm.trip.mapper.Rela_user_commentsMapper;
import cn.zm.trip.service.IRela_spot_commentsService;
import cn.zm.trip.service.IRela_user_commentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.Rela_spot_comments;
import entity.Rela_user_comments;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class Rela_spot_commentsImpl extends ServiceImpl<Rela_spot_commentsMapper, Rela_spot_comments> implements IRela_spot_commentsService {

}
