package com.dh.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dh.pojo.Order;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Entity com.dh.pojo.Order
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {

    Map findById4Detail(Integer id);

    int countByOrderdate(@Param("orderdate") Date orderdate);

    int countByOrderdateBetween(@Param("beginOrderdate") Date beginOrderdate, @Param("endOrderdate") Date endOrderdate);

    int countByOrderdateAndOrderstatus(@Param("orderdate") Date orderdate, @Param("orderstatus") String orderstatus);

    int countByOrderdateBetweenAndOrderstatus(@Param("beginOrderdate") Date beginOrderdate, @Param("endOrderdate") Date endOrderdate, @Param("orderstatus") String orderstatus);

    List<Map> findHotSetmeal();

    Page<Order> selectByCondition(String queryString);

    int updateOrderstatusById(@Param("orderstatus") String orderstatus, @Param("id") Integer id);


}




