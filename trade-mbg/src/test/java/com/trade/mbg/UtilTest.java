package com.trade.mbg;

import com.mycompany.common.utils.MyProductUtil;
import org.junit.Test;

import java.util.HashSet;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/18 21:14
 * @注释
 */
public class UtilTest extends BaseSpringBootTest{

    /**
     * 测试是否会出现重复的SN码
     */
    @Test
    public void SNUtilTest(){
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            String sn = MyProductUtil.generateProductSn();
            if (set.contains(sn)) {
                System.out.println("出现重复SN码");
                break;
            }else {
                set.add(sn);
            }

            System.out.println(sn);
        }
    }
}
