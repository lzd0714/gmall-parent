package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**平台属性相关的API
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-24 14:30
 **/
@RestController
@RequestMapping("/admin/product")
public class BaseAttrController {
    @Autowired
    BaseAttrInfoService baseAttrInfoService;
    @Autowired
    BaseAttrValueService baseAttrValueService;


    /**
     * 查询某个分类下的所有平台属性
     * @param category1Id
     * @param category2Id
     * @param category3Id
     */
    // http://192.168.200.1/admin/product/attrInfoList/1/0/0
    @GetMapping("/attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result getAttrInfoList(@PathVariable("category1Id") Long category1Id,
                                  @PathVariable("category2Id") Long category2Id,
                                  @PathVariable("category3Id") Long category3Id){
       List<BaseAttrInfo> infos =  baseAttrInfoService.getAttrInfoAndValueByCategoryId(category1Id,category2Id,category3Id);
       return Result.ok(infos);
    }

    /**
     * 属性的新增
     * @param info
     */
    //http://192.168.200.1/admin/product/saveAttrInfo
    @PostMapping("saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo info){
        baseAttrInfoService.saveAttrInfo(info);
        return Result.ok();
    }

    /**
     * 根据平台属性id查询平台属性的对象数据
     * @param attrId
     */
    //http://192.168.200.1/admin/product/getAttrValueList/1
    @GetMapping("/getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable("attrId") Long attrId){
        List<BaseAttrValue> values = baseAttrValueService.getAttrValueList(attrId);
        return Result.ok(values);
    }

}
