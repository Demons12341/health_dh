package com.dh.service;

import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.pojo.Member;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface MemberService extends IService<Member> {

    void add(Member member);

    Member findByTelephone(String telephone);

    List<Integer> findMemberCountByMonth(List<String> list);

    PageResult pageQuery(QueryPageBean queryPageBean);

}
