package com.github.z.auto.example.generator.builder;

import com.github.z.auto.example.generator.CachingExampleGenerator;
import com.github.z.auto.example.generator.ExampleGenerator;
import com.github.z.auto.example.generator.SimpleExampleGenerator;

/**
 * @author hujun
 * date 2019-08-16 17:03
 */
public abstract class ExampleGeneratorBuilder {

  public static ExampleGenerator buildCachingSimpleExampleGenerator() {
    SimpleExampleGenerator simpleExampleGenerator = new SimpleExampleGenerator();
    return new CachingExampleGenerator(simpleExampleGenerator);
  }

}
