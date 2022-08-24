package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** 品牌API
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-24 14:31
 **/
@RestController
@RequestMapping("/admin/product")
public class BaseTrademarkController {
    @Autowired
    BaseTrademarkService baseTrademarkService;

    /**
     * 分页查询所有品牌
     * @param pn
     * @param limit
     */
    //http://192.168.200.1/admin/product/baseTrademark/1/10
    @GetMapping("/baseTrademark/{pn}/{limit}")
    public Result baseTrademark(@PathVariable("pn") Long pn,
                                @PathVariable("limit") Long limit){
        //mybatisplus带的分页
        Page<BaseTrademark> page = new Page<>(pn,limit);
        Page<BaseTrademark> pageResult = baseTrademarkService.page(page);
        return Result.ok(pageResult);
    }

    /**
     * 根据品牌id获取品牌信息
     * @param id
     */
    //http://192.168.200.1/admin/product/baseTrademark/get/2
    @GetMapping("/baseTrademark/get/{id}")
    public Result getBaseTrademark(@PathVariable("id") Long id){
        BaseTrademark trademark = baseTrademarkService.getById(id);
        return Result.ok(trademark);
    }

    /**
     * 添加品牌
     * @param baseTrademark
     */
    // http://192.168.200.1/admin/product/baseTrademark/save
    @PostMapping("/baseTrademark/save")
    public Result saveBaseTrademark(@RequestBody BaseTrademark baseTrademark){
        baseTrademarkService.save(baseTrademark);
        return Result.ok();
    }

    /**
     * 修改品牌
     * @param baseTrademark
     */
    //http://192.168.200.1/admin/product/baseTrademark/update
    @PutMapping("/baseTrademark/update")
    public Result updateBaseTrademark(@RequestBody BaseTrademark baseTrademark){
        baseTrademarkService.updateById(baseTrademark);
        return Result.ok();
    }

    /**
     * 删除品牌
     * @param tid
     */
    //http://192.168.200.1/admin/product/baseTrademark/remove/3
    @DeleteMapping("/baseTrademark/remove/{tid}")
    public Result removeBaseTrademark(@PathVariable("tid") Long tid){
        baseTrademarkService.removeById(tid);
        return Result.ok();
    }

}
