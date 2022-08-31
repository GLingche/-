package cn.zm.trip.restful;

import cn.zm.common.base.ResResult;
import cn.zm.common.enums.ResEnum;
import cn.zm.common.utils.AssertUtil;
import cn.zm.trip.service.*;
import cn.zm.trip.uitls.JwtUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import entity.*;
import entity.dto.*;
import entity.vo.BaseUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("auth")
@Api(tags = "权限认证")
public class AuthController {
  // @Resource
  // private IViewUserAccountService userAccountService;
  //
  //
  // @PostMapping("/login")
  // @ApiOperation("登陆")
  // public ResResult<ViewUserAccountVO> login(@RequestBody ViewUserAccountDTO dto) {
  //   ViewUserAccount one = userAccountService.getOne(new QueryWrapper<>(dto.convert()));
  //   AssertUtil.assertNonNull(one, ResEnum.INVALID_PARAMS);
  //   return ResResult.succ(one.convert());
  // }
  //
  // @PostMapping("/register")
  // @ApiOperation("注册")
  // public ResResult register(@RequestBody @Validated ViewUserAccountDTO dto) {
  //   userAccountService.register(dto);
  //   return ResResult.succ();
  // }

    @Resource
    private IBaseAccountService baseAccountService;
    @Resource
    private IBaseRoleService baseRoleService;
    @Resource
    private IRelaRoleService relaRoleService;
    @Resource
    private IBaseUserService baseUserService;
    @Resource
    private IRelaUserAccountService iRelaUserAccountService;


    @PostMapping("login")
    @ApiOperation("登录")
    public ResResult login(@RequestBody BaseAccountDTO dto) {

        BaseAccount loginUser = baseAccountService.getOne(new QueryWrapper<>(dto.convert()));
        if(loginUser == null) {
            return ResResult.fail("用户不存在");
        }
        RelaUserRoleDTO relaUserRoleDTO = new RelaUserRoleDTO(null,loginUser.getId(),null);
        RelaUserRole roleRela = relaRoleService.getOne(new QueryWrapper<>(relaUserRoleDTO.convert()));
        BaseRole role = baseRoleService.getById(roleRela.getRoleId());
        String token = JwtUtil.getToken(loginUser.getId().toString(), role);
        return ResResult.succ(token);
    }

    @PostMapping("register")
    @ApiOperation("注册")
    public ResResult register(@RequestBody  @Validated UserInfoDTO userInfoDTO) {

        String phone =  userInfoDTO.getBaseUserDTO().getPhone();
        String account = userInfoDTO.getBaseAccountDTO().getUsername();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(account)) {
            return ResResult.fail("电话或账号不能为空！");
        }

        boolean judgeBaseUser = baseUserService.save(userInfoDTO.getBaseUserDTO().convert());
        boolean judgeBaseAccount = baseAccountService.save(userInfoDTO.getBaseAccountDTO().convert());

        RelaUserAccount relaUserAccount = new RelaUserAccount();
        userInfoDTO.getBaseAccountDTO().setId(null);
        BaseAccount baseAccount = baseAccountService.getOne(new QueryWrapper<>(userInfoDTO.getBaseAccountDTO().convert()));
        userInfoDTO.getBaseUserDTO().setId(null);
        BaseUser baseUser = baseUserService.getOne(new QueryWrapper<>(userInfoDTO.getBaseUserDTO().convert()));
        relaUserAccount.setAccountId(baseAccount.getId());
        relaUserAccount.setUserId(baseUser.getId());
        boolean judgeRelaUserAccount =  iRelaUserAccountService.save(relaUserAccount);

        RelaUserRole relaUserRole = new RelaUserRole();

        relaUserRole.setUserId(baseAccount.getId());
        BaseRoleDTO baseRoleQuery = new BaseRoleDTO();
        baseRoleQuery.setName(userInfoDTO.getBaseUserDTO().getAuth());

        BaseRole baseRole = baseRoleService.getOne(new QueryWrapper<>(baseRoleQuery.convert()));
        relaUserRole.setRoleId(baseRole.getId());
        boolean judgeRelaUserRole =  relaRoleService.save(relaUserRole);

        if (!judgeBaseAccount || !judgeBaseUser ||!judgeRelaUserAccount ||!judgeRelaUserRole) {
            return ResResult.fail("系统异常");
        }
        return ResResult.succ("注册成功");
    }

    @PostMapping("userInfo")
    @ApiOperation("获取用户信息接口")
    public ResResult getUserInfo(HttpServletRequest request) {

        boolean judge = JwtUtil.checkToken(request);
        if(!judge) {
            return ResResult.fail("token异常");
        }
        String userId = JwtUtil.getUserIdByToken(request);
        BaseAccount baseAccount = baseAccountService.getById(userId);
        if(baseAccount == null) {
            return ResResult.fail("用户不存在");
        }
        RelaUserAccountDTO relaUserAccountDTO = new RelaUserAccountDTO();
        relaUserAccountDTO.setAccountId(baseAccount.getId());
        relaUserAccountDTO.setId(null);
        RelaUserAccount relaUserAccount = iRelaUserAccountService.getOne(new QueryWrapper<>(relaUserAccountDTO.convert()));
        BaseUser baseUser = baseUserService.getById(relaUserAccount.getUserId());

        return ResResult.succ(baseUser);
    }



}
