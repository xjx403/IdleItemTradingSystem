package com.mycompany.auction.task;

import cn.hutool.core.date.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/17 13:39
 * @注释
 */
public class DemoJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(LocalDateTime.now() + "my demo job is executed!");
        System.out.println(DateUtil.toLocalDateTime(jobExecutionContext.getTrigger().getStartTime()));
    }
}
