package com.mycompany.auction.component;

import com.mycompany.auction.entity.AuctionProductVO;
import com.mycompany.common.utils.RedisKeyUtil;
import com.trade.mbg.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/17 16:22
 * @注释
 */
@Slf4j
@Component
public class MyAuctionTaskExecutor implements SchedulingConfigurer {
    @Resource
    private TaskScheduler taskScheduler;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${trade.host.url.mbg}")
    private String mbgHost;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

    }

    public void startTask(Date startTime){
        taskScheduler.schedule(() -> {
            log.info("test RESTFul API Use get object:{}" ,restTemplate.getForObject(mbgHost + "//product/select?id=4", Product.class).toString());
            log.info("test redis API user, get object:{}" ,(AuctionProductVO)redisTemplate.opsForValue().get(RedisKeyUtil.getAuctionProductKey(9)));
        }, startTime);
    }
}
