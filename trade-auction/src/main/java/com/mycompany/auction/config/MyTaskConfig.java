package com.mycompany.auction.config;

import lombok.SneakyThrows;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/17 14:07
 * @注释
 */
@Configuration
public class MyTaskConfig {
//    //1.创建调度器
//    SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//    Scheduler scheduler = schedulerFactory.getScheduler();
//    @Bean
//    public SchedulerFactory schedulerFactory(){
//        return new StdSchedulerFactory();
//    }
//
//    @SneakyThrows
//    @Bean
//    public Scheduler scheduler(){
//       return this.schedulerFactory().getScheduler();
//    }
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5); // 可以根据需要调整线程池大小
        scheduler.setThreadNamePrefix("TaskScheduler-");
        return scheduler;
    }

}
