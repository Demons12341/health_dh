package com.dh.controller;

import com.dh.common.constant.MessageConstant;
import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.common.entity.Result;
import com.dh.pojo.Checkitem;
import com.dh.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/*
    @author dh
    @creat 2023-04-23 13:50
    检查项管理
*/
@RestController
@RequestMapping("/checkItem/")
public class CheckItemController {
    @Autowired
    private CheckItemService checkItemService;
    //增加
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")//权限校验
    @RequestMapping("add")
    public Result add(@RequestBody Checkitem checkItem){
        try{
            checkItemService.save(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
    //分页
    @RequestMapping("pageQuery")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        return checkItemService.pageQuery(queryPageBean);
    }


    //删除
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @RequestMapping("deleteById")
    public Result deleteById(Integer id) {
        try {
//                checkItemService.removeById(id);
            checkItemService.removeById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL+"！看看是不是绑定了检查组！");
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    //编辑
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")//权限校验
    @RequestMapping("edit")
    public Result edit(@RequestBody Checkitem checkItem){


        try {
            checkItemService.updateById(checkItem);
        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }


    //回显到检查组增加
    @RequestMapping("findAll")
    public Result findAll(){
        try {
            List<Checkitem> list = checkItemService.list();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
}


