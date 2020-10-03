package com.github.z.auto.example.handler.criterion;

import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author hujun
 * date 2019-08-16 11:59
 */
public abstract class CriterionHandler {

  protected Object checkTime(String property, Object value) {
    if (property.toLowerCase().endsWith("time")) {
      if (value instanceof Long) {
        value = new Date((Long)value);
      }
    }
    return value;
  }

  public abstract void handle(Example.Criteria criteria,String property, Object value);

}
