package cn.zm.trip.restful;

import cn.zm.common.base.ResResult;
import cn.zm.mybatis.base.BaseController;
import cn.zm.trip.service.*;
import cn.zm.trip.uitls.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import entity.*;
import entity.dto.BaseScenicSpotDTO;
import entity.dto.BusCommentsDTO;
import entity.dto.RelaUserAccountDTO;
import entity.dto.ScenicSpotCommentsUserDTO;
import entity.vo.BusCommentsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
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
 * 业务评论表
 * @author 十渊
 * @since 2022-07-12
 */
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequestMapping("busComments")
@RestController
@Api(tags = "业务-评论表")
public class BusCommentsController extends BaseController {

    @Resource
    private IBusCommentsService busCommentsService;
    @Resource
    private IBaseAccountService baseAccountService;
    @Resource
    private IBaseUserService baseUserService;
    @Resource
    private IRelaUserAccountService iRelaUserAccountService;
    @Resource
    private IRela_spot_commentsService iRela_spot_commentsService;
    @Resource
    private IRela_user_commentsService iRela_user_commentsService;
    @Resource
    private IRelaUserSpotService relaUserSpot;


    @GetMapping
    @ApiOperation("业务评论表page查询")
    // @ApiImplicitParams({
    //     @ApiImplicitParam(name = "page", value = "当前页数", defaultValue = "1"),
    //     @ApiImplicitParam(name = "size", value = "每页个数", defaultValue = "10"),
    //     @ApiImplicitParam(name = "orderByColumn", value = "排序字段"),
    //     @ApiImplicitParam(name = "isDesc", value = "是否降序")
    // })
    public ResResult<IPage<BusCommentsVO>> getByPage(@Validated BusCommentsDTO busComments) {
        // TODO 分页查询
        IPage<BusCommentsVO> page = busCommentsService.selectByPage(getPage(), busComments);
        return ResResult.succ(page);
    }

    @PostMapping("/scenic/spot")
    @ApiOperation("业务-用户评论景点")
    public ResResult scenicSpotCommentsSave(@RequestBody @Validated ScenicSpotCommentsUserDTO dto, HttpServletRequest request) {
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

        BusComments comments = dto.getCommentsDTO().convert();
        BaseScenicSpotDTO scenicSpotDTO = dto.getScenicSpotDTO();

        log.info("评论景点-评论存库");
        busCommentsService.save(comments);
        comments.setId(null);
        BusComments busComments = busCommentsService.getOne(new QueryWrapper<>(comments));
        log.info("评论景点-景点评论关联存库");
        iRela_spot_commentsService.save(Rela_spot_comments.builder()
             .entity_id(scenicSpotDTO.getId())
             .comments_id(busComments.getId()).type(scenicSpotDTO.getType())
           .build());
        log.info("评论景点-用户评论关联存库");

        iRela_user_commentsService.save(
           Rela_user_comments.builder()
             .comments_id(busComments.getId())
             .user_id(baseUser.getId())
             .build()
         );
        log.info("评论景点-用户景点关联存库");
        relaUserSpot.save(RelaUserSpot.builder()
                .spot_id(scenicSpotDTO.getId()).
                user_id(baseUser.getId()).
                build());
        return ResResult.succ("新增成功");
    }

    @GetMapping("{id}")
    @ApiOperation("业务评论表查询(id)")
    public ResResult<BusCommentsVO> get(@PathVariable String id) {
        // TODO 查询
        boolean aNull = Objects.isNull(busCommentsService.getById(id));
        return ResResult.succ(aNull ? null : busCommentsService.getById(id).convert());
    }

    @PostMapping
    @ApiOperation("业务评论表新增")
    public ResResult add(@RequestBody @Validated BusCommentsDTO busComments) {
        // TODO 新增
        busCommentsService.save(busComments.convert());
        return ResResult.succ("新增成功");
    }

    @DeleteMapping("{id}")
    @ApiOperation("业务评论表删除")
    public ResResult del(@PathVariable String id, HttpServletRequest request) {
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
            // TODO 删除
            busCommentsService.removeById(id);
            return ResResult.succ("删除成功");
        }
        return ResResult.fail("您不是管理员,无此权限");

    }

    @PutMapping
    @ApiOperation("业务评论表修改")
    public ResResult update(@RequestBody @Validated BusCommentsDTO busComments) {
        // TODO 修改

        boolean done = busCommentsService.updateById(busComments.convert());

        return done?ResResult.succ("修改成功"):ResResult.fail("修改失败");
    }
}
