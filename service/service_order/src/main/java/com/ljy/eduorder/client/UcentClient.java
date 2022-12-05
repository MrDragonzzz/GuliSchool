package com.ljy.eduorder.client;

import com.ljy.commonutils.UcenterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter")
public interface UcentClient {
    @PostMapping("/educenter/member/getUserInfo/{id}")
    public UcenterVo getUserInfo(@PathVariable("id") String id);
}
