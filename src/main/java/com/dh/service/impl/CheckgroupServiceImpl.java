package com.dh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dh.common.entity.PageResult;
import com.dh.common.entity.QueryPageBean;
import com.dh.mapper.CheckgroupCheckitemMapper;
import com.dh.mapper.CheckgroupMapper;
import com.dh.pojo.Checkgroup;
import com.dh.pojo.CheckgroupCheckitem;
import com.dh.service.CheckgroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class CheckgroupServiceImpl extends ServiceImpl<CheckgroupMapper, Checkgroup> implements CheckgroupService{

    @Autowired
    private CheckgroupMapper checkgroupMapper;

    @Autowired
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;
    //新增检查表
    @Override
    public void add(Checkgroup checkgroup, Integer[] checkitemIds) {
        checkgroupMapper.insert(checkgroup);
        for (Integer checkitemId : checkitemIds) {
            checkgroupCheckitemMapper.insert(new CheckgroupCheckitem(checkgroup.getId(),checkitemId));
        }
    }
    //编辑
    @Override
    public void edit(Checkgroup checkgroup, Integer[] checkitemIds) {
        //根据检查组id删除中间表数据（清理原有关联关系）
        checkgroupCheckitemMapper.deleteByCheckgroupId(checkgroup.getId());
        for (Integer checkitemId : checkitemIds) {
            checkgroupCheckitemMapper.insert(new CheckgroupCheckitem(checkgroup.getId(),checkitemId));
        }
        //更新检查组基本信息
        checkgroupMapper.updateById(checkgroup);
    }
    //返回编辑页面关联
    @Override
    public List<CheckgroupCheckitem> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkgroupCheckitemMapper.selectAllByCheckgroupId(id);
    }

    @Override
    public Checkgroup findById(Integer id) {
        return checkgroupMapper.selectById(id);
    }

    //分页
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        if(queryString!=null){
            currentPage=1;
        }
        PageHelper.startPage(currentPage,pageSize);
        Page<Checkgroup> Checkgroup = checkgroupMapper.selectByCondition(queryString);
        long total = Checkgroup.getTotal();
        List<Checkgroup> rows = Checkgroup.getResult();
        return new PageResult(total,rows);
    }





}





