package com.dh.service.impl;

import com.dh.common.untils.DateUtils;
import com.dh.mapper.MemberMapper;
import com.dh.mapper.OrderMapper;
import com.dh.service.reportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    @author dh
    @creat 2023-05-06 16:45
*/
@Service
public class reportServiceImpl implements reportService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public Map<String, Object> getBusinessReport() throws Exception {
        //获得当前日期
        Date today = DateUtils.getToday();
        String today1 = DateUtils.parseDate2String(today);
        //获得本周一的日期
        Date thisWeekMonday = DateUtils.getThisWeekMonday();
        //获得本月第一天的日期
        Date firstDay4ThisMonth = DateUtils.getFirstDay4ThisMonth();
        //总会员数
        Integer totalMember = Math.toIntExact(memberMapper.selectCount(null));
        //今日新增会员数
        Integer todayNewMember = memberMapper.countByRegtime(today);
        //本周新增会员数
        Integer thisWeekNewMember = memberMapper.countByRegtimeBetween(thisWeekMonday,today);
        //本月新增会员数
        Integer thisMonthNewMember = memberMapper.countByRegtimeBetween(firstDay4ThisMonth,today);
        //今日预约数
        Integer todayOrderNumber = orderMapper.countByOrderdate(today);
        //本周预约数
        Integer thisWeekOrderNumber = orderMapper.countByOrderdateBetween(thisWeekMonday,today);
        //本月预约数
        Integer thisMonthOrderNumber = orderMapper.countByOrderdateBetween(firstDay4ThisMonth,today);
        //今日到诊数
        Integer todayVisitsNumber = orderMapper.countByOrderdateAndOrderstatus(today,"已到诊");
        //本周到诊数
        Integer thisWeekVisitsNumber = orderMapper.countByOrderdateBetweenAndOrderstatus(thisWeekMonday,today,"已到诊");
        //本月到诊数
        Integer thisMonthVisitsNumber = orderMapper.countByOrderdateBetweenAndOrderstatus(firstDay4ThisMonth,today,"已到诊");
        //热门套餐（取前4）
        List<Map> hotSetmeal = orderMapper.findHotSetmeal();
        Map<String,Object> result = new HashMap<>();
        result.put("reportDate",today1);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);

        return result;
    }
    }
