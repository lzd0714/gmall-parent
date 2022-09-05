package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 17337
* @description 针对表【sku_info(库存单元表)】的数据库操作Service
* @createDate 2022-08-23 18:24:25
*/
public interface SkuInfoService extends IService<SkuInfo> {

    void onSale(Long skuId);

    void cancelSale(Long skuId);

    void saveSkuInfo(SkuInfo skuInfo);

    SkuDetailTo getSkuDetail(Long skuId);

    BigDecimal get1010Price(Long skuId);

    SkuInfo getDetailSkuInfo(Long skuId);

    List<SkuImage> getDetailSkuImages(Long skuId);

    List<Long> findAllSkuId();
}
