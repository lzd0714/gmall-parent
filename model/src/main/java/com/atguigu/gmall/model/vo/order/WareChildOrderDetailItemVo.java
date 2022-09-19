package com.atguigu.gmall.model.vo.order;

import lombok.Data;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-18 20:49
 **/
@Data
public class WareChildOrderDetailItemVo {
    private Long skuId;
    private Integer skuNum;
    private String skuName;
}
