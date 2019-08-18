package com.vipkid.auto.example.generator;

import com.vipkid.auto.example.annotation.AutoExample;
import tk.mybatis.mapper.entity.Example;

/**
 * @author hujun1
 * @date 2019-08-16 15:33
 */
public interface ExampleGenerator {

  Example generate(Object parameter, AutoExample autoExample);

}
