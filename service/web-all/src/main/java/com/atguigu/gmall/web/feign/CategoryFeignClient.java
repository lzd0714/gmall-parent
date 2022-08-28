package com.atguigu.gmall.web.feign;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-27 16:07
 **/
@RequestMapping("/api/inner/rpc/product")
@FeignClient("service-product")
public interface CategoryFeignClient {

//    @GetMapping("/api/inner/rpc/product/category/tree")
//    Result<List<CategoryTreeTo>> getCategoryTree();
    @GetMapping("/category/tree")
    Result<List<CategoryTreeTo>> getAllCategoryWithTree();
}
