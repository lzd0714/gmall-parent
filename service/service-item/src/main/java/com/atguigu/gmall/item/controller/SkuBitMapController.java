package com.atguigu.gmall.item.controller;

import com.atguigu.gmall.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-03 17:56
 **/
@RestController
public class SkuBitMapController {
    /**
     * 同步数据库中所有商品的id占位标识
     * @return
     */
    @GetMapping("/sync/skuid/bitmap")
    public Result syncBitMap(){
        //1、数据库中所有商品id查询出来
        //2、挨个置位  setbit  skuids  50  0
        return Result.ok();
    }
}
