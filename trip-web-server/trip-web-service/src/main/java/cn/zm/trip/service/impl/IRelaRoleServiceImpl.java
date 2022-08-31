package cn.zm.trip.service.impl;

import cn.zm.mybatis.utils.ConvertUtil;
import cn.zm.trip.mapper.BusCommentsMapper;
import cn.zm.trip.mapper.RelaRoleMapper;
import cn.zm.trip.service.IBusCommentsService;
import cn.zm.trip.service.IRelaRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.BaseRole;
import entity.BaseUser;
import entity.BusComments;
import entity.RelaUserRole;
import entity.dto.BaseRoleDTO;
import entity.dto.BaseUserDTO;
import entity.vo.BaseUserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class IRelaRoleServiceImpl extends ServiceImpl<RelaRoleMapper, RelaUserRole> implements IRelaRoleService {

}
