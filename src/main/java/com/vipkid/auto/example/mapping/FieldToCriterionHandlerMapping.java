package com.vipkid.auto.example.mapping;

import com.vipkid.auto.example.annotation.AndIsNotNull;
import com.vipkid.auto.example.annotation.AndIsNull;
import com.vipkid.auto.example.annotation.Ignore;
import com.vipkid.auto.example.annotation.OrIsNotNull;
import com.vipkid.auto.example.annotation.OrIsNull;
import com.vipkid.auto.example.exception.AutoExampleException;
import com.vipkid.auto.example.handler.criterion.CriterionHandler;
import tk.mybatis.mapper.entity.Example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author hujun1
 * @date 2019-08-16 16:06
 */
public class FieldToCriterionHandlerMapping {

  private Field field;

  private CriterionHandler criterionHandler;

  private Annotation[] annotations;

  public FieldToCriterionHandlerMapping(Field field, CriterionHandler criterionHandler) {
    this.field = field;
    this.criterionHandler = criterionHandler;
    annotations = field.getDeclaredAnnotations();
  }

  public void mapping(Object parameter,Example.Criteria criteria) {
    Object value;
    try {
      value = field.get(parameter);
    } catch (IllegalAccessException e) {
      throw new AutoExampleException(e);
    }
    if (!shouldSkip(value)) {
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

  private boolean shouldSkip(Object value) {
    boolean nullOrEmptyString = null == value || ((value instanceof String) && "".equals(value));
    boolean canBeNullOrEmptyString = false;
    boolean ignore = false;
    for (Annotation annotation : annotations) {
      if ((annotation instanceof AndIsNull)
          || (annotation instanceof AndIsNotNull)
          || (annotation instanceof OrIsNull)
          || (annotation instanceof OrIsNotNull)) {
        canBeNullOrEmptyString = true;
      }
      if (annotation instanceof Ignore) {
        ignore = true;
      }
    }
    return ignore || (nullOrEmptyString && !canBeNullOrEmptyString);
  }

}
