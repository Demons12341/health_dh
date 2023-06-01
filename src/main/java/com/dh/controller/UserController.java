package com.dh.controller;

import com.dh.common.constant.MessageConstant;
import com.dh.common.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    @author dh
    @creat 2023-05-06 14:32
*/
@RestController
@RequestMapping("/user/")
public class UserController {

    @RequestMapping("getUsername")
    private Result getUsername(){
        try{
            //认证后通过session保存
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
