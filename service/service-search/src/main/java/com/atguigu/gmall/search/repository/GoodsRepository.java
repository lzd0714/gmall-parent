package com.atguigu.gmall.search.repository;

import com.atguigu.gmall.model.list.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-05 17:31
 **/
@Repository
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
