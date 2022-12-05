package com.ljy.eduservice.client;


import com.ljy.commonutils.UcenterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterDegradeFeignClient.class)
public interface UcenterClient {
    @PostMapping("/educenter/member/getUserInfo/{id}")
    public UcenterVo getUserInfo(@PathVariable("id") String id);

}
