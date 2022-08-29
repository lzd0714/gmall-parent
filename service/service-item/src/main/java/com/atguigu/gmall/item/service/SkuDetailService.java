package com.atguigu.gmall.item.service;

import com.atguigu.gmall.model.to.SkuDetailTo;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-29 17:15
 **/
public interface SkuDetailService {
    SkuDetailTo getSkuDetail(Long skuId);
}
