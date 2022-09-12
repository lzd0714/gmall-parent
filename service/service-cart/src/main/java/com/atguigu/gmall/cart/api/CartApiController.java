package com.atguigu.gmall.cart.api;

import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-07 20:25
 **/
@RestController
@RequestMapping("/api/inner/rpc/cart")
public class CartApiController {
    @Autowired
    CartService cartService;

    /**
     * 将商品添加到购物车
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/addToCart")
    public Result<SkuInfo> addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num){
        SkuInfo skuInfo = cartService.addToCart(skuId,num);
        return Result.ok(skuInfo);
    }
    /**
     * 删除购物车中选中的商品
     * @return
     */
    @GetMapping("/deleteChecked")
    public Result deleteChecked(){
        String cartKey = cartService.determinCartKey();
        cartService.deleteChecked(cartKey);
        return Result.ok();
    }
}
