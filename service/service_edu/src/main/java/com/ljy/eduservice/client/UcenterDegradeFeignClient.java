package com.ljy.eduservice.client;

import com.ljy.commonutils.UcenterVo;
import org.springframework.stereotype.Component;


@Component
public class UcenterDegradeFeignClient implements  UcenterClient{
    @Override
    public UcenterVo getUserInfo(String id) {
        return null;
    }
}
