package com.dh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dh.common.constant.MessageConstant;
import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.common.entity.Result;
import com.dh.common.untils.DateUtils;
import com.dh.mapper.MemberMapper;
import com.dh.mapper.OrderMapper;
import com.dh.mapper.OrdersettingMapper;
import com.dh.pojo.Member;
import com.dh.pojo.Order;
import com.dh.pojo.Ordersetting;
import com.dh.service.OrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class
OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

    @Autowired
    private OrdersettingMapper ordersettingMapper;

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    //体检预约
    public Result order(Map map) throws Exception {
        //检查当前日期是否进行了预约设置
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        LambdaQueryWrapper<Ordersetting> ordersettingLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ordersettingLambdaQueryWrapper.eq(Ordersetting::getOrderdate, date);
        Ordersetting orderSetting = ordersettingMapper.selectOne(ordersettingLambdaQueryWrapper);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //检查预约日期是否预约已满
        int number = orderSetting.getNumber();//可预约人数
        int reservations = orderSetting.getReservations();//已预约人数
        if (reservations >= number) {
            //预约已满，不能预约
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //检查当前用户是否为会员，根据手机号判断
        String telephone = (String) map.get("telephone");
        LambdaQueryWrapper<Member> memberLambdaQueryWrapper = new LambdaQueryWrapper<>();
        memberLambdaQueryWrapper.eq(Member::getPhonenumber, telephone);
        Member member = memberMapper.selectOne(memberLambdaQueryWrapper);
        //防止重复预约
        if (member != null) {
            Integer memberId = member.getId();
            int setmealId = Integer.parseInt((String) map.get("setmealId"));


            LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderLambdaQueryWrapper.eq(Order::getMemberId, memberId)
                    .eq(Order::getOrderdate, date)
                    .eq(Order::getSetmealId, setmealId);

            List<Order> list = orderMapper.selectList(orderLambdaQueryWrapper);
            if (list != null && list.size() > 0) {
                //已经完成了预约，不能重复预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }

        //可以预约，设置预约人数加一
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        ordersettingMapper.updateById(orderSetting);

        if (member == null) {
            //当前用户不是会员，需要添加到会员表
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhonenumber(telephone);
            member.setIdcard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegtime(new Date());
            memberMapper.insert(member);
        }

//            保存预约信息到预约表
        Order order = new Order(member.getId(),date,(String) map.get("orderType"),Order.ORDERSTATUS_NO,Integer.parseInt((String) map.get("setmealId")));
        orderMapper.insert(order);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
    }
    //根据id查询预约信息，包括体检人信息、套餐信息
    public Map findById(Integer id) throws Exception {
        Map map = orderMapper.findById4Detail(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Order> page = orderMapper.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void changeStatus(Integer id) {
        orderMapper.updateOrderstatusById("已到诊",id);
    }

    @Override
    public void changeStatus1(Integer id) {
        orderMapper.updateOrderstatusById("未到诊",id);
    }

}




