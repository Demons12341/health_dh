package com.dh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dh.mapper.OrdersettingMapper;
import com.dh.pojo.Ordersetting;
import com.dh.service.OrdersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class OrdersettingServiceImpl extends ServiceImpl<OrdersettingMapper, Ordersetting>
    implements OrdersettingService{

    @Autowired
    private OrdersettingMapper ordersettingMapper;

    //上预约文件
    @Override
    public void add(List<Ordersetting> data) {
        if (data != null && data.size() > 0) {
            for (Ordersetting ordersetting : data) {
//                判断数据库中是否有该日期的数据 如果有更新 如果没有 添加
                LambdaQueryWrapper<Ordersetting> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(Ordersetting::getOrderdate, ordersetting.getOrderdate());
                if(ordersettingMapper.selectOne(lambdaQueryWrapper)==null){ordersettingMapper.insert(ordersetting);}
                else {
                        ordersettingMapper.updateNumberByOrderdate(ordersetting.getNumber(),ordersetting.getOrderdate());
                    }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String data) {//yyyy-MM

        List<Map> result = new ArrayList<>();
        LambdaQueryWrapper<Ordersetting> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Ordersetting::getOrderdate,data);
        List<Ordersetting> ordersettings = ordersettingMapper.selectList(lambdaQueryWrapper);
        for (Ordersetting ordersettingList :ordersettings){
                Map<String,Object> m =new HashMap<>();
                m.put("date",ordersettingList.getOrderdate().getDate());//日期数字(几号)
                m.put("number",ordersettingList.getNumber());
                m.put("reservations",ordersettingList.getReservations());
                result.add(m);
        }

        return result;
    }

    @Override
    public void updateByDate(Ordersetting ordersetting) {

        LambdaQueryWrapper<Ordersetting> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Ordersetting::getOrderdate, ordersetting.getOrderdate());
        if(ordersettingMapper.selectOne(lambdaQueryWrapper)==null){ordersettingMapper.insert(ordersetting);}
        else {
            ordersettingMapper.updateNumberByOrderdate(ordersetting.getNumber(),ordersetting.getOrderdate());
        }
    }
}




