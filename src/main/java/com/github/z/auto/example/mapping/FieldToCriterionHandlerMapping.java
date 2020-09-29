package com.github.z.auto.example.mapping;

import com.github.z.auto.example.annotation.AndIsNotNull;
import com.github.z.auto.example.annotation.AndIsNull;
import com.github.z.auto.example.annotation.OrIsNotNull;
import com.github.z.auto.example.annotation.Ignore;
import com.github.z.auto.example.annotation.OrIsNull;
import com.github.z.auto.example.exception.AutoExampleException;
import com.github.z.auto.example.handler.criterion.CriterionHandler;
import tk.mybatis.mapper.entity.Example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author hujun
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

  /**
   * 没有被@AndIsNull，@ANdIsNotNull，@OrIsNull, @OrIsNotNull注解标记的 null或者空串的property
   * 以及被@Ignore标记的property，应该跳过
   * @param value
   * @return
   */
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
