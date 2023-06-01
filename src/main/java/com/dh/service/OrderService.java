package com.dh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.common.entity.Result;
import com.dh.pojo.Order;

import java.util.Map;

/**
 *
 */
public interface OrderService extends IService<Order> {

    public Result order(Map map) throws Exception;

    public Map findById(Integer id) throws Exception;

    PageResult pageQuery(QueryPageBean queryPageBean);

    void changeStatus(Integer id);

    void changeStatus1(Integer id);

}
