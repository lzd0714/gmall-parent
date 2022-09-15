package com.atguigu.gmall.model.vo.order;

import lombok.Data;

import java.util.List;

/**订单提交 数据模型
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-13 20:06
 **/
@Data
public class OrderSubmitVo {
    private String consignee;
    private String consigneeTel;
    private String deliveryAddress;
    private String paymentWay;
    private String orderComment;
    private List<CartInfoVo> orderDetailList;
}
