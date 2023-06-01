package com.dh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.common.untils.MD5Utils;
import com.dh.mapper.MemberMapper;
import com.dh.pojo.Member;
import com.dh.service.MemberService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member>
    implements MemberService{


    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void add(Member member) {
        String password =member.getPassword();
        if(password != null){
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }
        memberMapper.insert(member);
    }

    @Override
    public Member findByTelephone(String telephone) {
        LambdaQueryWrapper<Member> memberLambdaQueryWrapper = new LambdaQueryWrapper<>();
        memberLambdaQueryWrapper.eq(Member::getPhonenumber,telephone);
        return memberMapper.selectOne(memberLambdaQueryWrapper);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> month) {
        List<Integer> list = new ArrayList<>();
        for(String m : month){
            LambdaQueryWrapper<Member> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(Member::getRegtime,m);
            Integer count = Math.toIntExact(memberMapper.selectCount(lambdaQueryWrapper));
            list.add(count);
        }
        return list;
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Member> page = memberMapper.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }
}




