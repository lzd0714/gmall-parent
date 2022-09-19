package com.atguigu.gmall.model.to.mq;

import lombok.Data;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-18 19:43
 **/
@Data
public class WareDeduceSkuInfo {

    Long skuId;
    Integer skuNum;
    String skuName;
}
