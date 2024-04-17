package com.mycompany.common.utils;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/15 18:07
 * @注释
 */
public class RedisKeyUtil {
    private static final String SPILT = ":";
    private static final String BACK = "back";
    private static final String PRODUCT = "product";
    private static final String AUCTION = "auction";
    private static final String USERS = "users";

    /**
     * @param productId
     * @return redisKeyRule -- back:product:auction:[productId]
     */
    public static String getAuctionProductKey(long productId){
        StringBuilder aucProKey = new StringBuilder(BACK);
        aucProKey.append(SPILT)
                .append(PRODUCT)
                .append(SPILT)
                .append(AUCTION)
                .append(SPILT)
                .append(productId);
        return aucProKey.toString();
    }

    public static String getAuctionProductKeyHead(){
        StringBuilder aucProKey = new StringBuilder(BACK);
        aucProKey.append(SPILT)
                .append(PRODUCT)
                .append(SPILT)
                .append(AUCTION)
                .append(SPILT);
        return aucProKey.toString();
    }

    /**
     *
     * @param productId
     * @return redis set key : back:auction:users:[productId]
     */
    public static String getAuctionUsersKey(long productId){
        StringBuilder aucUserKey
                = new StringBuilder(BACK)
                .append(SPILT)
                .append(AUCTION)
                .append(SPILT)
                .append(USERS)
                .append(SPILT)
                .append(productId);
        return aucUserKey.toString();
    }
}
