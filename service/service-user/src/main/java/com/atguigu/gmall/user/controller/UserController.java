package com.atguigu.gmall.user.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.model.user.UserInfo;
import com.atguigu.gmall.model.vo.user.LoginSuccessVo;
import com.atguigu.gmall.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-06 21:26
 **/
@RequestMapping("/api/user")
@RestController
public class UserController {
    @Autowired
    UserInfoService userInfoService;


    /**
     * 用户登录
     * @return
     */
    @PostMapping("/passport/login")
    public Result login(@RequestBody UserInfo info){

        LoginSuccessVo vo = userInfoService.login(info);
        if(vo != null){
            return Result.ok(vo);
        }


        return Result.build("", ResultCodeEnum.LOGIN_ERROR);
    }

    /**
     * 退出
     * @return
     */
    @GetMapping("/passport/logout")
    public Result logout(@RequestHeader("token") String token){

        userInfoService.logout(token);
        return Result.ok();
    }

}
