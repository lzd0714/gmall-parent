package com.atguigu.gmall.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-06 21:15
 **/
@Controller
public class LoginController {
    /**
     * 登录页
     * @return
     */
    @GetMapping("/login.html")
    public String loginPage(@RequestParam("originUrl") String originUrl,
                            Model model){
        model.addAttribute("originUrl",originUrl);
        return "login";
    }
}
