package com.atguigu.gmall.model.to.mq;

import lombok.Data;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-18 19:44
 **/
@Data
public class WareDeduceStatusMsg {
    private Long orderId;
    private String status;
}
