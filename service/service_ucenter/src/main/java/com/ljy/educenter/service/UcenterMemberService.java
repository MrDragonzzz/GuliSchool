package com.ljy.educenter.service;

import com.ljy.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljy.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);


    Integer countRegisterDay(String day);
}
