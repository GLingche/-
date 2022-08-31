package cn.zm.trip.service.impl;

import cn.zm.trip.mapper.RelaUserSpotMapper;
import cn.zm.trip.mapper.Rela_user_commentsMapper;
import cn.zm.trip.service.IRelaUserSpotService;
import cn.zm.trip.service.IRela_user_commentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.RelaUserSpot;
import entity.Rela_user_comments;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class RelaUserSpotImpl extends ServiceImpl<RelaUserSpotMapper, RelaUserSpot> implements IRelaUserSpotService {

}
