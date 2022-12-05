package com.ljy.msmservice.controller;


import com.ljy.commonutils.R;
import com.ljy.msmservice.service.EmailService;
import com.ljy.msmservice.service.MsmService;
import com.ljy.msmservice.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
 
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private EmailService emailService;

    @GetMapping("send/{email}")
    public R sendMsm(@PathVariable("email") String email){
        //1 从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //2 如果redis获取 不到，进行邮箱发送
        //生成随机码

        String checkCode = RandomUtil.getFourBitRandom();

        Map<String,Object> params = new HashMap<>();
        params.put("code",checkCode);

        try {
            emailService.send(email, params);

            redisTemplate.opsForValue().set(email,checkCode,2, TimeUnit.MINUTES);
            //        return checkCode;
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("邮件发送失败");
        }

    }
}
