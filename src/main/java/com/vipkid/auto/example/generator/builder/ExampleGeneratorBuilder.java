package com.vipkid.auto.example.generator.builder;

import com.vipkid.auto.example.generator.CachingExampleGenerator;
import com.vipkid.auto.example.generator.ExampleGenerator;
import com.vipkid.auto.example.generator.SimpleExampleGenerator;

/**
 * @author hujun1
 * @date 2019-08-16 17:03
 */
public abstract class ExampleGeneratorBuilder {

  public static ExampleGenerator buildCachingSimpleExampleGenerator() {
    SimpleExampleGenerator simpleExampleGenerator = new SimpleExampleGenerator();
    return new CachingExampleGenerator(simpleExampleGenerator);
  }

}
