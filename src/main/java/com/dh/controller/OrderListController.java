package com.dh.controller;

import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.common.entity.Result;
import com.dh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    @author dh
    @creat 2023-05-08 12:56
*/
@RestController
@RequestMapping("/orderlist")
public class OrderListController {

    @Autowired
    private OrderService orderService;
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return orderService.pageQuery(queryPageBean);
    }

    @RequestMapping("/changeStatus")
    public Result changeStatus(Integer id){
        orderService.changeStatus(id);
        return new Result(true, "已设置为已到诊");
    }

    @RequestMapping("/changeStatus1")
    public Result changeStatus1(Integer id){
        orderService.changeStatus1(id);
        return new Result(true, "已设置为未到诊");
    }
}
