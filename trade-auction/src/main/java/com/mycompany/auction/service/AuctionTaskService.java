package com.mycompany.auction.service;

import cn.hutool.core.date.DateTime;
import com.mycompany.auction.entity.AuctionProductVO;
import com.mycompany.auction.task.AuctionTask;
import com.mycompany.common.utils.RedisKeyUtil;
import com.mycompany.common.value_set.PayWayCode;
import com.trade.mbg.entity.Order;
import com.trade.mbg.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/17 14:17
 * @注释
 */
@Slf4j
@Service
public class AuctionTaskService implements SchedulingConfigurer {
    @Resource
    private TaskScheduler taskScheduler;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${trade.host.url.mbg}")
    private String mbgHost;
    @Value("${trade.host.url.order}")
    private String orderHost;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

    }

    public void startTask(Date startTime){
        taskScheduler.schedule(() -> {
            log.info("test RESTFul API Use get object:{}" ,restTemplate.getForObject(mbgHost + "//product/select?id=4", Product.class).toString());
            log.info("test redis API user, get object:{}" ,(AuctionProductVO)redisTemplate.opsForValue().get(RedisKeyUtil.getAuctionProductKey(9)));
        }, startTime);
    }


    private  long parseUserId(String str) {
        // 匹配字符串中的数字部分，即用户ID
        Pattern pattern = Pattern.compile("用户(\\d+)");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String userIdStr = matcher.group(1);
            return Long.parseLong(userIdStr);
        } else {
//            throw new IllegalArgumentException("无法解析出用户ID，输入：" + str);
            return  -1;
        }
    }

    private boolean delKey(long productId){
        return redisTemplate.delete(RedisKeyUtil.getAuctionProductKey(productId));
    }
    private boolean initOrder(Long productId, Long userId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("userId", userId.toString());
        valueMap.add("productId", productId.toString());
        valueMap.add("payWay", String.valueOf(PayWayCode.SELF_TRADING.getCode()));

        valueMap.add("remark", "这是拍卖商品的相关订单");
        org.springframework.http.HttpEntity<MultiValueMap> entity = new HttpEntity<>(valueMap, headers);
        ResponseEntity<Order> responseEntity = restTemplate.postForEntity(mbgHost + "/order/generate", entity, Order.class);
        Order order = responseEntity.getBody();
        return order != null;
    }

    public void startTask(AuctionProductVO productVO){
        //参数合法性校验
        if (productVO.getEndTime() == null) return;
        Date startTime = new DateTime(productVO.getEndTime());
        log.info("提交延迟任务，处理拍卖商品：{}", productVO);
        //开启延迟任务，在倒计时结束后自动生成订单，删除商品redis缓存
        taskScheduler.schedule(() -> {
            //参数校验
            AuctionProductVO newProductVO = (AuctionProductVO)redisTemplate.opsForValue().get(RedisKeyUtil.getAuctionProductKey(productVO.getProductId()));
            log.info("开始执行任务，处理商品：", productVO.getProductId());
            if (newProductVO.getBidHistory() == null || newProductVO.getBidHistory().size() == 0) return;
            List<String> bidHistory = newProductVO.getBidHistory();
            String lastBid = bidHistory.get(bidHistory.size() - 1);
            Long userId = parseUserId(lastBid);
            log.info("parse bidString:{} get userId:{}", lastBid, userId);
            if (userId == -1) return;

            boolean delKeyEnd = delKey(newProductVO.getProductId());
            if (!delKeyEnd) {
                log.info("delete redis key for {} failed!" , newProductVO.getProductId());
                return;
            }

            boolean initOrderEnd = initOrder(newProductVO.getProductId(), userId);
            if (!initOrderEnd) {
                redisTemplate.opsForValue().set(RedisKeyUtil.getAuctionProductKey(newProductVO.getProductId()), newProductVO);
                log.info("init order failed, input params include: {}, {}",newProductVO.getProductId(), userId);
                return;
            }

            redisTemplate.opsForList().remove(RedisKeyUtil.getAuctionProductKeyHead() + "list", 1, RedisKeyUtil.getAuctionProductKey(newProductVO.getProductId()));
            log.info("test redis API user, get object:{}" ,(AuctionProductVO)redisTemplate.opsForValue().get(RedisKeyUtil.getAuctionProductKey(newProductVO.getProductId())));
        }, startTime);
    }
}
