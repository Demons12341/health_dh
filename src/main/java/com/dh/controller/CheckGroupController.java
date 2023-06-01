package com.dh.controller;

import com.dh.common.constant.MessageConstant;
import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.common.entity.Result;
import com.dh.pojo.Checkgroup;
import com.dh.pojo.CheckgroupCheckitem;
import com.dh.service.CheckgroupCheckitemService;
import com.dh.service.CheckgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/*
    @author dh
    @creat 2023-04-24 11:24
    检查组管理
*/
@RestController
@RequestMapping("/checkgroup/")
public class CheckGroupController {

    @Autowired
    private CheckgroupService checkgroupService;

    @Autowired
    private CheckgroupCheckitemService checkgroupCheckitemService;

    //增加
    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")//权限校验
    @RequestMapping("add")
    public Result add(@RequestBody Checkgroup checkgroup, Integer[] ids){
        try {
            checkgroupService.add(checkgroup,ids);
        }catch (Exception e){
            //新增失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        //新增成功
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    //分页
    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkgroupService.pageQuery(queryPageBean);
    }
    //编辑回显数据
    @RequestMapping("findById")
    public Result findById(Integer id){
        Checkgroup checkGroup = checkgroupService.findById(id);
        if(checkGroup != null){
            Result result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS);
            result.setData(checkGroup);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
    //编辑回显复选框
    @RequestMapping("findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        try {
            List<CheckgroupCheckitem> checkgroupCheckitems = checkgroupService.findCheckItemIdsByCheckGroupId(id);
            List<Integer> ls = new ArrayList<>();
            for (CheckgroupCheckitem list : checkgroupCheckitems) {
                ls.add(list.getCheckitemId());
            }return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, ls);
            //查询成功
        }catch (Exception e){
            //查询失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    //编辑
    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")//权限校验
    @RequestMapping("edit")
    public Result edit(@RequestBody Checkgroup checkgroup, Integer[] ids){
        try {
            checkgroupService.edit(checkgroup,ids);
        }catch (Exception e){
            //编辑失败
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        //编辑成功
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
    //删除
    @PreAuthorize("hasAuthority('CHECKGROUP_DELETE')")//权限校验
    @RequestMapping("deleteById")
    public Result deleteById(Integer id) {
        try {
            checkgroupService.removeById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL+"！看看是不是绑定了套餐！");
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
    //查找 回显到套餐增加页面
    @RequestMapping("findAll")
    public Result findAll(){
        try {
            List<Checkgroup> checkgroupList = checkgroupService.list();
            System.out.println(checkgroupList);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroupList);
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
    }



