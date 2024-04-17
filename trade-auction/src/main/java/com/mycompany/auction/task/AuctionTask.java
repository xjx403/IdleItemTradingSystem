package com.mycompany.auction.task;

import com.mycompany.common.utils.RedisKeyUtil;
import com.trade.mbg.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/17 13:20
 * @注释
 */
@Slf4j
@Component
public class AuctionTask implements Job {
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${trade.host.url.mbg}")
    private String mbgHost;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
       log.info("test RESTFul API Use get object:{}" ,restTemplate.getForObject(mbgHost + "//product/select?id=4", Product.class).toString());
       log.info("test redis API user, get object:{}" ,redisTemplate.opsForValue().get(RedisKeyUtil.getAuctionUsersKey(9)).toString());
    }


}
