package com.dh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dh.common.constant.RedisConstant;
import com.dh.common.entity.PageResult;
import com.dh.mapper.*;
import com.dh.pojo.*;
import com.dh.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService {

    @Resource
    private JedisPool jedisPool;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${spring.freemarker.out_put_path}")
    private String outPutPath;

    @Autowired
    private CheckgroupMapper checkgroupMapper;

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealCheckgroupMapper setmealCheckgroupMapper;

    @Autowired
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //添加到数据库中的setMeal
        setmealMapper.insert(setmeal);
        //添加套餐与检查组的关联
        for (Integer checkGroupId : checkgroupIds) {
            setmealCheckgroupMapper.insert(new SetmealCheckgroup(setmeal.getId(),checkGroupId));
        }
        //将图片名称放图Redis集合中
        if(setmeal.getImg()!=null)
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        //添加套餐后生成静态页面（套餐列表页面，套餐详情页面）
        generateMobileStaticHtml();
    }

    //生成当前方法所需的静态页面
    public void generateMobileStaticHtml(){
        //生成今天页面之前查询数据
        List<Setmeal> list = setmealMapper.selectList(null);
        //需要生成套餐列表静态页面
        generateMobileSetmealListHtml(list);
        //需要生成套餐详情静态页面
        generateMobileSetmealDetailHtml(list);

    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealMapper.findSetmealCount();
    }

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> list){
            Map map =new HashMap();
            //为模板提供数据
            map.put("setmealList",list);
            generateHtml("mobile_setmeal.ftl","m_setmeal.html",map);

    }

    //生成套餐详情静态页面(可能有多个)
    public void generateMobileSetmealDetailHtml(List<Setmeal> list){

        for (Setmeal setmeal:list){
            Map map = new HashMap();
            map.put("setmeal",findById(setmeal.getId()));
            generateHtml("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",map);
        }

    }

    //生成静态页面
    public void generateHtml(String templateName, String htmlPageName, Map map){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
        Template template = configuration.getTemplate(templateName);
        out = new FileWriter(new File(outPutPath+"/"+htmlPageName));
        template.process(map,out);
        out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealMapper.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<SetmealCheckgroup> findGroupIdsBySetMealId(Integer id) {
        return setmealCheckgroupMapper.selectAllBySetmealId(id);
    }


    @Override
    public void edit(Setmeal setmeal, Integer[] ids) {
        //根据套餐id删除中间表数据（清理原有关联关系）
        setmealCheckgroupMapper.deleteBySetmealId(setmeal.getId());
        for (Integer id : ids) {
            setmealCheckgroupMapper.insert(new SetmealCheckgroup(setmeal.getId(),id));
        }
        //更新套餐基本信息
        setmealMapper.updateById(setmeal);
        if(setmeal.getImg()!=null)
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

    }

    @Override
    public Setmeal findById(int id) {

        List<SetmealCheckgroup> list1 = setmealCheckgroupMapper.selectAllBySetmealId(id);
                List<Checkgroup> checkgroupList = new ArrayList<>();
                for(SetmealCheckgroup setmealCheckgroup :list1) {
                    checkgroupList.add(checkgroupMapper.selectById(setmealCheckgroup.getCheckgroupId()));
                    for (Checkgroup checkgroup : checkgroupList) {
                        List<Checkitem> checkitemList = new ArrayList<>();
                        List<CheckgroupCheckitem> list2 = checkgroupCheckitemMapper.selectAllByCheckgroupId(checkgroup.getId());
                        for (CheckgroupCheckitem checkgroupCheckitem : list2) {
                            Checkitem checkitem = checkItemMapper.selectById(checkgroupCheckitem.getCheckitemId());
                            checkitemList.add(checkitem);
                        }
                        checkgroup.setCheckItems(checkitemList);
                    }
                }
        Setmeal setmeal = setmealMapper.selectById(id);
        setmeal.setCheckGroups(checkgroupList);
        return setmeal;
    }

//    @Override
//    public List<Checkgroup> findGroupBySetMealId(int id) {
//        List<SetmealCheckgroup> list = setmealCheckgroupMapper.selectAllBySetmealId(id);
//                List<Checkgroup> checkgroupList = new ArrayList<>();
//        for(SetmealCheckgroup setmealCheckgroup :list){
//            checkgroupList.add(checkgroupMapper.selectById(setmealCheckgroup.getSetmealId()));
//        }
//        return checkgroupList;
//    }
}



