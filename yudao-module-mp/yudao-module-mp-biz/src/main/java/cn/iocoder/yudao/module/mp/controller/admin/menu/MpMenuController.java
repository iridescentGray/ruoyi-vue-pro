package cn.iocoder.yudao.module.mp.controller.admin.menu;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.mp.controller.admin.menu.vo.MpMenuRespVO;
import cn.iocoder.yudao.module.mp.controller.admin.menu.vo.MpMenuSaveReqVO;
import cn.iocoder.yudao.module.mp.convert.menu.MpMenuConvert;
import cn.iocoder.yudao.module.mp.dal.dataobject.menu.MpMenuDO;
import cn.iocoder.yudao.module.mp.service.menu.MpMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 公众号菜单")
@RestController
@RequestMapping("/mp/menu")
@Validated
public class MpMenuController {

    @Resource
    private MpMenuService mpMenuService;

    @PostMapping("/save")
    @ApiOperation("保存公众号菜单")
    @PreAuthorize("@ss.hasPermission('mp:menu:save')")
    public CommonResult<Long> saveMenu(@Valid @RequestBody MpMenuSaveReqVO createReqVO) {
        return success(mpMenuService.saveMenu(createReqVO));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除公众号菜单")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('mp:menu:delete')")
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id) {
        mpMenuService.deleteMenu(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得公众号菜单")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('mp:menu:query')")
    public CommonResult<MpMenuRespVO> getMenu(@RequestParam("id") Long id) {
        MpMenuDO menu = mpMenuService.getMenu(id);
        return success(MpMenuConvert.INSTANCE.convert(menu));
    }

    @GetMapping("/list")
    @ApiOperation("获得公众号菜单列表")
    @ApiImplicitParam(name = "accountId", value = "公众号账号的编号", required = true, example = "10", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('mp:menu:query')")
    public CommonResult<List<MpMenuRespVO>> getMenuList(@RequestParam("accountId") Long accountId) {
        List<MpMenuDO> list = mpMenuService.getMenuListByAccountId(accountId);
        return success(MpMenuConvert.INSTANCE.convertList(list));
    }

}
