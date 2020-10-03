package com.github.z.auto.example.handler.criterion.impl;

import com.github.z.auto.example.handler.criterion.CriterionHandler;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;

/**
 * @author hujun
 * date 2019-08-16 14:24
 */
public class OrInHandler extends CriterionHandler {

  @Override
  public void handle(Example.Criteria criteria,String property, Object value) {
    if (value instanceof Collection) {
      Collection c = (Collection) value;
      if (c.size() == 0) {
        return;
      }
      criteria.orIn(property,(Iterable) value);
    }
  }
}
