package com.dh.service;

import com.dh.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserService extends IService<User> {

    User findByUsername(String username);

}
