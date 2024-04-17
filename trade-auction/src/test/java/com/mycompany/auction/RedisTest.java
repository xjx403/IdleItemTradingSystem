package com.mycompany.auction;

import com.mycompany.common.utils.RedisKeyUtil;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/15 18:05
 * @注释
 */
public class RedisTest extends BaseSpringBootTest{

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("user1","nihao");
        System.out.println(redisTemplate.opsForValue().get("user1"));
    }

    @Test
    public void testList(){
        System.out.println(redisTemplate.opsForList().range("test::list", 1, -1));
    }

    @Test
    public void delKey(){
        for (int i = 1; i < 10; i++) {
            redisTemplate.delete("back:product:auction:" + i);
        }
        redisTemplate.delete(RedisKeyUtil.getAuctionProductKeyHead() + "list");
    }

    @Test
    public void listTest(){
        for (int i = 1; i < 10; i++) {
            redisTemplate.opsForList().leftPush(RedisKeyUtil.getAuctionProductKeyHead()+"list", RedisKeyUtil.getAuctionProductKey(i));
        }
    }
    @Test
    public void delList() {
        System.out.println(redisTemplate.delete(RedisKeyUtil.getAuctionProductKeyHead() + "list"));
    }
}
