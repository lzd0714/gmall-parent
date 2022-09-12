package com.atguigu.gmall.cart.controller;

import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.cart.CartInfo;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-08 20:16
 **/
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;

    /**
     * 查询购物车中的信息
     * @return
     */
    @GetMapping("/cartList")
    public Result getCartList(){
        //决定用哪个购物车的键
        String cartKey = cartService.determinCartKey();
        //先尝试合并购物车
        cartService.mergeUserAndTempCart();
        List<CartInfo> infos = cartService.getCartList(cartKey);
        return Result.ok(infos);
    }
    /**
     * 修改购物车中得商品数量
     * @param skuId
     * @param updateNum
     * @return
     */
    //http://api.gmall.com/api/cart/addToCart/51/1
    @PostMapping("/addToCart/{skuId}/{updateNum}")
    public Result updateItemNum(@PathVariable("skuId")Long skuId,@PathVariable("updateNum") Integer updateNum){
        //决定用哪个购物车的键
        String cartKey = cartService.determinCartKey();
        cartService.updateItemNum(skuId,updateNum,cartKey);
        return Result.ok();
    }

    /**
     * 修改购物车的勾选状态
     * @param skuId
     * @param status
     * @return
     */
    //http://api.gmall.com/api/cart/checkCart/51/1
    @GetMapping("/checkCart/{skuId}/{status}")
    public Result checkCart(@PathVariable("skuId") Long skuId,@PathVariable("status") Integer status){
        //决定用哪个购物车的键
        String cartKey = cartService.determinCartKey();
        cartService.checkCart(skuId,status,cartKey);
        return Result.ok();
    }

    /**
     * 从购物车中删除指定商品
     * @param skuId
     * @return
     */
    //http://api.gmall.com/api/cart/deleteCart/51
    @DeleteMapping("/deleteCart/{skuId}")
    public Result deleteCartItem(@PathVariable("skuId")Long skuId){
        //决定用哪个购物车的键
        String cartKey = cartService.determinCartKey();
        cartService.deleteCartItem(skuId,cartKey);
        return Result.ok();
    }

}
