package com.atguigu.gmall.product.bloom;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-05 14:23
 **/

public interface BloomOpsService {
    /**
     * 重建指定布隆过滤器
     * @param bloomName
     */
    void rebuildBloom(String bloomName,BloomDataQueryService dataQueryService);
}
