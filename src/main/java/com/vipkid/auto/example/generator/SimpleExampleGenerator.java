package com.vipkid.auto.example.generator;

import com.vipkid.auto.example.annotation.AndEqualTo;
import com.vipkid.auto.example.annotation.AndIsNotNull;
import com.vipkid.auto.example.annotation.AndIsNull;
import com.vipkid.auto.example.annotation.AutoExample;
import com.vipkid.auto.example.annotation.Ignore;
import com.vipkid.auto.example.annotation.OrIsNotNull;
import com.vipkid.auto.example.annotation.OrIsNull;
import com.vipkid.auto.example.annotation.registry.CriterionAnnotationRegistry;
import com.vipkid.auto.example.cache.CacheBuilder;
import com.vipkid.auto.example.exception.AutoExampleException;
import com.vipkid.auto.example.handler.criterion.CriterionHandler;
import com.vipkid.auto.example.handler.criterion.CriterionHandlerRegistry;
import com.vipkid.auto.example.mapping.FieldToCriterionHandlerMapping;
import org.apache.ibatis.cache.Cache;
import tk.mybatis.mapper.entity.Example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author hujun1
 * @date 2019-08-16 15:37
 */
public class SimpleExampleGenerator implements ExampleGenerator {

  /**
   * 一级缓存，使用静态成员变量，不让JVM垃圾回收
   */
  private static final Cache PRIMARY_CACHE = CacheBuilder.getSafeLruCache("primaryCache");

  @Override
  public Example generate(Object parameter, AutoExample autoExample) {
    Example example = new Example(autoExample.value());
    @SuppressWarnings("unchecked")
    Map<Integer, List<FieldToCriterionHandlerMapping>> map = (LinkedHashMap<Integer, List<FieldToCriterionHandlerMapping>>)PRIMARY_CACHE.getObject(parameter.getClass());
    if (null == map) {
      synchronized (PRIMARY_CACHE) {
        if (null == PRIMARY_CACHE.getObject(parameter.getClass())) {
          map = generateMap(parameter, example);
          PRIMARY_CACHE.putObject(parameter.getClass(),map);
        }
      }
    }
    for (Integer key : Objects.requireNonNull(map).keySet()) {
      Example.Criteria criteria = example.or();
      List<FieldToCriterionHandlerMapping> fieldToCriterionHandlerMappings = map.get(key);
      for (FieldToCriterionHandlerMapping fieldToCriterionHandlerMapping : fieldToCriterionHandlerMappings) {
        fieldToCriterionHandlerMapping.mapping(parameter,criteria);
      }
    }
    if (autoExample.distinct()) {
      example.setDistinct(autoExample.distinct());
    }
    String[] orderBys = autoExample.orderBy();
    for (String orderBy : orderBys) {
      orderBy = orderBy.trim();
      if (!"".equals(orderBy)) {
        if (orderBy.toUpperCase().endsWith("DESC")) {
          example.orderBy(orderBy.substring(0,orderBy.length()-4)).desc();
        } else if (orderBy.toUpperCase().endsWith("ASC")) {
          example.orderBy(orderBy.substring(0,orderBy.length()-3)).asc();
        }else {
          example.orderBy(orderBy).asc();
        }
      }
    }
    return example;
  }

  /**
   * 创建一个Criteria的Map，因为一个查询可能涉及多个oredCriteria
   * @param parameter
   * @param example
   * @return
   */
  private Map<Integer, List<FieldToCriterionHandlerMapping>> generateMap(Object parameter, Example example) {
    Map<Integer, List<FieldToCriterionHandlerMapping>> map =new LinkedHashMap<>();
    Field[] declaredFields = parameter.getClass().getDeclaredFields();
    if (declaredFields.length == 0) {
      throw new AutoExampleException("the class: " + parameter.getClass().getName() + " does not have even one field!");
    }
    for (Field field : declaredFields) {
      field.setAccessible(true);
      Annotation[] annotations = field.getAnnotations();
      if (annotations.length == 0 || !CriterionAnnotationRegistry.containsAnyOne(annotations)) {
        List<FieldToCriterionHandlerMapping> fieldToCriterionHandlerMappings = map.get(0);
        addToMap(fieldToCriterionHandlerMappings,map,AndEqualTo.class.getName(),field,0);
        continue;
      }
      for (Annotation annotation : annotations) {
        String name = annotation.getClass().getInterfaces()[0].getName();
        if (!CriterionAnnotationRegistry.contains(name)) {
          continue;
        }
        Class<? extends Annotation> aClass = annotation.annotationType();
        Integer criterionNum;
        try {
          Method value = aClass.getMethod("value");
          value.setAccessible(true);
          criterionNum = (Integer)value.invoke(annotation);
        } catch (Exception e) {
          throw new AutoExampleException("value field not found in annotation: " + name);
        }
        List<FieldToCriterionHandlerMapping> fieldToCriterionHandlerMappings = map.get(criterionNum);
        addToMap(fieldToCriterionHandlerMappings,map,name,field,criterionNum);
      }
    }
    return map;
  }

  private void addToMap(List<FieldToCriterionHandlerMapping> fieldToCriterionHandlerMappings,
                        Map<Integer, List<FieldToCriterionHandlerMapping>> map,
                        String name,
                        Field field,
                        Integer criteriaNum) {
    if (null == fieldToCriterionHandlerMappings) {
      fieldToCriterionHandlerMappings = new ArrayList<>();
      map.put(criteriaNum, fieldToCriterionHandlerMappings);
    }
    CriterionHandler criterionHandler = CriterionHandlerRegistry.getCriterionHandler(name);
    fieldToCriterionHandlerMappings.add(new FieldToCriterionHandlerMapping(field, criterionHandler));
  }
}
