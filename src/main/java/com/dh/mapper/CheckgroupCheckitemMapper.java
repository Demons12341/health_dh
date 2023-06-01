package com.dh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dh.pojo.CheckgroupCheckitem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.dh.pojo.CheckgroupCheckitem
 */
@Repository
public interface CheckgroupCheckitemMapper extends BaseMapper<CheckgroupCheckitem> {

    List<CheckgroupCheckitem> selectAllByCheckgroupId(@Param("checkgroupId") Integer checkgroupId);

    int deleteByCheckgroupId(@Param("checkgroupId") Integer checkgroupId);

}




