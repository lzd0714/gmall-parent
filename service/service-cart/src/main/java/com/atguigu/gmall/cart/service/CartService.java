package com.atguigu.gmall.cart.service;

import com.atguigu.gmall.model.cart.CartInfo;
import com.atguigu.gmall.model.product.SkuInfo;

import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-08 18:49
 **/
public interface CartService {
    /**
     * 将商品保存到redis中
     * @param skuId
     * @param num
     * @return
     */
    SkuInfo addToCart(Long skuId, Integer num);

    /**
     * 决定购物车是用什么键
     * @return
     */
    String determinCartKey();

    /**
     * 给购物车添加商品
     * @param skuId
     * @param num
     * @param cartKey
     * @return
     */
    SkuInfo addItemToCart(Long skuId, Integer num, String cartKey);

    /**
     * 根据cartKey和SkuId获取原来的商品详情
     * @param cartKey
     * @param skuId
     * @return
     */
    CartInfo getItemFromCart(String cartKey, Long skuId);

    /**
     * 查询购物车中的信息
     * @param cartKey
     * @return
     */
    List<CartInfo> getCartList(String cartKey);

    /**
     * 合并购物车
     */
    void mergeUserAndTempCart();

    /**
     *  修改购物车中得商品数量
     * @param skuId
     * @param updateNum
     * @param cartKey
     */
    void updateItemNum(Long skuId, Integer updateNum, String cartKey);

    /**
     * 修改购物车的勾选状态
     * @param skuId
     * @param status
     * @param cartKey
     */
    void checkCart(Long skuId, Integer status, String cartKey);

    /**
     * 从购物车中删除指定商品
     * @param skuId
     * @param cartKey
     */
    void deleteCartItem(Long skuId, String cartKey);

    /**
     * 删除购物车中选中的商品
     * @param cartKey
     */
    void deleteChecked(String cartKey);

    /**
     * 获取指定购物车中所有选中的商品
     * @param cartKey
     * @return
     */
    List<CartInfo> getCheckedItems(String cartKey);
    /**
     * 更新这个购物车中所有商品的价格
     * @param cartKey
     * @param cartInfos  所有的商品
     */
    void updateCartAllItemsPrice(String cartKey,List<CartInfo> cartInfos);


}
