package cn.zm.trip.restful;

import cn.zm.common.base.ResResult;
import cn.zm.mybatis.base.BaseController;
import cn.zm.trip.service.*;
import cn.zm.trip.uitls.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import entity.*;
import entity.dto.BaseUserDTO;
import entity.dto.RelaUserAccountDTO;
import entity.dto.RelaUserRoleDTO;
import entity.vo.BaseUserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 基础用户
 * @author 十渊
 * @since 2022-07-07
 */
@RequestMapping("baseUser")
@RestController
@Api(tags = "基础-用户")
public class BaseUserController extends BaseController {

    @Resource
    private IBaseUserService baseUserService;
    @Resource
    private IBaseAccountService baseAccountService;

    @Resource
    private IRelaUserAccountService iRelaUserAccountService;

    @Resource
    private IRelaUserSpotService iRelaUserSpotService;
    @Resource
    private IBaseScenicSpotService iBaseScenicSpotService;

    @GetMapping
    @ApiOperation("基础用户page查询")
    // @ApiImplicitParams({
    //     @ApiImplicitParam(name = "page", value = "当前页数", defaultValue = "1"),
    //     @ApiImplicitParam(name = "size", value = "每页个数", defaultValue = "10"),
    //     @ApiImplicitParam(name = "orderByColumn", value = "排序字段"),
    //     @ApiImplicitParam(name = "isDesc", value = "是否降序")
    // })
    public ResResult<IPage<BaseUserVO>> getByPage(@Validated BaseUserDTO baseUser) {
        // TODO 分页查询
        IPage<BaseUserVO> page = baseUserService.selectByPage(getPage(), baseUser);
        return ResResult.succ(page);
    }

    // @PostMapping("list")
    // @ApiOperation("基础用户list查询")
    // @ApiImplicitParams({
    //     @ApiImplicitParam(name = "orderByColumn", value = "排序字段"),
    //     @ApiImplicitParam(name = "isDesc", value = "是否降序")
    // })
    // public ResResult<List<BaseUserVO>> list(@Validated @RequestBody BaseUserDTO baseUser) {
    //     // TODO 分页查询
    //     IPage<BaseUserVO> page = baseUserService.list(getPage(), baseUser);
    //     return ResResult.succ(page);
    // }

    @GetMapping("{id}")
    @ApiOperation("基础用户查询(id)")
    public ResResult<BaseUserVO> get(@PathVariable String id) {
        // TODO 查询
        boolean aNull = Objects.isNull(baseUserService.getById(id));
        return ResResult.succ(aNull ? null : baseUserService.getById(id).convert());
    }

    @PostMapping
    @ApiOperation("基础用户新增")
    public ResResult add(@RequestBody @Validated BaseUserDTO baseUser) {
        // TODO 新增
        baseUserService.save(baseUser.convert());
        return ResResult.succ("新增成功");
    }

    @DeleteMapping("{id}")
    @ApiOperation("基础用户删除")
    public ResResult del(@PathVariable String id) {
        // TODO 删除
        baseUserService.removeById(id);
        return ResResult.succ("删除成功");
    }

    @PutMapping
    @ApiOperation("基础用户修改")
    public ResResult update(@RequestBody @Validated BaseUserDTO baseUser) {
        // TODO 修改
        baseUserService.updateById(baseUser.convert());
        return ResResult.succ("修改成功");
    }


    @PostMapping("buy/{spotId}")
    @ApiOperation("基础用户门票服务")
    public ResResult buy(@PathVariable Long spotId, HttpServletRequest request ) {
        // TODO 新增
        boolean judge = JwtUtil.checkToken(request);
        if(!judge) {
            return ResResult.fail("请登录账号");
        }
        String userId = JwtUtil.getUserIdByToken(request);
        BaseAccount baseAccount = baseAccountService.getById(userId);
        RelaUserAccountDTO relaUserAccountDTO = new RelaUserAccountDTO();
        relaUserAccountDTO.setAccountId(baseAccount.getId());
        relaUserAccountDTO.setId(null);
        RelaUserAccount relaUserAccount = iRelaUserAccountService.getOne(new QueryWrapper<>(relaUserAccountDTO.convert()));
        BaseUser baseUser = baseUserService.getById(relaUserAccount.getUserId());

        iRelaUserSpotService.save(RelaUserSpot.builder().user_id(baseUser.getId()).spot_id(spotId).build());
        BaseScenicSpot baseScenicSpot =  iBaseScenicSpotService.getById(spotId);
        baseScenicSpot.setNum(baseScenicSpot.getNum()-1);
        iBaseScenicSpotService.updateById(baseScenicSpot);
        return ResResult.succ("新增成功");
    }


}
