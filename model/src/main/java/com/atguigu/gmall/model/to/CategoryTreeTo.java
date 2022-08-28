package com.atguigu.gmall.model.to;

import lombok.Data;

import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-27 16:15
 **/
@Data
public class CategoryTreeTo {
    private  Long categoryId; //1
    private String categoryName;
    private List<CategoryTreeTo> categoryChild;//子分类
}
