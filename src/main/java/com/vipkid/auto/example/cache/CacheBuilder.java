package com.vipkid.auto.example.cache;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.cache.impl.PerpetualCache;

/**
 * @author hujun1
 * @date 2019-08-16 15:45
 */
public class CacheBuilder {

  /**
   * 使用MyBatis的缓存实现
   * @param name
   * @return
   */
  public static Cache getSafeLruCache(String name) {
    PerpetualCache perpetualCache = new PerpetualCache(name);
    //默认Lru存储1024个元素
    LruCache lruCache = new LruCache(perpetualCache);
    return new SynchronizedCache(lruCache);
  }

}
