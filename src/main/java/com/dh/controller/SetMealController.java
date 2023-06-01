package com.dh.controller;

/*
    @author dh
    @creat 2023-04-25 19:25
    套餐管理
*/

import com.dh.common.constant.MessageConstant;
import com.dh.common.constant.RedisConstant;
import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.common.entity.Result;
import com.dh.common.untils.QiNiuUtils;
import com.dh.pojo.Setmeal;
import com.dh.pojo.SetmealCheckgroup;
import com.dh.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setMeal/")
public class SetMealController {

    @Resource
    private JedisPool jedisPool;

    @Autowired
    private SetmealService setmealService;

    //图片上传
    @RequestMapping("upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {

        String originalFilename = imgFile.getOriginalFilename();//获取原始文件名
        int index = originalFilename.lastIndexOf(".");//找到到最后一个点的位置
        String string = originalFilename.substring(index - 1);//获取到后缀名
        String fileName = UUID.randomUUID().toString()+string;//得到文件名称 随机生成随机生成名字

        try {
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);//将点击上传的文件名上传到redis数据库中
            QiNiuUtils.upload2Qiniu(imgFile.getBytes(),(fileName));
        }catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }
    //增加
    @PreAuthorize("hasAuthority('SETMEAL_ADD')")//权限校验
    @RequestMapping("add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){

        try {
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            //新增失败
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        //新增成功
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    //删除
    @PreAuthorize("hasAuthority('SETMEAL_DELETE')")//权限校验
    @RequestMapping("deleteById")
    public Result deleteById(Integer id) {
        try {
            setmealService.removeById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL+"！看看是不是绑定了检查组！");
        }
        setmealService.generateMobileStaticHtml();
        return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
    //分页
    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
    }
    //编辑回显数据
    @RequestMapping("findById")
    public Result findById(Integer id){
        Setmeal setmeal =setmealService.getById(id);
        if(setmeal != null){
            Result result = new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS);
            result.setData(setmeal);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
    }
    //编辑回显复选框
    @RequestMapping("findGroupIdsBySetMealId")
    public Result findGroupIdsBySetMealId(Integer id){
        try {
            List<SetmealCheckgroup> setMealCheckgroup = setmealService.findGroupIdsBySetMealId(id);
            List<Integer> ls = new ArrayList<>();
            for (SetmealCheckgroup list : setMealCheckgroup) {
                ls.add(list.getCheckgroupId());
            }return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, ls);
            //查询成功
        }catch (Exception e){
            //查询失败
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    //编辑
    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")//权限校验
    @RequestMapping("edit")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] ids){
        try {
            setmealService.edit(setmeal,ids);
        }catch (Exception e){
            //编辑失败
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
        //编辑成功
        return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    //查找所有的套餐返回到小程序
//    @RequestMapping("getSetMeal")
//    public Result getSetMeal(){
//
//        re
//    }
}

