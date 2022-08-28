package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.atguigu.gmall.web.feign.CategoryFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.jws.WebParam;
import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-27 16:04
 **/
@Controller
public class IndexController {
    @Autowired
    CategoryFeignClient categoryFeignClient;

    /**
     * 跳到首页
     * @param model
     */
    @GetMapping({"/","/index"})
    public String indexPage(Model model){
        Result<List<CategoryTreeTo>> result = categoryFeignClient.getAllCategoryWithTree();
        if (result.isOk()){
            //远程调用成功。强类型语言
            List<CategoryTreeTo> data = result.getData();
            model.addAttribute("list",data);
        }
        return "index/index";//页面的逻辑视图名
    }
}
