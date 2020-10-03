package com.github.z.auto.example.generator;

import com.github.z.auto.example.annotation.AutoExample;
import tk.mybatis.mapper.entity.Example;

/**
 * @author hujun
 * date 2019-08-16 15:33
 */
public interface ExampleGenerator {

  Example generate(Object parameter, AutoExample autoExample);

}
