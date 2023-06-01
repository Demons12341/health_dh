package com.dh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.pojo.Checkitem;

/*
    @author dh
    @creat 2023-04-23 13:53
*/
public interface CheckItemService extends IService<Checkitem> {

    PageResult pageQuery(QueryPageBean queryPageBean);


}
