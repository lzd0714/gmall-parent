package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.service.SpuImageService;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.List;

/**Spu管理
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-26 10:16
 **/
@RestController
@RequestMapping("/admin/product")
public class SpuController {
    @Autowired
    SpuInfoService spuInfoService;
    @Autowired
    SpuImageService spuImageService;

    /**
     * 分页查询获取spu
     * @param pageNum
     * @param pageSize
     * @param category3Id
     */
    //http://192.168.200.1/admin/product/1/10?category3Id=1
    @GetMapping("/{pageNum}/{pageSize}")
    public Result getSpoPage(@PathVariable("pageNum") Long pageNum,
                             @PathVariable("pageSize")Long pageSize,
                             @RequestParam("category3Id") Long category3Id){
        Page<SpuInfo> page = new Page<>(pageNum,pageSize);

        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id",category3Id);
        //分页查询
        Page<SpuInfo> result = spuInfoService.page(page, wrapper);
        return  Result.ok(result);
    }

    /**
     * 大保存spu信息
     * @param spuInfo
     */
    //http://192.168.200.1/admin/product/saveSpuInfo
    @PostMapping("/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo){
        spuInfoService.saveSpuInfo(spuInfo);
        return Result.ok();
    }

    /**
     * 查询这个spu的所有图片
     * @param spuId
     */
    //http://192.168.200.1/admin/product/spuImageList/28
    @GetMapping("/spuImageList/{spuId}")
    public Result spuImageList(@PathVariable("spuId")Long spuId){
        QueryWrapper<SpuImage> wrapper = new QueryWrapper<>();
        wrapper.eq("spu_id",spuId);
        List<SpuImage> list = spuImageService.list(wrapper);
        return Result.ok(list);
    }
}
