package com.dh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.mapper.CheckItemMapper;
import com.dh.pojo.Checkitem;
import com.dh.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    @author dh
    @creat 2023-04-23 13:59
*/
@Service
public class CheckItemServiceImpl extends ServiceImpl<CheckItemMapper,Checkitem> implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;

    //分页
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        if(queryPageBean.getQueryString()!=null){
            queryPageBean.setCurrentPage(1);
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Checkitem> checkItems = checkItemMapper.selectByCondition(queryPageBean.getQueryString());
        long total = checkItems.getTotal();
        List<Checkitem> rows = checkItems.getResult();
        return new PageResult(total,rows);
    }

}
