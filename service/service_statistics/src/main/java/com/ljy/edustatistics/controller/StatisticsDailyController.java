package com.ljy.edustatistics.controller;


import com.ljy.commonutils.R;
import com.ljy.edustatistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-12-01
 */
@RestController
@RequestMapping("/edustatistics/sta")
 
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){

        staService.registerCount(day);
        return R.ok();
    }

    @GetMapping("showDate/{type}/{begin}/{end}")
    public R showDate(@PathVariable String type,@PathVariable String begin,
                      @PathVariable String end){
        Map<String,Object> map=staService.getShowDate(type,begin,end);
        return R.ok().data(map);
    }

}

