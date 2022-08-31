package cn.zm.trip.restful;

import cn.zm.common.base.ResResult;
import cn.zm.mybatis.base.BaseController;
import cn.zm.trip.service.*;
import cn.zm.trip.uitls.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import entity.BaseAccount;
import entity.BaseUser;
import entity.RelaUserAccount;
import entity.dto.BaseScenicSpotDTO;
import entity.dto.RelaUserAccountDTO;
import entity.vo.BaseScenicSpotVO;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 基础景点
 * @author 十渊
 * @since 2022-07-12
 */
@RequestMapping("baseScenicSpot")
@RestController
@Api(tags = "基础-景点")
public class BaseScenicSpotController extends BaseController {

    @Resource
    private IBaseScenicSpotService baseScenicSpotService;
    @Resource
    private IBaseAccountService baseAccountService;
    @Resource
    private IBaseUserService baseUserService;
    @Resource
    private IRelaUserAccountService iRelaUserAccountService;

    @GetMapping
    @ApiOperation("基础景点page查询")
    // @ApiImplicitParams({
    //     @ApiImplicitParam(name = "page", value = "当前页数", defaultValue = "1"),
    //     @ApiImplicitParam(name = "size", value = "每页个数", defaultValue = "10"),
    //     @ApiImplicitParam(name = "orderByColumn", value = "排序字段"),
    //     @ApiImplicitParam(name = "isDesc", value = "是否降序")
    // })
    public ResResult<IPage<BaseScenicSpotVO>> getByPage(@Validated BaseScenicSpotDTO baseScenicSpot) {
        // TODO 分页查询
        IPage<BaseScenicSpotVO> page = baseScenicSpotService.selectByPage(getPage(), baseScenicSpot);
        return ResResult.succ(page);
    }

    // @PostMapping("list")
    // @ApiOperation("基础景点list查询")
    // @ApiImplicitParams({
    //     @ApiImplicitParam(name = "orderByColumn", value = "排序字段"),
    //     @ApiImplicitParam(name = "isDesc", value = "是否降序")
    // })
    // public ResResult<List<BaseScenicSpotVO>> list(@Validated @RequestBody BaseScenicSpotDTO baseScenicSpot) {
    //     // TODO 分页查询
    //     IPage<BaseScenicSpotVO> page = baseScenicSpotService.list(getPage(), baseScenicSpot);
    //     return ResResult.succ(page);
    // }

    @GetMapping("{id}")
    @ApiOperation("基础景点查询(id)")
    public ResResult<BaseScenicSpotVO> get(@PathVariable String id) {
        // TODO 查询
        boolean aNull = Objects.isNull(baseScenicSpotService.getById(id));
        return ResResult.succ(aNull ? null : baseScenicSpotService.getById(id).convert());
    }

    @PostMapping
    @ApiOperation("基础景点新增")
    public ResResult add(@RequestBody @Validated BaseScenicSpotDTO baseScenicSpot, HttpServletRequest request) {
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
        if(baseUser.getAuth().equals("管理员")){
            // TODO 新增
            baseScenicSpotService.save(baseScenicSpot.convert());
            return ResResult.succ("新增成功");
        }
       return ResResult.fail("您不是管理员,无此权限");
    }

    @DeleteMapping("{id}")
    @ApiOperation("基础景点删除")
    public ResResult del(@PathVariable String id) {
        // TODO 删除
        baseScenicSpotService.removeById(id);
        return ResResult.succ("删除成功");
    }

    @PutMapping
    @ApiOperation("基础景点修改")
    public ResResult update(@RequestBody @Validated BaseScenicSpotDTO baseScenicSpot) {
        // TODO 修改
        baseScenicSpotService.updateById(baseScenicSpot.convert());
        return ResResult.succ("修改成功");
    }
}
