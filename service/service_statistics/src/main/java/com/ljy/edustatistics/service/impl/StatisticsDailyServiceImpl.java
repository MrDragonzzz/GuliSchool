package com.ljy.edustatistics.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljy.commonutils.R;
import com.ljy.edustatistics.client.UcenterClient;
import com.ljy.edustatistics.entity.StatisticsDaily;
import com.ljy.edustatistics.mapper.StatisticsDailyMapper;
import com.ljy.edustatistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-12-01
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        //删除已存在的统计对象
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);
        //获取统计信息
        R r = ucenterClient.countRegister(day);
        Integer registerNum=(Integer) r.getData().get("count");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);
        baseMapper.insert(daily);

    }

    @Override
    public Map<String, Object> getShowDate(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper=new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        List<String> dateList=new ArrayList<>();
        List<Integer> numberList=new ArrayList<>();

        for (StatisticsDaily daily : staList) {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    numberList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    numberList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numberList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numberList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String,Object> map=new HashMap<>();
        map.put("dateList",dateList);
        map.put("numberList",numberList);
        return map;
    }
}
