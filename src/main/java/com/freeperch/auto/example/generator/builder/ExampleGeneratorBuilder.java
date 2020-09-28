package com.freeperch.auto.example.generator.builder;

import com.freeperch.auto.example.generator.CachingExampleGenerator;
import com.freeperch.auto.example.generator.ExampleGenerator;
import com.freeperch.auto.example.generator.SimpleExampleGenerator;

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
