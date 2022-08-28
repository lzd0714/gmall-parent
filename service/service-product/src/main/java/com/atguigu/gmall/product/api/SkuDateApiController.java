package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**商品详情数据库层操作
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-27 16:27
 **/
@RestController
@RequestMapping("/api/inner/rpc/product")
public class SkuDateApiController {
    @Autowired
    SkuInfoService skuInfoService;
    @GetMapping("/skudetail/{skuId}")
    public Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId") Long skuId){
        SkuDetailTo skuDetailTo =skuInfoService.getSkuDetail(skuId);
        return Result.ok(skuDetailTo);
    }
}
