package com.dh.controller;

import com.dh.common.constant.MessageConstant;
import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.common.entity.Result;
import com.dh.common.untils.DateUtils;
import com.dh.pojo.Member;
import com.dh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    @author dh
    @creat 2023-05-06 19:34
*/
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return memberService.pageQuery(queryPageBean);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Member member) {
        member.setRegtime(DateUtils.getToday());
        try {
            System.out.println(member);
            memberService.add(member);
            return new Result(true, MessageConstant.ADD_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MEMBER_FAIL);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            memberService.removeById(id);
            return new Result(true, MessageConstant.DELETE_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MEMBER_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Member member) {

        try {
            memberService.updateById(member);
            return new Result(true, MessageConstant.EDIT_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_MEMBER_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Member member = memberService.getById(id);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, member);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}


