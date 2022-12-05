package com.ljy.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljy.commonutils.R;
import com.ljy.educms.entity.CrmBanner;
import com.ljy.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/educms/banner")
 
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable("page") long page,@PathVariable("limit") long limit){
        Page<CrmBanner> pageBanner=new Page<>(page,limit);
        bannerService.page(pageBanner,null);

        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }
    @ApiOperation(value = "新增Banner")
    @PostMapping("save")
    public R save(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }
    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }


}

