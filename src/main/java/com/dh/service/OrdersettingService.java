package com.dh.service;

import com.dh.pojo.Ordersetting;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface OrdersettingService extends IService<Ordersetting> {

    void add(List<Ordersetting> data);

    List<Map> getOrderSettingByMonth(String data);

    void updateByDate(Ordersetting ordersetting);

}
