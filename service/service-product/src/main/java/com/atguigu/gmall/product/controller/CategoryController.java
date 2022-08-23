package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**分类请求处理器
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-23 16:31
 **/
@RestController
@RequestMapping("/admin/product")
public class CategoryController {
    @Autowired
    BaseCategory1Service baseCategory1Service;
    @Autowired
    BaseCategory2Service baseCategory2Service;

    /**
     * 获取所有的一级分类
     */
    @GetMapping("/getCategory1")
    public Result getCategory1(){
        List<BaseCategory1> list = baseCategory1Service.list();
        return Result.ok(list);
    }
    //http://192.168.200.1/admin/product/getCategory2/8

    /**
     *
     * @param c1Id
     * @return
     */
    @GetMapping("getCategory2/{c1Id}")
    public Result getCategory2(@PathVariable("c1Id") Long c1Id){
        List<BaseCategory2> category2s = baseCategory2Service.getCategory1Child(c1Id);
        return Result.ok(category2s);
    }
    // http://192.168.200.1/admin/product/getCategory3/17


}
