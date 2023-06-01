package com.dh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dh.common.entity.PageResult;
import com.dh.pojo.Setmeal;
import com.dh.pojo.SetmealCheckgroup;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface SetmealService extends IService<Setmeal> {

    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    List<SetmealCheckgroup> findGroupIdsBySetMealId(Integer id);

    void edit(Setmeal setmeal, Integer[] ids);

    Setmeal findById(int id);

    void generateMobileStaticHtml();

    List<Map<String, Object>> findSetmealCount();



//    List<Checkgroup> findGroupBySetMealId(int id);

}
