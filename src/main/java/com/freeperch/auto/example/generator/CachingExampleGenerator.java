package com.freeperch.auto.example.generator;

import com.freeperch.auto.example.cache.CacheBuilder;
import com.freeperch.auto.example.annotation.AutoExample;
import org.apache.ibatis.cache.Cache;
import tk.mybatis.mapper.entity.Example;


/**
 * @author hujun1
 * @date 2019-08-16 15:36
 */
public class CachingExampleGenerator implements ExampleGenerator {

  private ExampleGenerator delegate;

  /**
   * 二级缓存，使用静态成员变量，不让JVM垃圾回收
   */
  private static final Cache SECONDARY_CACHE = CacheBuilder.getSafeLruCache("secondaryCache");

  public CachingExampleGenerator(ExampleGenerator exampleGenerator) {
    this.delegate = exampleGenerator;
  }

  @Override
  public Example generate(Object parameter, AutoExample autoExample) {
    //使用更有效率的二级缓存缓存，需要入参实现hashCode(), equals()方法
    if (autoExample.useSecondaryCache()) {
      Example example = (Example)SECONDARY_CACHE.getObject(parameter);
      if (example == null) {
        synchronized (SECONDARY_CACHE) {
          if (null == SECONDARY_CACHE.getObject(parameter)) {
            example = delegate.generate(parameter, autoExample);
            SECONDARY_CACHE.putObject(parameter,example);
          }
        }
      }
      return example;
    }
    return delegate.generate(parameter,autoExample);
  }
}
