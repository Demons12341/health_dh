//package com.dh.jobs;
//
//import com.dh.common.constant.RedisConstant;
//import com.dh.common.untils.QiNiuUtils;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//import redis.clients.jedis.JedisPool;
//
//import java.util.Set;
//
///*
//    @author dh
//    @creat 2023-04-26 11:56
//*/
//public class ClearImgJob extends QuartzJobBean {
//    @Autowired
//    private JedisPool jedisPool;
//    public void clearImg(){
//        //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
//        Set<String> set =
//                jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
//                        RedisConstant.SETMEAL_PIC_DB_RESOURCES);
//        if(set != null){
//            for (String picName : set) {
//                //删除七牛云服务器上的图片
//                QiNiuUtils.deleteFileFromQiniu(picName);
//                //从Redis集合中删除图片名称
//                jedisPool.getResource().
//                        srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
//            }
//        }
//    }
//
//    @Override
//    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//
//    }
//}
