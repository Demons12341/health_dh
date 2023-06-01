package com.dh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dh.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Entity com.dh.pojo.Setmeal
 */
@Repository
public interface SetmealMapper extends BaseMapper<Setmeal> {

    Page<Setmeal> selectByCondition(String queryString);


    List<Map<String, Object>> findSetmealCount();

}




