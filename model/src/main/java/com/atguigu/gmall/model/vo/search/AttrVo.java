package com.atguigu.gmall.model.vo.search;

import lombok.Data;

import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-05 17:45
 **/
@Data
public class AttrVo {
    private Long attrId;
    private String attrName;
    //每个属性涉及到的所有值集合
    private List<String> attrValueList;
}
