package com.atguigu.gmall.order.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.vo.order.OrderSubmitVo;
import com.atguigu.gmall.order.biz.OrderBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-13 20:04
 **/

@RestController
@RequestMapping("/api/order/auth")
public class OrderRestController {
    @Autowired
    OrderBizService orderBizService;
    /**
     * 提交订单
     * @return
     */
    @PostMapping("/submitOrder")
    public Result submitOrder(@RequestParam("tradeNo") String tradeNo,
                              @RequestBody OrderSubmitVo submitVo){

        Long orderId = orderBizService.submitOrder(submitVo,tradeNo);
//http://api.gmall.com/api/order/auth/submitOrder?tradeNo=1663211267926_2
        return Result.ok(orderId.toString());
    }
}