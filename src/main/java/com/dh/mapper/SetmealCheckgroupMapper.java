package com.dh.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dh.pojo.SetmealCheckgroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.dh.pojo.SetmealCheckgroup
 */
@Repository
public interface SetmealCheckgroupMapper extends BaseMapper<SetmealCheckgroup> {


    int deleteBySetmealId(@Param("setmealId") Integer setmealId);

    List<SetmealCheckgroup> selectAllBySetmealId(@Param("setmealId") Integer setmealId);

    List<SetmealCheckgroup> selectCheckgroupIdBySetmealId(@Param("setmealId") Integer setmealId);
}




