package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**文件上传
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-24 14:32
 **/
@RestController
@RequestMapping("/admin/product")
public class FileuploadController {
    /**
     * 文件上传到Minio
     * @param file
     */
    //http://192.168.200.1/admin/product/fileUpload
    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestPart("file")MultipartFile file){
        //TODO 文件上传 怎么上传到Minio?
        return Result.ok();
    }

    /**
     * 接收文件参数的测试
     * @param username
     * @param password
     * @param email
     * @param header
     * @param sfz
     * @param shz
     * @param hobby
     * @param cache
     * @return
     */
    @PostMapping("/reg")
    public Result reg(@RequestParam("username")String username,
                      @RequestParam("password")String password,
                      @RequestParam("email")String email,
                      @RequestPart("header")MultipartFile[] header,
                      @RequestPart("sfz") MultipartFile sfz,
                      @RequestPart("shz") MultipartFile shz,
                      @RequestParam("hobby") String[] hobby,
                      @RequestHeader("Cache-Control") String cache
                      ){
        //@CookieValue("jsessionid") String jsessionid
        //1、用户名，密码，邮箱
        Map<String, Object> result = new HashMap<>();
        result.put("用户名",username);
        result.put("密码",password);
        result.put("邮箱",email);

        result.put("头像文件大小？",header.length);
        result.put("生活照文件大小？",sfz.getSize());
        result.put("身份证文件大小？",shz.getSize());
        result.put("爱好", Arrays.asList(hobby));
        result.put("cache",cache);
        System.out.println(result);
        return Result.ok(result);
    }
}
