package com.dh.mapper;

import com.dh.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @Entity com.dh.pojo.Permission
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    Set<Permission> findByRoleId(Integer roleId);

}




