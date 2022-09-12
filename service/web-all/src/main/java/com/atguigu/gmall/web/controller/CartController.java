package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.cart.CartFeignClient;
import com.atguigu.gmall.model.product.SkuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-07 20:17
 **/
@Controller
public class CartController {
    @Autowired
    CartFeignClient cartFeignClient;

    @GetMapping("/addCart.html")
    public String addCartHtml(@RequestParam("skuId") Long skuId,
                              @RequestParam("skuNum") Integer skuNum,
                              Model model){

        Result<SkuInfo> result = cartFeignClient.addToCart(skuId, skuNum);
        if (result.isOk()) {
            model.addAttribute("skuInfo",result.getData());
            model.addAttribute("skuNum",skuNum);
            return "cart/addCart";
        }else {
            String message = result.getMessage();
            model.addAttribute("msg",result.getData());
            return "cart/error";
        }

    }
    ///cart.html
    @GetMapping("/cart.html")
    public String cartHtml(){
        return "cart/index";

    }
    /**
     * 删除购物车中选中商品
     */
    @GetMapping("/cart/deleteChecked")
    public String deleteChecked(){

        /**
         * redirect: 重定向
         * forward: 转发
         */
        cartFeignClient.deleteChecked();
        return "redirect:http://cart.gmall.com/cart.html";
    }
}
