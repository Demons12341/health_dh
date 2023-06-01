package com.dh.service;

import com.dh.common.entity.*;
import com.dh.pojo.Checkgroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dh.pojo.CheckgroupCheckitem;

import java.util.List;

/**
 *
 */
public interface CheckgroupService extends IService<Checkgroup> {

    void add(Checkgroup checkgroup, Integer[] ids);

    PageResult pageQuery(QueryPageBean queryPageBean);

    void edit(Checkgroup checkgroup, Integer[] ids);

    List<CheckgroupCheckitem> findCheckItemIdsByCheckGroupId(Integer id);

    Checkgroup findById(Integer id);
}
