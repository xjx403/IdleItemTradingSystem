package com.trade.mbg.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mycompany.common.api.CommonResult;
import com.mycompany.common.utils.MyImageUtil;
import com.trade.mbg.entity.Member;
import com.trade.mbg.entity.Picture;
import com.trade.mbg.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjx
 * @since 2024-03-20 11:45:58
 */
@RestController
@RequestMapping("/picture")
public class PictureController {
    @Autowired
    private PictureService pictureService;
    private MyImageUtil myImageUtil = new MyImageUtil();

    @GetMapping(value = "/get")
    public void getImage(int type, long ownerId, HttpServletResponse response) throws IOException {
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        queryWrapper.eq("owner_id", ownerId);
        queryWrapper.last("limit 1");
        Picture picture = pictureService.getOne(queryWrapper);
        String pictureContent = picture.getContent();
        if (pictureContent != null) {

            byte[] bytes = null;
            OutputStream os = null;
            try {
                bytes = myImageUtil.stringToImage(pictureContent);
                InputStream ips = new ByteArrayInputStream(bytes);
                BufferedImage image = ImageIO.read(ips);
                response.setContentType("image/png");
                os = response.getOutputStream();
                if (bytes != null) {
                    ImageIO.write(image,picture.getFileType(), os);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            }
        }
    }


    @PostMapping(value = "/upload")
    public boolean uploadPicture(MultipartFile file, int type, long ownerId) {
        if (file == null) {
            return false;
        }
        String codingString = null;
        try {
            codingString = myImageUtil.imageToString(file.getBytes(), file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (codingString == null) {
            return false;
        }
        Picture picture = new Picture();
        picture.setType(type);
        picture.setOwnerId(ownerId);
        picture.setContent(codingString);
        String filename = file.getOriginalFilename(); // image/jpg
        String fileExtension = filename.substring(filename.lastIndexOf(".") + 1);
        picture.setFileType(fileExtension);
        return pictureService.save(picture);
    }
}
