package com.atguigu.starter.cache.constant;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-05 14:47
 **/

public class SysRedisConst {
    public static final String NULL_VAL = "x";
    public static final String LOCK_SKU_DETAIL = "lock:sku:detail:";
    public static final Long NULL_VAL_TTL = 60*30L;
    public static final Long SKUDETAIL_TTL = 60*60*24*7L;

    public static final String SKU_INFO_PREFIX = "sku:info:";

    public static final String BLOOM_SKUID = "bloom:skuid";
    public static final String LOCK_PREFIX = "lock:";
}
