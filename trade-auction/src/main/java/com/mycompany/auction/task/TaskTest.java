package com.mycompany.auction.task;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/17 13:38
 * @注释
 */
public class TaskTest {
    public static void main(String[] args) throws Exception {
        //1.创建调度器
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        //2.创建JobDetails实例，并与DemoJob类绑定
        JobDetail job = JobBuilder.newJob(DemoJob.class)
                .withIdentity("job1", "group1")
                .build();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        Date startTime = calendar.getTime();
        //3. 构建Trigger实例，每隔30S执行一次
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .build();

        //4.执行，开启调度器
        scheduler.scheduleJob(job, trigger);
        System.out.println("scheduler start time: " + LocalDateTime.now());
        scheduler.start();

        TimeUnit.MINUTES.sleep(2);
        scheduler.shutdown();
        System.out.println(LocalDateTime.now());
    }
}
