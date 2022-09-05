package com.atguigu.gmall.model.vo.search;

import lombok.Data;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-05 17:39
 **/
@Data
public class SearchParamVo {
    Long category3Id;
    Long category1Id;
    Long category2Id;
    String keyword;
    String trademark;
    String[] props;
    String order = "1:desc";
    Integer pageNo = 1;
}
