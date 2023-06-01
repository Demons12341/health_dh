package com.dh.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dh.pojo.Checkitem;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;


/*
    @author dh
    @creat 2023-03-06 16:27
*/
@Repository
public interface CheckItemMapper extends BaseMapper<Checkitem> {


    Page<Checkitem> selectByCondition(String queryString);

}
