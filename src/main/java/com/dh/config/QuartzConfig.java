//package com.dh.config;
//
//import com.dh.jobs.ClearImgJob;
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//
///*
//    @author dh
//    @creat 2023-04-26 1:30
//*/
//public class QuartzConfig {
//    @Value("${spring.quartz.cron}")
//    private String cron;
//
//    /**
//     * 创建定时任务
//     */
//    @Bean
//    public JobDetail quartzCartJobDetail() {
//        JobDetail jobDetail = JobBuilder.newJob(ClearImgJob.class)
//                .withIdentity("quartzCartJobDetail", "QUARTZ_CART")
//                .usingJobData("userName", "coderyeah")
//                .storeDurably()
//                .build();
//        return jobDetail;
//    }
//
//    /**
//     * 创建触发器
//     */
//    @Bean
//    public Trigger quartzCartJobTrigger() {
//        //每隔5秒执行一次
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
//        //创建触发器
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .forJob(quartzCartJobDetail())
//                .withIdentity("quartzCartJobTrigger", "QUARTZ_CART_JOB_TRIGGER")
//                .withSchedule(cronScheduleBuilder)
//                .build();
//        return trigger;
//    }
////    @Bean
////    public JobDetailFactoryBean jobDetail() {
////        JobDetailFactoryBean factory = new JobDetailFactoryBean();
////        factory.setJobClass(ClearImgJob.class);
////        factory.setGroup("myGroup");
////        factory.setName("myJob");
////        return factory;
////    }
////
////    @Bean
////    public SimpleTriggerFactoryBean trigger(JobDetail job) {
////        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
////        factory.setJobDetail(job);
////        factory.setStartDelay(0);
////        factory.setRepeatInterval(5000);
////        factory.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
////        return factory;
////    }
//
//
////    @Bean
////    public SchedulerFactoryBean schedulerFactoryBean() {
////        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
////        schedulerFactoryBean.setQuartzProperties(quartzProperties());
////        schedulerFactoryBean.setJobDetails(clearImgJobDetail());
////        schedulerFactoryBean.setTriggers(clearImgTrigger());
////        return schedulerFactoryBean;
////    }
////
////    @Bean
////    public Properties quartzProperties() {
////        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
////        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
////        Properties properties = null;
////        try {
////            propertiesFactoryBean.afterPropertiesSet();
////            properties = propertiesFactoryBean.getObject();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        return properties;
////    }
////
////    @Bean
////    public JobDetail clearImgJobDetail() {
////        return JobBuilder.newJob(ClearImgJob.class).withIdentity("clearImgJob", "clearImgJobGroup").storeDurably().build();
////    }
////
////    @Bean
////    public Trigger clearImgTrigger() {
////        return TriggerBuilder.newTrigger().withIdentity("clearImgTrigger", "clearImgTriggerGroup").forJob(clearImgJobDetail())
////                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * ? * * *")).build();
////    }
////
////    @Bean
////    public Scheduler scheduler() {
////        return schedulerFactoryBean().getScheduler();
////    }
//
//}
