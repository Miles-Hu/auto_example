package com.vipkid.auto.example.annotation.registry;

import com.vipkid.auto.example.annotation.AndEqualTo;
import com.vipkid.auto.example.annotation.AndGreaterThan;
import com.vipkid.auto.example.annotation.AndGreaterThanOrEqualTo;
import com.vipkid.auto.example.annotation.AndIn;
import com.vipkid.auto.example.annotation.AndIsNotNull;
import com.vipkid.auto.example.annotation.AndIsNull;
import com.vipkid.auto.example.annotation.AndLessThan;
import com.vipkid.auto.example.annotation.AndLessThanOrEqualTo;
import com.vipkid.auto.example.annotation.AndLike;
import com.vipkid.auto.example.annotation.AndNotEqualTo;
import com.vipkid.auto.example.annotation.AndNotIn;
import com.vipkid.auto.example.annotation.AndNotLike;
import com.vipkid.auto.example.annotation.OrEqualTo;
import com.vipkid.auto.example.annotation.OrGreaterThan;
import com.vipkid.auto.example.annotation.OrGreaterThanOrEqualTo;
import com.vipkid.auto.example.annotation.OrIn;
import com.vipkid.auto.example.annotation.OrIsNotNull;
import com.vipkid.auto.example.annotation.OrIsNull;
import com.vipkid.auto.example.annotation.OrLessThan;
import com.vipkid.auto.example.annotation.OrLessThanOrEqualTo;
import com.vipkid.auto.example.annotation.OrLike;
import com.vipkid.auto.example.annotation.OrNotEqualTo;
import com.vipkid.auto.example.annotation.OrNotIn;
import com.vipkid.auto.example.annotation.OrNotLike;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hujun1
 * @date 2019-08-16 16:20
 */
public class CriterionAnnotationRegistry {

  private static Set<String> criterionAnnotatinSet = new HashSet<>();

  static {
    criterionAnnotatinSet.add(AndEqualTo.class.getName());
    criterionAnnotatinSet.add(AndGreaterThan.class.getName());
    criterionAnnotatinSet.add(AndGreaterThanOrEqualTo.class.getName());
    criterionAnnotatinSet.add(AndIn.class.getName());
    criterionAnnotatinSet.add(AndIsNotNull.class.getName());
    criterionAnnotatinSet.add(AndIsNull.class.getName());
    criterionAnnotatinSet.add(AndLessThan.class.getName());
    criterionAnnotatinSet.add(AndLessThanOrEqualTo.class.getName());
    criterionAnnotatinSet.add(AndLike.class.getName());
    criterionAnnotatinSet.add(AndNotEqualTo.class.getName());
    criterionAnnotatinSet.add(AndNotIn.class.getName());
    criterionAnnotatinSet.add(AndNotLike.class.getName());

    criterionAnnotatinSet.add(OrEqualTo.class.getName());
    criterionAnnotatinSet.add(OrGreaterThan.class.getName());
    criterionAnnotatinSet.add(OrGreaterThanOrEqualTo.class.getName());
    criterionAnnotatinSet.add(OrIn.class.getName());
    criterionAnnotatinSet.add(OrIsNotNull.class.getName());
    criterionAnnotatinSet.add(OrIsNull.class.getName());
    criterionAnnotatinSet.add(OrLessThan.class.getName());
    criterionAnnotatinSet.add(OrLessThanOrEqualTo.class.getName());
    criterionAnnotatinSet.add(OrLike.class.getName());
    criterionAnnotatinSet.add(OrNotEqualTo.class.getName());
    criterionAnnotatinSet.add(OrNotIn.class.getName());
    criterionAnnotatinSet.add(OrNotLike.class.getName());

  }

  public static boolean contains(String annotationName) {
    return criterionAnnotatinSet.contains(annotationName);
  }

}
