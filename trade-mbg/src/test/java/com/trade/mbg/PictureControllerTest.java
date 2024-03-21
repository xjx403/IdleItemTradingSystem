package com.trade.mbg;

import com.trade.mbg.entity.Picture;
import com.trade.mbg.service.PictureService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/21 18:39
 * @注释
 */
public class PictureControllerTest extends BaseSpringBootTest{

    @Autowired
    private PictureService pictureService;

    @Test
    public void deleteRepeatedPicture(){

    }

    @Test
    public void testPictureLogicDelete(){
        List<Picture> list = pictureService.list();
        for (Picture picture: list) {
            System.out.println(picture.toString());
        }
    }
}
