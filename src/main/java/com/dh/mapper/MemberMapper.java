package com.dh.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dh.pojo.Member;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Entity com.dh.pojo.Member
 */
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    List<Member> selectByPhonenumber(@Param("phonenumber") String phonenumber);

    //今日新增会员数
    int countByRegtime(@Param("regtime") Date regtime);


    int countByRegtimeBetween(@Param("beginRegtime") Date beginRegtime, @Param("endRegtime") Date endRegtime);

    Page<Member> selectByCondition(String queryString);

}




