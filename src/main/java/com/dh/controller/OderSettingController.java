package com.dh.controller;

import com.dh.common.constant.MessageConstant;
import com.dh.common.entity.Result;
import com.dh.common.untils.POIUtils;
import com.dh.pojo.Ordersetting;
import com.dh.service.OrdersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
    @author dh
    @creat 2023-04-28 0:06
*/
@RestController
@RequestMapping("/ordersetting/")
public class OderSettingController {

    @Autowired
    private OrdersettingService ordersettingService;

    @RequestMapping("upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<Ordersetting> data = new ArrayList<>();
            for (
                    String[] strings :list
            ){
                String orderData = strings[0];
                String number = strings[1];
                Ordersetting ordersetting = new Ordersetting(new Date(orderData), Integer.parseInt(number));
                data.add(ordersetting);
            }
            ordersettingService.add(data);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        }catch (IOException e){
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }


    @RequestMapping("getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String data) {//格式为yyyy-MM

        try {
            List<Map> list =  ordersettingService.getOrderSettingByMonth(data);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("editNumberByDate")
    public Result editNumberByDate(@RequestBody Ordersetting ordersetting){
        System.out.println(ordersetting);
        try {
            ordersettingService.updateByDate(ordersetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

}
