package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 17337
* @description 针对表【base_category3(三级分类表)】的数据库操作Service实现
* @createDate 2022-08-23 16:47:13
*/
@Service
public class BaseCategory3ServiceImpl extends ServiceImpl<BaseCategory3Mapper, BaseCategory3> implements BaseCategory3Service{
    @Autowired
    BaseCategory3Mapper baseCategory3Mapper;
    @Override
    public List<BaseCategory3> getCategory2Child(Long c2Id) {
        QueryWrapper<BaseCategory3> warpper = new QueryWrapper<>();
        warpper.eq("category2_id",c2Id);
        List<BaseCategory3> list = baseCategory3Mapper.selectList(warpper);
        return list;
    }

    @Override
    public CategoryViewTo getCategoryView(Long category3Id) {
        CategoryViewTo categoryViewTo = baseCategory3Mapper.getCategoryView(category3Id);
        return categoryViewTo;
    }
}




