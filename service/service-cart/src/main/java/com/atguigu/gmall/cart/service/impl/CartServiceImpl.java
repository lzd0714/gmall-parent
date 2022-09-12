package com.atguigu.gmall.cart.service.impl;

import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.common.auth.AuthUtils;
import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.execption.GmallException;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.feign.product.SkuProductFeignClient;
import com.atguigu.gmall.model.cart.CartInfo;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.vo.user.UserAuthInfo;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-08 18:49
 **/
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    SkuProductFeignClient skuFeignClient;
    @Autowired
    ThreadPoolExecutor executor;
    @Override
    public SkuInfo addToCart(Long skuId, Integer num) {
        //1.决定购物车用什么键
        String cartKey = determinCartKey();
        //2.给购物车添加指定商品
        SkuInfo skuInfo = addItemToCart(skuId,num,cartKey);
        //3.购物车超时设置，自动延期
        UserAuthInfo authInfo = AuthUtils.getCurrentAuthInfo();
        if(authInfo.getUserId() == null){
            //用户未登录状态一直操作临时购物车
            String tempKey = SysRedisConst.CART_KEY + authInfo.getUserTempId();
            //临时购物车都有过期时间，自动延期
            redisTemplate.expire(tempKey,90, TimeUnit.DAYS);
        }
        return skuInfo;
    }

    /**
     * 给购物车中添加商品
     * @param skuId
     * @param num
     * @param cartKey
     * @return
     */
    @Override
    public SkuInfo addItemToCart(Long skuId, Integer num, String cartKey) {
        //拿到购物车
        BoundHashOperations<String, String, String> cart = redisTemplate.boundHashOps(cartKey);
        Boolean hasKey = cart.hasKey(skuId.toString());
        //获取当前购物车的最大数量
        Long itemSize = cart.size();
        //1.如果这个skuId之前没有添加过，就新增
        if (!hasKey){
            if (itemSize + 1 > SysRedisConst.CART_ITEMS_LIMIT){
                //异常机制
                throw new GmallException(ResultCodeEnum.CART_OVERFLOW);
            }
            //1.1远程调用查询详情
            SkuInfo data = skuFeignClient.getSkuInfo(skuId).getData();
            //1.2 转为购物车中得模型
            CartInfo item = converSkuInfo2CartInfo(data);
            //1.3设置好数量
            item.setSkuNum(num);
            //1.4保存到redis中
            cart.put(skuId.toString(), Jsons.toStr(item));
            return data;
        }else{
            //2.redis中原来就有，就修改队形的商品的数量
            //2.1 获取实时价格
            BigDecimal price = skuFeignClient.getSku1010Price(skuId).getData();
            //2.2获取原来的信息
            CartInfo cartInfo = getItemFromCart(cartKey,skuId);
            //2.3更新商品
            cartInfo.setSkuNum(cartInfo.getSkuNum()+num);
            cartInfo.setCartPrice(price);
            cartInfo.setUpdateTime(new Date());
            //2.4同步到redis
            cart.put(skuId.toString(),Jsons.toStr(cartInfo));
            SkuInfo  skuInfo = converCartInfo2SkuInfo(cartInfo);
            return skuInfo;
        }
    }

    /**
     * 把CartInfo转为SkuInfo
     * @param cartInfo
     * @return
     */
    private SkuInfo converCartInfo2SkuInfo(CartInfo cartInfo) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSkuName(cartInfo.getSkuName());
        skuInfo.setSkuDefaultImg(cartInfo.getImgUrl());
        skuInfo.setId(cartInfo.getSkuId());
        return skuInfo;
    }

    /**
     * 根据cartKey和SkuId获取原来的商品详情
     * @param cartKey
     * @param skuId
     * @return
     */
    @Override
    public CartInfo getItemFromCart(String cartKey, Long skuId) {
        BoundHashOperations<String, String, String> hashOps= redisTemplate.boundHashOps(cartKey);
        String jsonCart = hashOps.get(skuId.toString());
        return Jsons.toObj(jsonCart,CartInfo.class);
    }



    /**
     * 将SkuInfo转为CartInfo
     * @param data
     * @return
     */
    private CartInfo converSkuInfo2CartInfo(SkuInfo data) {
        CartInfo cartInfo = new CartInfo();
        cartInfo.setSkuId(data.getId());
        cartInfo.setCartPrice(data.getPrice());
        cartInfo.setImgUrl(data.getSkuDefaultImg());
        cartInfo.setSkuName(data.getSkuName());
        cartInfo.setIsChecked(1);
        cartInfo.setCreateTime(new Date());
        cartInfo.setUpdateTime(new Date());
        cartInfo.setSkuPrice(data.getPrice());
        return cartInfo;
    }

    /**
     * 根据用户登录信息决定购物车使用哪个键
     * @return
     */
    @Override
    public String determinCartKey() {
        UserAuthInfo authInfo = AuthUtils.getCurrentAuthInfo();
        String cartKey = SysRedisConst.CART_KEY;
        if (authInfo.getUserId() != null){
            //用户登录了
            cartKey = cartKey + authInfo.getUserId();
        }else {
            //用户没有登录使用临时id
            cartKey = cartKey + authInfo.getUserTempId();
        }
        return cartKey;
    }
    /**
     * 查询购物车中的信息
     * @param cartKey
     * @return
     */
    @Override
    public List<CartInfo> getCartList(String cartKey) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(cartKey);
        //流式编程
        List<CartInfo> infos = hashOps.values().stream()
                .map((str) -> Jsons.toObj(str, CartInfo.class))//先将字符流转成对象流，map是映射
                .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))//按照添加购物城的时间排序
                .collect(Collectors.toList());
        //顺便把购物车中所有商品的价格再次查询一遍进行更新。 异步不保证立即执行。
        //不用等价格更新。 异步情况下拿不到老请求
        //1、老请求
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //【异步会导致feign丢失请求】
        executor.submit( ()->  {
            //2、绑定请求到到这个线程
            RequestContextHolder.setRequestAttributes(requestAttributes);
            updateCartAllItemsPrice(cartKey,infos);
            //3、移除数据
            RequestContextHolder.resetRequestAttributes();
        });
//        updateCartAllItemsPrice(cartKey,infos);
        return infos;
    }

    /**
     * 合并购物车
     */
    @Override
    public void mergeUserAndTempCart() {
        UserAuthInfo authInfo = AuthUtils.getCurrentAuthInfo();
        //1.判断是否需要合并
        if (authInfo.getUserId() != null && !StringUtil.isAllBlank(authInfo.getUserTempId())){
            //2.可能需要合并
            //3.临时购物车可能有东西，和宾沟删除临时购物车
            String tempCartKey = SysRedisConst.CART_KEY + authInfo.getUserTempId();
            //3.1 获取临时购物车的所有商品
            List<CartInfo> tempCartList = getCartList(tempCartKey);
            if (tempCartList != null && tempCartList.size() > 0){
                //临时购物车有数据，需要合并
                String userCartKey = SysRedisConst.CART_KEY + authInfo.getUserId();
                for (CartInfo info : tempCartList) {
                    Long skuId = info.getSkuId();
                    Integer skuNum = info.getSkuNum();
                    addItemToCart(skuId,skuNum,userCartKey);
                    //合并成就删除一个
                    redisTemplate.opsForHash().delete(tempCartKey,skuId.toString());
                }
            }
        }

    }

    /**
     *  修改购物车中得商品数量
     * @param skuId
     * @param updateNum
     * @param cartKey
     */
    @Override
    public void updateItemNum(Long skuId, Integer updateNum, String cartKey) {
        //1.拿到购物车
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(cartKey);
        //2.拿到商品
        CartInfo cartInfo = getItemFromCart(cartKey, skuId);
        //3.修改数量
        cartInfo.setSkuNum(cartInfo.getSkuNum()+updateNum);
        //4.修改时间
        cartInfo.setUpdateTime(new Date());
        //5.保存到购物车中
        hashOps.put(skuId.toString(),Jsons.toStr(cartInfo));

    }

    /**
     * 修改购物车的勾选状态
     * @param skuId
     * @param status
     * @param cartKey
     */
    @Override
    public void checkCart(Long skuId, Integer status, String cartKey) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(cartKey);
        CartInfo cartInfo = getItemFromCart(cartKey, skuId);
        cartInfo.setIsChecked(status);
        cartInfo.setUpdateTime(new Date());
        hashOps.put(skuId.toString(),Jsons.toStr(cartInfo));
    }

    /**
     * 从购物车中删除指定商品
     * @param skuId
     * @param cartKey
     */
    @Override
    public void deleteCartItem(Long skuId, String cartKey) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(cartKey);
        hashOps.delete(skuId.toString());
    }

    /**
     * 删除购物车中选中的商品
     * @param cartKey
     */
    @Override
    public void deleteChecked(String cartKey) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(cartKey);
        //1、拿到选中的商品，并删除。收集所有选中商品的id
        List<String> ids = getCheckedItems(cartKey).stream()
                .map(cartInfo -> cartInfo.getSkuId().toString())
                .collect(Collectors.toList());

        if(ids!=null && ids.size() > 0){
            hashOps.delete(ids.toArray());
        }

        //stream 不是默认并发。默认串行；
    }

    /**
     * 获取指定购物车中所有选中的商品
     * @param cartKey
     * @return
     */
    @Override
    public List<CartInfo> getCheckedItems(String cartKey) {
        List<CartInfo> cartList = getCartList(cartKey);
        List<CartInfo> checkedItems = cartList.stream()
                .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                .collect(Collectors.toList());
        return checkedItems;
    }

    /**
     * 更新这个购物车中所有商品的价格
     * @param cartKey
     * @param cartInfos  所有的商品
     */
    @Override
    public void updateCartAllItemsPrice(String cartKey, List<CartInfo> cartInfos) {
        BoundHashOperations<String, String, String> cartOps =
                redisTemplate.boundHashOps(cartKey);

        System.out.println("更新价格启动："+Thread.currentThread());
        //200个商品  4s
        cartInfos.stream()
                .forEach(cartInfo -> {
                    //1、查出最新价格  15ms
                    Result<BigDecimal> price = skuFeignClient.getSku1010Price(cartInfo.getSkuId());
                    //2、设置新价格
                    cartInfo.setSkuPrice(price.getData());
                    cartInfo.setUpdateTime(new Date());
                    //3、更新购物车价格  5ms
                    cartOps.put(cartInfo.getSkuId().toString(),Jsons.toStr(cartInfo));
                });
        System.out.println("更新价格结束："+Thread.currentThread());

    }
}
