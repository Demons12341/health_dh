package com.dh.Controller;

import com.dh.common.constant.MessageConstant;
import com.dh.common.entity.Result;
import com.dh.pojo.Setmeal;
import com.dh.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    @author dh
    @creat 2023-04-28 22:28
*/
@RequestMapping("/setmeal/")
@RestController
public class MealController {

    @Autowired
    private SetmealService setmealService;

    @RequestMapping("getSetMeal")
    public Result findAll(){
        try {
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmealService.list());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_LIST_FAIL);
        }

    }

    @RequestMapping("findById")
    public Result findById(int id){
        try{
            Setmeal setmeal = setmealService.findById(id);
            System.out.println(setmeal);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
//    @RequestMapping("findGroupBySetMealId")
//    public Result findGroupBySetMealId(int id){
//
//        try{
//            List<Checkgroup> checkgroup = setmealService.findGroupBySetMealId(id);
//            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroup);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
//        }
//    }
}
