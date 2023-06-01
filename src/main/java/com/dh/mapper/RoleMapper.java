package com.dh.mapper;

import com.dh.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @Entity com.dh.pojo.Role
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    Set<Role> findByUserId(Integer userId);

}




