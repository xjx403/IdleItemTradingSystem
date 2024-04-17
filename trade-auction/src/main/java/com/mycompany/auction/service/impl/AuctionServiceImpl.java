package com.mycompany.auction.service.impl;

import com.github.pagehelper.PageInfo;
import com.mycompany.auction.entity.AuctionProductVO;
import com.mycompany.auction.service.AuctionService;
import com.mycompany.common.utils.RedisKeyUtil;
import com.trade.mbg.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/15 16:53
 * @注释
 */
@Service
public class AuctionServiceImpl implements AuctionService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${trade.host.url.mbg}")
    private String mbgHost;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public AuctionProductVO getAuctionProductById(long productId) {
        String auctionProductKey = RedisKeyUtil.getAuctionProductKey(productId);
        AuctionProductVO productVO = (AuctionProductVO) redisTemplate.opsForValue().get(auctionProductKey);
        if (productVO == null) {
            Product product = restTemplate.getForObject(mbgHost + "/product/select?id=" + productId, Product.class);
            if (product == null) return null;
            productVO = new AuctionProductVO(product);
            redisTemplate.opsForValue().set(auctionProductKey, productVO, 5, TimeUnit.HOURS);
            redisTemplate.opsForSet().add(RedisKeyUtil.getAuctionProductKeyHead(), auctionProductKey);
            redisTemplate.opsForList().rightPush(RedisKeyUtil.getAuctionProductKeyHead() + "list", auctionProductKey);
        }
        return productVO;
    }

    private Set<String> scanKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }


    private List<AuctionProductVO> tmpList(){
        List<AuctionProductVO> list = new ArrayList<>();
        String pattern = "*" + RedisKeyUtil.getAuctionProductKeyHead() + "\\d*";
        System.out.println(pattern);
        Set<String> keys = scanKeys(pattern);
        System.out.println(keys);
        for (String key: keys) {
            list.add((AuctionProductVO) redisTemplate.opsForValue().get(key));
        }
        return list;
    }
    @Override
    public List<AuctionProductVO> list() {
        List<AuctionProductVO> productVOS = new ArrayList<>();
        String listKey = RedisKeyUtil.getAuctionProductKeyHead() + "list";
        List<Object> keys = redisTemplate.opsForList().range(listKey, 0, -1);
        for (Object key: keys) {
            AuctionProductVO productVO = (AuctionProductVO) redisTemplate.opsForValue().get(key);
            if (productVO == null) redisTemplate.opsForList().remove(listKey, 0,  key);
            else productVOS.add(productVO);
        }
        return productVOS;
    }

    @Override
    public PageInfo<AuctionProductVO> list(Integer pageNumber, Integer pageSize) {
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = startIndex + pageSize - 1;
        String listKey = RedisKeyUtil.getAuctionProductKeyHead() + "list";
        List<Object> range = redisTemplate.opsForList().range(listKey, startIndex, endIndex);
        System.out.println(listKey + "list lrang " + startIndex + " " + endIndex );
        Long totalSize = redisTemplate.opsForList().size(listKey);
        int total = totalSize.intValue();
        int maxPageNumber =  total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1;

        List<AuctionProductVO> productVOS = new ArrayList<>();
        System.out.println(range);
        for (Object key : range) {
            AuctionProductVO productVO = (AuctionProductVO) redisTemplate.opsForValue().get(key);
            if (productVO == null) redisTemplate.opsForList().remove(listKey, 0,  key);
            else productVOS.add(productVO);
        }
        //设置分页信息
        PageInfo<AuctionProductVO> productVOPageInfo = new PageInfo<>();
        productVOPageInfo.setList(productVOS);
        System.out.println("isFirstPage: " + new Boolean(pageNumber == 1).toString());
        productVOPageInfo.setIsFirstPage(pageNumber == 1);
        productVOPageInfo.setIsLastPage(pageNumber == maxPageNumber);
        productVOPageInfo.setSize(productVOS.size());
        productVOPageInfo.setTotal(total);
        productVOPageInfo.setPageNum(pageNumber);
        productVOPageInfo.setPageSize(pageSize);
        productVOPageInfo.setStartRow(1);
        productVOPageInfo.setEndRow(maxPageNumber);
        productVOPageInfo.setPages(new Integer(new Long(maxPageNumber).toString()));
        productVOPageInfo.setPrePage(pageNumber - 1);
        productVOPageInfo.setNextPage(pageNumber + 1);
        productVOPageInfo.setHasPreviousPage(pageNumber > 1);
        productVOPageInfo.setHasNextPage(pageNumber < maxPageNumber);
        int[] navigatePageNums = new int[maxPageNumber];
        for (int i = 0; i < maxPageNumber; i++) {
            navigatePageNums[i] = i + 1;
        }
        productVOPageInfo.setNavigatepageNums(navigatePageNums);
        productVOPageInfo.setNavigateFirstPage(1);
        productVOPageInfo.setNavigateLastPage(maxPageNumber);
        return productVOPageInfo;
    }

    //用户竞价
    private String generateBidHistory(Long userId, BigDecimal userBiding){
        StringBuffer bidHistory = new StringBuffer("用户")
                .append(userId)
                .append(" 出价 ")
                .append(userBiding)
                .append("元");
        return bidHistory.toString();
    }
    private synchronized AuctionProductVO addPrice(Long userId,String productKey, BigDecimal increment) {
        AuctionProductVO auctionProduct = (AuctionProductVO)redisTemplate.opsForValue().get(productKey);
        if (auctionProduct == null) return null;
        if (increment.compareTo(auctionProduct.getMinBidIncrement()) == -1) return null;
        //出价增加值是否能被最小出价幅度整除
        if (increment.remainder(auctionProduct.getMinBidIncrement()).compareTo(BigDecimal.ZERO) != 0) {
            return null;
        }
        BigDecimal userBiding = auctionProduct.getCurrentPrice().add(increment);
        auctionProduct.setCurrentPrice(userBiding);
        List<String> history = auctionProduct.getBidHistory();
        if (history == null) history = new ArrayList<>();
        history.add(generateBidHistory(userId, userBiding));
        auctionProduct.setBidHistory(history);
        redisTemplate.opsForValue().set(productKey, auctionProduct);
        return auctionProduct;
    }


    @Override
    public AuctionProductVO bid(Long userId, Long productId, BigDecimal increment) {
        String productKey = RedisKeyUtil.getAuctionProductKey(productId);
        Boolean isSignUp = redisTemplate.opsForSet().isMember(RedisKeyUtil.getAuctionUsersKey(productId), userId);
        if (!isSignUp) {
            return null;
        }
        AuctionProductVO auctionProduct = addPrice(userId, productKey, increment);
        return auctionProduct;
    }

    @Override
    public boolean signUp(Long userId, Long productId) {
        String auctionUsersKey = RedisKeyUtil.getAuctionUsersKey(productId);
        Long addEnd = redisTemplate.opsForSet().add(auctionUsersKey, userId);
        return addEnd != 0;
    }
}
