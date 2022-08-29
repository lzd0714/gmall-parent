package com.atguigu.gmall.item.feign;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-29 17:21
 **/
@RequestMapping("/api/inner/rpc/product")
@FeignClient("service-product")
public interface SkuDetailFeignClient {
    /**
     * 查询sku的基本信息
     * @param skuId
     */
    @GetMapping("/skudetail/info/{skuId}")
    Result<SkuInfo> getSkuInfo(@PathVariable("skuId") Long skuId);


    /**
     * 查询sku的图片信息
     * @param skuId
     */
    @GetMapping("/skudetail/images/{skuId}")
    Result<List<SkuImage>> getSkuImages(@PathVariable("skuId")Long skuId);


    /**
     * 查询sku的实时价格
     * @param skuId
     */
    @GetMapping("/skudetail/price/{skuId}")
    Result<BigDecimal> getSku1010Price(@PathVariable("skuId")Long skuId);

    /**
     * 查询sku对应的spu定义的所有销售属性名和值。并且标记出当前sku是哪个
     * @param skuId
     * @param spuId
     */
    @GetMapping("/skudetail/saleattrvalues/{skuId}/{spuId}")
    Result<List<SpuSaleAttr>> getSkuSaleattrvalues(@PathVariable("skuId") Long skuId,
                                                   @PathVariable("spuId") Long spuId);


    /**
     * 查sku组合 valueJson
     * @param spuId
     */
    @GetMapping("/skudetail/valuejson/{spuId}")
    Result<String> getSKuValueJson(@PathVariable("spuId") Long spuId);


    /**
     * 查分类
     * @param category3Id
     */
    @GetMapping("/skudetail/categoryview/{category3Id}")
    Result<CategoryViewTo> getCategoryView(@PathVariable("category3Id") Long category3Id);
}
