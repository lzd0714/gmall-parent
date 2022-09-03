package com.atguigu.gmall.model.to;

import lombok.Data;

import java.util.List;

/**DDD：领域驱动设计
 * 三级分类树形结构，支持无限级
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-27 16:15
 **/
@Data
public class CategoryTreeTo {
    private  Long categoryId;
    private String categoryName;
    private List<CategoryTreeTo> categoryChild;
}
