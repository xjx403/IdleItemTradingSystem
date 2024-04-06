package com.trade.mbg.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mycompany.common.api.CommonResult;
import com.mycompany.common.utils.MyImageUtil;
import com.mycompany.common.value_set.ImageTypeCode;
import com.mycompany.common.value_set.MemberPrivilegeCode;
import com.trade.mbg.entity.Member;
import com.trade.mbg.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjx
 * @since 2024-03-18 04:30:00
 */

@Tag(name = "MemberController", description = "用户相关")
@RestController
@RequestMapping("/user")
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    @Autowired
    private MemberService memberService;
    private MyImageUtil myImageUtil = new MyImageUtil();
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/hello")
    public CommonResult hello(){
        return CommonResult.success("hello");
    }
    @Operation(description = "用户注册")
    @PostMapping(value = "/register")
    public CommonResult register(@RequestParam(required = true) String username,
                                @RequestParam(required = true) String password,
                                @RequestParam(required = true) String email ) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        Member member = memberService.getOne(queryWrapper);
        if (member != null) {
            return CommonResult.failed("用户邮箱已注册");
        }
        member = new Member();
        member.setEmail(email);
        member.setUsername(username);
        member.setPassword(password);
        member.setCreateTime(LocalDate.now());
        member.setPrivilege(MemberPrivilegeCode.ACTIVATED.getLevel());
        member.setLevel(0);
        member.setHeader("https://cdn.pixabay.com/photo/2023/04/05/09/17/spring-7901015_640.jpg");
        member.setPurse(0l);
        memberService.save(member);
        return CommonResult.success(member);
    }



    @Operation(description = "获取用户的收款码")
    @GetMapping(value = "/getPaymentCode")
    public void getPaymentCode(long userId, HttpServletResponse response) throws IOException {
        Member member = memberService.getById(userId);
        if (member.getPaymentCodeImage() != null) {
            byte[] bytes = null;
            OutputStream os = null;
            try {
                bytes = myImageUtil.stringToImage(member.getPaymentCodeImage());
                InputStream ips = new ByteArrayInputStream(bytes);
                BufferedImage image = ImageIO.read(ips);
                response.setContentType("image/png");
                os = response.getOutputStream();
                if (bytes != null) {
                    ImageIO.write(image,"png", os);
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

    @Operation(description = "上传更新用户的收款码，文件上传的最大不能超过1MB！!")
    @PostMapping(value = "/updatePaymentCode")
    public CommonResult updatePaymentCode(MultipartFile file, long userId){
        //MultipartFile file = request.getFile("upload");

        if (file == null) {
            return CommonResult.failed("上传文件为空！");
        }
        String codingString = null;
        try {
            codingString = myImageUtil.imageToString(file.getBytes(), file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (codingString == null) {
            return CommonResult.failed();
        }
        UpdateWrapper<Member> memberUpdateWrapper = new UpdateWrapper<>();
        memberUpdateWrapper.eq("id", userId);
        memberUpdateWrapper.set("payment_code_image", codingString);
        boolean updateEnd = memberService.update(memberUpdateWrapper);
        return CommonResult.success(updateEnd);
    }

    @Operation(description = "修改用户头像")
    @PostMapping(value = "/changeHeader")
    public CommonResult changeUserHeader(MultipartFile newHeader, Long userId) {
        if (newHeader == null) {
            return CommonResult.failed("上传文件为空！");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> eqMap = new HashMap<>();
        eqMap.put("file", newHeader);
        eqMap.put("type", ImageTypeCode.HEADER.getCode());
        eqMap.put("ownerId", userId);
        HttpEntity<Map> entity = new HttpEntity<>(eqMap, headers);
        restTemplate.postForEntity("http://localhost:8092/picture/upload", entity ,Boolean.class);
        return CommonResult.undeveloped();
    }
}
