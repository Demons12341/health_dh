package com.dh.Controller;

/*
    @author dh
    @creat 2023-05-04 13:00
*/

import com.aliyuncs.exceptions.ClientException;
import com.dh.common.constant.MessageConstant;
import com.dh.common.constant.RedisMessageConstant;
import com.dh.common.entity.Result;
import com.dh.common.untils.SMSUtils;
import com.dh.pojo.Order;
import com.dh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("submit")
    public Result submitOrder(@RequestBody Map map) {

        String telephone = (String) map.get("telephone");
        //从Redis中获取缓存的验证码，key为手机号+RedisConstant.SENDTYPE_ORDER
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        //校验手机验证码
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        Result result = null;
        //调用体检预约服务
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            //预约失败
            return result;
        }
        if (result.isFlag()) {
            //预约成功，发送短信通知
            try {
                SMSUtils.sendShortMessage2(SMSUtils.ORDER_NOTICE, telephone,null);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping("findById")
    public Result findById(Integer id){
        try {
             Map map = orderService.findById(id);
             return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
