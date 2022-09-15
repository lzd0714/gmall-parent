package com.atguigu.gmall.model.to.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-15 14:50
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderMsg {
    private Long orderId;
    private Long userId;
}
