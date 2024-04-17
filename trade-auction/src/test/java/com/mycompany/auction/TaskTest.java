package com.mycompany.auction;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.mycompany.auction.service.AuctionService;
import com.mycompany.auction.service.AuctionTaskService;
import org.junit.Test;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/17 14:26
 * @注释
 */
public class TaskTest extends BaseSpringBootTest{

    @Autowired
    private AuctionTaskService auctionTaskService;

    @Test
    public void testAuctionService(){
        LocalDateTime endLocalTime = LocalDateTimeUtil.offset(LocalDateTime.now(), 20, ChronoUnit.SECONDS);
        Date endTime = new DateTime(endLocalTime);
        try {
            log.info("任务已提交");
            auctionTaskService.startTask(endTime);
            TimeUnit.MINUTES.sleep(1);
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
