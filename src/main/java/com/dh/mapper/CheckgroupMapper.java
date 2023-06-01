package com.dh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dh.pojo.Checkgroup;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.dh.pojo.Checkgroup
 */
@Repository
public interface CheckgroupMapper extends BaseMapper<Checkgroup> {


    Page<Checkgroup> selectByCondition(String queryString);




}




