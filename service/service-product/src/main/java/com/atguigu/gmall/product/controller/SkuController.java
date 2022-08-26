package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**Sku管理
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-26 10:16
 **/
@RestController
@RequestMapping("/admin/product")
public class SkuController {
    @Autowired
    SkuInfoService skuInfoService;

    /**
     * sku分页查询
     * @param pageNum
     * @param pageSize
     */
    //http://192.168.200.1/admin/product/list/1/10
    @GetMapping("/list/{pageNum}/{pageSize}")
    public Result getSkuList(@PathVariable("pageNum") Long pageNum,
                             @PathVariable("pageSize") Long pageSize){
        Page<SkuInfo> page = new Page<>(pageNum,pageSize);
        Page<SkuInfo> result = skuInfoService.page(page);
        return Result.ok(result);
    }

    /**
     * 商品上架
     * @param skuId
     * @return
     */
    // http://192.168.200.1/admin/product/onSale/48
    @GetMapping("/onSale/{skuId}")
    public Result onSale(@PathVariable("skuId")Long skuId){
        skuInfoService.onSale(skuId);
        return Result.ok();
    }

    /**
     * 商品下架
     * @param skuId
     */
    // http://192.168.200.1/admin/product/cancelSale/47
    @GetMapping("/cancelSale/{skuId}")
    public Result cancelSale(@PathVariable("skuId")Long skuId){
        skuInfoService.cancelSale(skuId);
        return Result.ok();
    }
    //http://192.168.200.1/admin/product/saveSkuInfo
    @PostMapping("saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuInfo){
        skuInfoService.saveSkuInfo(skuInfo);
        return Result.ok();
    }

}
