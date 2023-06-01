package com.dh.service.impl;

import com.dh.pojo.Permission;
import com.dh.pojo.Role;
import com.dh.pojo.User;
import com.dh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
    @author dh
    @creat 2023-05-06 10:07
*/
@Service
public class SpringSecurityUserService  implements UserDetailsService {

    @Autowired
    private UserService userService;

    //根据用户名查询数据库获取用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);
        if (user ==null){
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for (Role role :roles){
                //遍历角色集合，为用户授权角色
                list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                //遍历权限集合，位用户授权
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);

        return securityUser;
    }
}
