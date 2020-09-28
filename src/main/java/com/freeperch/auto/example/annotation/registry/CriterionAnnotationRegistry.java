package com.freeperch.auto.example.annotation.registry;

import com.freeperch.auto.example.annotation.AndEqualTo;
import com.freeperch.auto.example.annotation.AndGreaterThan;
import com.freeperch.auto.example.annotation.AndIn;
import com.freeperch.auto.example.annotation.AndLessThan;
import com.freeperch.auto.example.annotation.AndNotEqualTo;
import com.freeperch.auto.example.annotation.AndNotIn;
import com.freeperch.auto.example.annotation.OrEqualTo;
import com.freeperch.auto.example.annotation.OrGreaterThanOrEqualTo;
import com.freeperch.auto.example.annotation.OrIn;
import com.freeperch.auto.example.annotation.OrIsNotNull;
import com.freeperch.auto.example.annotation.OrLessThan;
import com.freeperch.auto.example.annotation.OrLessThanOrEqualTo;
import com.freeperch.auto.example.annotation.OrNotLike;
import com.freeperch.auto.example.annotation.AndGreaterThanOrEqualTo;
import com.freeperch.auto.example.annotation.AndIsNotNull;
import com.freeperch.auto.example.annotation.AndIsNull;
import com.freeperch.auto.example.annotation.AndLessThanOrEqualTo;
import com.freeperch.auto.example.annotation.AndLike;
import com.freeperch.auto.example.annotation.AndNotLike;
import com.freeperch.auto.example.annotation.OrGreaterThan;
import com.freeperch.auto.example.annotation.OrIsNull;
import com.freeperch.auto.example.annotation.OrLike;
import com.freeperch.auto.example.annotation.OrNotEqualTo;
import com.freeperch.auto.example.annotation.OrNotIn;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hujun1
 * @date 2019-08-16 16:20
 */
public class CriterionAnnotationRegistry {

  private static Set<String> criterionAnnotationSet = new HashSet<>();

  static {
    criterionAnnotationSet.add(AndEqualTo.class.getName());
    criterionAnnotationSet.add(AndGreaterThan.class.getName());
    criterionAnnotationSet.add(AndGreaterThanOrEqualTo.class.getName());
    criterionAnnotationSet.add(AndIn.class.getName());
    criterionAnnotationSet.add(AndIsNotNull.class.getName());
    criterionAnnotationSet.add(AndIsNull.class.getName());
    criterionAnnotationSet.add(AndLessThan.class.getName());
    criterionAnnotationSet.add(AndLessThanOrEqualTo.class.getName());
    criterionAnnotationSet.add(AndLike.class.getName());
    criterionAnnotationSet.add(AndNotEqualTo.class.getName());
    criterionAnnotationSet.add(AndNotIn.class.getName());
    criterionAnnotationSet.add(AndNotLike.class.getName());

    criterionAnnotationSet.add(OrEqualTo.class.getName());
    criterionAnnotationSet.add(OrGreaterThan.class.getName());
    criterionAnnotationSet.add(OrGreaterThanOrEqualTo.class.getName());
    criterionAnnotationSet.add(OrIn.class.getName());
    criterionAnnotationSet.add(OrIsNotNull.class.getName());
    criterionAnnotationSet.add(OrIsNull.class.getName());
    criterionAnnotationSet.add(OrLessThan.class.getName());
    criterionAnnotationSet.add(OrLessThanOrEqualTo.class.getName());
    criterionAnnotationSet.add(OrLike.class.getName());
    criterionAnnotationSet.add(OrNotEqualTo.class.getName());
    criterionAnnotationSet.add(OrNotIn.class.getName());
    criterionAnnotationSet.add(OrNotLike.class.getName());

  }

  public static boolean contains(String annotationName) {
    return criterionAnnotationSet.contains(annotationName);
  }

  public static boolean containsAnyOne(Annotation[] annotations) {
    for (Annotation annotation : annotations) {
      boolean contains = contains(annotation.getClass().getInterfaces()[0].getName());
      if (contains) {
        return true;
      }
    }
    return false;
  }

}
