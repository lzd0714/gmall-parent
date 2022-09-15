package com.atguigu.gmall.order;
import java.math.BigDecimal;
import java.util.Date;
import com.atguigu.gmall.model.order.OrderDetail;
import com.atguigu.gmall.model.vo.order.CartInfoVo;
import com.google.common.collect.Lists;

import com.atguigu.gmall.model.vo.order.OrderSubmitVo;
import com.atguigu.gmall.order.biz.OrderBizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-15 11:30
 **/
@SpringBootTest
public class SubmitTest {
    @Autowired
    OrderBizService orderBizService;
    @Test
    public void test(){
        OrderSubmitVo submitVo = new OrderSubmitVo();
        submitVo.setConsignee("admin");
        submitVo.setConsigneeTel("123456789");
        submitVo.setDeliveryAddress("北京市昌平区北七家");
        submitVo.setPaymentWay("ONLINE");
        List<CartInfoVo> list = new ArrayList<>();
        CartInfoVo cartInfoVo = new CartInfoVo();
        cartInfoVo.setSkuId(43L);
        cartInfoVo.setImgUrl("http://39.99.159.121:9000/gmall/16230513636548bd01a398e614d2bab5fd3366883b1b7.png");
        cartInfoVo.setSkuName("小米 CC9 手机 美颜自拍 游戏手机 仙女渐变色（美图定制版） 全网通 6GB+128GB");
        cartInfoVo.setOrderPrice(new BigDecimal("2200"));
        cartInfoVo.setSkuNum(2);
        cartInfoVo.setHasStock("1");
        list.add(cartInfoVo);
        submitVo.setOrderDetailList(list);
        Long aLong = orderBizService.submitOrder(submitVo, "1663220864104_2");
        System.out.println(aLong);
    }
}
