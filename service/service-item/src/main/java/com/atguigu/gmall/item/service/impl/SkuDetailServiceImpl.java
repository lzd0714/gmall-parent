package com.atguigu.gmall.item.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.item.feign.SkuDetailFeignClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-29 17:16
 **/
@Service
public class SkuDetailServiceImpl implements SkuDetailService {
    @Autowired
    SkuDetailFeignClient skuDetailFeignClient;
    @Autowired
    ThreadPoolExecutor executor;
    @Autowired
    StringRedisTemplate redisTemplate;
    //未优化前400s
    public SkuDetailTo getSkuDetailFromRpc(Long skuId) {
        SkuDetailTo detailTo = new SkuDetailTo();
        //以前   一次远程超级调用【网络交互比较浪费时间】  查出所有数据直接给我们返回
        // 一次远程用 2s
        // 6次远程调用 6s + 0.5*6 = 9s
        //同步调用浪费时间
        //远程调用其实不用等待，各查各的。 异步的方式
        //CompletableFuture.runAsync()// CompletableFuture<Void>  启动一个下面不用它返回结果的异步任务
        //CompletableFuture.supplyAsync()//CompletableFuture<U>  启动一个下面用它返回结果的异步任务

        //1、查基本信息   1s
        CompletableFuture<SkuInfo> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            Result<SkuInfo> result = skuDetailFeignClient.getSkuInfo(skuId);
            SkuInfo skuInfo = result.getData();
            detailTo.setSkuInfo(skuInfo);
            return skuInfo;
        }, executor);


        //2、查商品图片信息  1s
        CompletableFuture<Void> imageFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Result<List<SkuImage>> skuImages = skuDetailFeignClient.getSkuImages(skuId);
            skuInfo.setSkuImageList(skuImages.getData());
        }, executor);




        //3、查商品实时价格 2s
        CompletableFuture<Void> priceFuture = CompletableFuture.runAsync(() -> {
            Result<BigDecimal> price = skuDetailFeignClient.getSku1010Price(skuId);
            detailTo.setPrice(price.getData());
        }, executor);


        //4、查销售属性名值
        CompletableFuture<Void> saleAttrFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Long spuId = skuInfo.getSpuId();
            Result<List<SpuSaleAttr>> saleattrvalues = skuDetailFeignClient.getSkuSaleattrvalues(skuId, spuId);
            detailTo.setSpuSaleAttrList(saleattrvalues.getData());
        }, executor);


        //5、查sku组合
        CompletableFuture<Void> skuVlaueFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Result<String> sKuValueJson = skuDetailFeignClient.getSKuValueJson(skuInfo.getSpuId());
            detailTo.setValuesSkuJson(sKuValueJson.getData());
        }, executor);



        //6、查分类
        CompletableFuture<Void> categoryFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Result<CategoryViewTo> categoryView = skuDetailFeignClient.getCategoryView(skuInfo.getCategory3Id());
            detailTo.setCategoryView(categoryView.getData());
        },executor);


        //异步实际上是： 空间换时间；  new Thread()
        //最一行： 串行： 6s
        //最一行： 并行： 等待一个最长时间，全部任务都能完成。
        //如果异步：  new Thread().start();
        //  不能直接用 new Thread().start();一个请求进来，直接无脑开6个线程，高并发下直接OOM。
        // 一个一炸可能导致整个集群雪崩。
        // 不能无脑开线程，很容易资源耗尽，池技术（线程池、连接池、xxx池）【资源复用问题】
        //   线程池+阻塞队列：解决资源复用与等待问题。

        //6个任务都结束后，To才能返回
        //
        //1、CompletableFuture 异步【编排】
        //启动一个异步任务有多少种方法
        //1、new Thread().start()
        //2、Runnable  new Thread(runnable).start();
        //3、Callable  带结果  FutureTask
        //4、线程池
        //     executor.submit(()->{});  executor.execute(()->{});
        //5、异步编排 CompletableFuture
        //    - CompletableFuture启动异步任务
        CompletableFuture
                .allOf(imageFuture,priceFuture,saleAttrFuture,skuVlaueFuture,categoryFuture)
                .join();

        return detailTo;
    }


    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {
        //1.先查缓存
        String jsonStr = redisTemplate.opsForValue().get("sku:info:" + skuId);
        //2.判断
        if(StringUtils.isEmpty(jsonStr)){
            //缓存中没有,回源
            SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
            //放到缓存
            redisTemplate.opsForValue().set("sku:info:"+skuId, Jsons.toStr(fromRpc));
            return fromRpc;
        }
        SkuDetailTo skuDetailTo= Jsons.toObj(jsonStr,SkuDetailTo.class);
        return skuDetailTo;
    }

    /**
     * Map作为缓存【本地缓存】：优缺点
     * 优点：
     * 缺点：容量问题，集群化缓存的数据同步问题
     */
    private Map<Long,SkuDetailTo> skuCache1 = new ConcurrentHashMap<>();
    //本地缓存方法
    public SkuDetailTo getSkuDetailFromMap(Long skuId) {
       // 1、先看缓存
        SkuDetailTo cacheData = skuCache1.get(skuId);
        //2、判断
        if(cacheData == null){
            //3、缓存没有，真正查询【回源（回到数据源头真正检索）】【提高缓存的命中率】
            // 1 - 0/1： 0%
            // 2 - 1/2: 50%
            // N - (N-1)/N： 无限接近100%
            //缓存命中率提升到100%；预缓存机制；
            SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
            skuCache1.put(skuId,fromRpc);
            return fromRpc;
        }
        //4、缓存有
        return cacheData;
    }
}
