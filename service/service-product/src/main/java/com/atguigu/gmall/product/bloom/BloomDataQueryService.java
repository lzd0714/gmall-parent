package com.atguigu.gmall.product.bloom;

import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-05 14:24
 **/
public interface BloomDataQueryService {
    //模板模式；所有的设计模式：封装、继承、多态

    /**
     * 父类规定好算法
     * @return
     */
    List queryData();
}
