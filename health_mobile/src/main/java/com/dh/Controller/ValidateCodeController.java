package com.dh.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.dh.common.constant.MessageConstant;
import com.dh.common.constant.RedisMessageConstant;
import com.dh.common.entity.Result;
import com.dh.common.untils.SMSUtils;
import com.dh.common.untils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/*
    @author dh
    @creat 2023-05-04 12:42
*/
@RequestMapping("/validateCode/")
@RestController
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    //预约验证码
    @RequestMapping("send4Order")
    public Result send4Order(String telephone){
        Integer code = ValidateCodeUtils.generateValidateCode(4);//生成4位数字验证码
        try {
            //发送短信
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            //验证码发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("发送的手机验证码为：" + code);
        //将生成的验证码缓存到redis 保存五分钟  //十分钟用于测试
        jedisPool.getResource().setex(
                telephone + RedisMessageConstant.SENDTYPE_ORDER,20000 * 60,code.toString());
        //验证码发送成功
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    //登录验证码
    @RequestMapping("send4Login")
    public Result send4Login(String telephone){
        Integer code = ValidateCodeUtils.generateValidateCode(6);//生成6位数字验证码
        try {
            //发送短信
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            //验证码发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("发送的手机验证码为：" + code);
        //将生成的验证码缓存到redis 保存五分钟  //十分钟用于测试
        jedisPool.getResource().setex(
                telephone + RedisMessageConstant.SENDTYPE_LOGIN,20000 * 60,code.toString());
        //验证码发送成功
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}
