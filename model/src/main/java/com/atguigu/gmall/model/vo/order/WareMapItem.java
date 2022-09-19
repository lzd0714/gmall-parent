package com.atguigu.gmall.model.vo.order;

import lombok.Data;

import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-18 21:47
 **/
@Data
public class WareMapItem {
    private Long wareId;
    private List<Long> skuIds;
}
