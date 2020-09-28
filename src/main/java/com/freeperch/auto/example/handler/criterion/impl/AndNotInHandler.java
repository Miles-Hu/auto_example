package com.freeperch.auto.example.handler.criterion.impl;

import com.freeperch.auto.example.handler.criterion.CriterionHandler;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;

/**
 * @author hujun1
 * @date 2019-08-16 14:24
 */
public class AndNotInHandler extends CriterionHandler {

  @Override
  public void handle(Example.Criteria criteria,String property, Object value) {
    if (value instanceof Collection) {
      Collection c = (Collection) value;
      if (c.size() == 0) {
        return;
      }
      criteria.andNotIn(property,(Iterable) value);
    }

  }
}
