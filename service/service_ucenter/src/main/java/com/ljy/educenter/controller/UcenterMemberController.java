package com.ljy.educenter.controller;


import com.ljy.commonutils.JWTUtils;
import com.ljy.commonutils.R;
import com.ljy.commonutils.UcenterVo;
import com.ljy.educenter.entity.UcenterMember;
import com.ljy.educenter.entity.vo.RegisterVo;
import com.ljy.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-28
 */
@RestController
@RequestMapping("/educenter/member")
 
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @PostMapping ("login")
    public R loginUser(@RequestBody UcenterMember member){
       String token= memberService.login(member);
       return R.ok().data("token",token);
    }

    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberId = JWTUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);

    }

    @PostMapping("getUserInfo/{id}")
    public UcenterVo getUserInfo(@PathVariable  String id){
        UcenterMember member = memberService.getById(id);
        UcenterVo ucenterVo = new UcenterVo();
        BeanUtils.copyProperties(member,ucenterVo);
        return ucenterVo;
    }

    @PostMapping("countRegister/{day}")
    public R countRegister(@PathVariable("day") String day ){
        Integer count=memberService.countRegisterDay(day);
        return R.ok().data("count",count);
    }

}

