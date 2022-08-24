package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 17337
* @description 针对表【base_attr_info(属性表)】的数据库操作Service
* @createDate 2022-08-23 18:24:25
*/
public interface BaseAttrInfoService extends IService<BaseAttrInfo> {


    List<BaseAttrInfo> getAttrInfoAndValueByCategoryId(Long category1Id, Long category2Id, Long category3Id);

    void saveAttrInfo(BaseAttrInfo info);
}
