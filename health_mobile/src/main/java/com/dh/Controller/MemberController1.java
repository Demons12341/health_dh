package com.dh.Controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dh.common.constant.MessageConstant;
import com.dh.common.constant.RedisMessageConstant;
import com.dh.common.entity.Result;
import com.dh.pojo.Member;
import com.dh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/*
    @author dh
    @creat 2023-05-04 20:39
*/
@RestController
@RequestMapping("/member/")
public class MemberController1 {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private MemberService memberService;

    //使用手机号和验证码登录
    @RequestMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        //从Redis中获取缓存的验证码
        String codeInRedis =
                jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_LOGIN);
        if(codeInRedis == null || !codeInRedis.equals(validateCode)){
            //验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else{
            //验证码输入正确
            //判断当前用户是否为会员

            Member member = memberService.findByTelephone(telephone);
            LambdaQueryWrapper<Member> memberLambdaQueryWrapper = new LambdaQueryWrapper<>();
            memberLambdaQueryWrapper.eq(Member::getPhonenumber,telephone);
            if(member == null){
                //当前用户不是会员，自动完成注册
                member = new Member();
                member.setPhonenumber(telephone);
                member.setRegtime(new Date());
                memberService.add(member);
            }
            //登录成功
            //写入Cookie，跟踪用户
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");//路径
            cookie.setMaxAge(60*60*24*30);//有效期30天
            response.addCookie(cookie);
            //保存会员信息到Redis中
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone,60*30,json);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }
    }
}
