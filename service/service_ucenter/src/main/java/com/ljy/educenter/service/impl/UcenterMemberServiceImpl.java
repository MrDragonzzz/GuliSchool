package com.ljy.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljy.commonutils.JWTUtils;
import com.ljy.commonutils.MD5;
import com.ljy.educenter.entity.UcenterMember;
import com.ljy.educenter.entity.vo.RegisterVo;
import com.ljy.educenter.mapper.UcenterMemberMapper;
import com.ljy.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljy.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-28
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        String email = member.getEmail();
        String password = member.getPassword();
        if(StringUtils.isEmpty(email)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"不能为空");
        }

        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("email",email);
        UcenterMember emailMember = getOne(wrapper);

        if(emailMember==null){
            throw new GuliException(20001,"邮箱不存在");
        }

        if(!MD5.encrypt(password).equals(emailMember.getPassword())){
            throw new GuliException(20001,"密码错误");
        }

        if(emailMember.getIsDisabled()){
            throw new GuliException(20001,"用户黑名单");
        }

        String token = JWTUtils.getJwtToken(emailMember.getId(), emailMember.getNickname());

        return token;

    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String email = registerVo.getEmail();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(email)||StringUtils.isEmpty(password)
                ||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(code)){
            throw new GuliException(20001,"不能为空");
        }
        String redisCode = redisTemplate.opsForValue().get(email);
        if(!code.equals(redisCode)){
            throw new GuliException(20001,"验证码错误");
        }
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("email",email);
        int count = count(wrapper);
        if(count>0){
            throw new GuliException(20001,"重复注册");
        }
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setEmail(email);
        ucenterMember.setNickname(nickname);
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/MQ7qUmCprK9am16M1Ia1Cs3RK0qiarRrl9y8gsssBjIZeS2GwKSrnq7ZYhmrzuzDwBxSMMAofrXeLic9IBlW4M3Q/132");
        ucenterMember.setIsDisabled(false);
        save(ucenterMember);
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
