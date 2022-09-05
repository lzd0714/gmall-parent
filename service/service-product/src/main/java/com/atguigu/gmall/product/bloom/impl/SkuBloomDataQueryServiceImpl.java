package com.atguigu.gmall.product.bloom.impl;

import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-05 14:25
 **/
@Service
public class SkuBloomDataQueryServiceImpl implements BloomDataQueryService {
    @Autowired
    SkuInfoService skuInfoService;
    @Override
    public List queryData() {
        return skuInfoService.findAllSkuId();
    }
}
