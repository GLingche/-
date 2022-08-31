package cn.zm.trip.service.impl;

import cn.zm.trip.mapper.BaseRoleMapper;
import cn.zm.trip.mapper.RelaRoleMapper;
import cn.zm.trip.service.IBaseRoleService;
import cn.zm.trip.service.IRelaRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.BaseRole;
import entity.RelaUserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BaseRoleServiceImpl  extends ServiceImpl<BaseRoleMapper, BaseRole> implements IBaseRoleService {
}
