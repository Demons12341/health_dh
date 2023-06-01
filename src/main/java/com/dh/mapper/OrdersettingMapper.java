package com.dh.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dh.pojo.Ordersetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Entity com.dh.pojo.Ordersetting
 */
@Repository
public interface OrdersettingMapper extends BaseMapper<Ordersetting> {


    int updateNumberByOrderdate(@Param("number") Integer number, @Param("orderdate") Date orderdate);

    long findCountByOrderDate(Date orderdate);

    Ordersetting findByOrderdate(Date parseString2Date);

}




