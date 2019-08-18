package com.vipkid.auto.example.mapping;

import com.vipkid.auto.example.exception.AutoExampleException;
import com.vipkid.auto.example.handler.criterion.CriterionHandler;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;

/**
 * @author hujun1
 * @date 2019-08-16 16:06
 */
public class FieldToCriterionHandlerMapping {

  private Field field;

  private CriterionHandler criterionHandler;

  public FieldToCriterionHandlerMapping(Field field, CriterionHandler criterionHandler) {
    this.field = field;
    this.criterionHandler = criterionHandler;

  }

  public void mapping(Object parameter,Example.Criteria criteria) {
    Object value;
    try {
      value = field.get(parameter);
    } catch (IllegalAccessException e) {
      throw new AutoExampleException(e);
    }
    boolean nullOrEmptyString = null == value || ((value instanceof String) && "".equals(value));
    if (!nullOrEmptyString) {
      criterionHandler.handle(criteria,field.getName(),value);
    }
  }

  public Field getField() {
    return field;
  }

  public void setField(Field field) {
    this.field = field;
  }

  public CriterionHandler getCriterionHandler() {
    return criterionHandler;
  }

  public void setCriterionHandler(CriterionHandler criterionHandler) {
    this.criterionHandler = criterionHandler;
  }


}
