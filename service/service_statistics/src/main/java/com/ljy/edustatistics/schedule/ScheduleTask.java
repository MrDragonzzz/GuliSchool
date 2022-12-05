package com.ljy.edustatistics.schedule;


import com.ljy.edustatistics.service.StatisticsDailyService;
import com.ljy.edustatistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataUnit;

import java.util.Date;

@Component
public class ScheduleTask {
    @Autowired
    private StatisticsDailyService staService;
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
