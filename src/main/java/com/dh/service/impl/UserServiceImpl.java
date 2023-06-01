package com.dh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dh.mapper.PermissionMapper;
import com.dh.mapper.RoleMapper;
import com.dh.mapper.UserMapper;
import com.dh.pojo.Permission;
import com.dh.pojo.Role;
import com.dh.pojo.User;
import com.dh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public User findByUsername(String username) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(userLambdaQueryWrapper);//查询用户基本信息 不包含用户角色
        //根据用户id查询角色
        Integer userId = user.getId();
        Set<Role> roles = roleMapper.findByUserId(userId);
        for (Role role : roles) {
            Integer roleId = role.getId();
            Set<Permission> permissions = permissionMapper.findByRoleId(roleId);
            role.setPermissions(permissions);
            //根据角色查权限
        }
        user.setRoles(roles);
        return user;
    }
}




