package com.atguigu.gmall.model.to;

import lombok.Data;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-27 16:30
 **/
@Data
public class CategoryViewTo {
    private Long category1Id;
    private String category1Name;
    private Long category2Id;
    private String category2Name;
    private Long category3Id;
    private String category3Name;
}
