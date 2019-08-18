package com.vipkid.auto.example.handler.criterion;

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
import com.vipkid.auto.example.handler.criterion.impl.AndEqualToHandler;
import com.vipkid.auto.example.handler.criterion.impl.AndGreaterThanHandler;
import com.vipkid.auto.example.handler.criterion.impl.AndGreaterThanOrEqualToHandler;
import com.vipkid.auto.example.handler.criterion.impl.AndInHandler;
import com.vipkid.auto.example.handler.criterion.impl.AndIsNotNullHandler;
import com.vipkid.auto.example.handler.criterion.impl.AndIsNullHandler;
import com.vipkid.auto.example.handler.criterion.impl.AndLessThanHandler;
import com.vipkid.auto.example.handler.criterion.impl.AndLessThanOrEqualToHandler;
import com.vipkid.auto.example.handler.criterion.impl.AndLikeHandler;
import com.vipkid.auto.example.handler.criterion.impl.AndNotEqualToHandler;
import com.vipkid.auto.example.handler.criterion.impl.AndNotInHandler;
import com.vipkid.auto.example.handler.criterion.impl.AndNotLikeHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrEqualToHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrGreaterThanHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrGreaterThanOrEqualToHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrInHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrIsNotNullHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrIsNullHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrLessThanHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrLessThanOrEqualToHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrLikeHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrNotEqualToHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrNotInHandler;
import com.vipkid.auto.example.handler.criterion.impl.OrNotLikeHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hujun1
 * @date 2019-08-16 12:01
 */
public class CriterionHandlerRegistry {

  private static Map<String, CriterionHandler> registry = new HashMap<>(32);
  static {
    register(AndEqualTo.class.getName(), new AndEqualToHandler());
    register(AndGreaterThan.class.getName(), new AndGreaterThanHandler());
    register(AndGreaterThanOrEqualTo.class.getName(), new AndGreaterThanOrEqualToHandler());
    register(AndIn.class.getName(), new AndInHandler());
    register(AndIsNotNull.class.getName(), new AndIsNotNullHandler());
    register(AndIsNull.class.getName(), new AndIsNullHandler());
    register(AndLessThan.class.getName(), new AndLessThanHandler());
    register(AndLessThanOrEqualTo.class.getName(), new AndLessThanOrEqualToHandler());
    register(AndLike.class.getName(), new AndLikeHandler());
    register(AndNotEqualTo.class.getName(), new AndNotEqualToHandler());
    register(AndNotIn.class.getName(), new AndNotInHandler());
    register(AndNotLike.class.getName(), new AndNotLikeHandler());

    register(OrEqualTo.class.getName(), new OrEqualToHandler());
    register(OrGreaterThan.class.getName(), new OrGreaterThanHandler());
    register(OrGreaterThanOrEqualTo.class.getName(), new OrGreaterThanOrEqualToHandler());
    register(OrIn.class.getName(), new OrInHandler());
    register(OrIsNotNull.class.getName(), new OrIsNotNullHandler());
    register(OrIsNull.class.getName(), new OrIsNullHandler());
    register(OrLessThan.class.getName(), new OrLessThanHandler());
    register(OrLessThanOrEqualTo.class.getName(), new OrLessThanOrEqualToHandler());
    register(OrLike.class.getName(), new OrLikeHandler());
    register(OrNotEqualTo.class.getName(), new OrNotEqualToHandler());
    register(OrNotIn.class.getName(), new OrNotInHandler());
    register(OrNotLike.class.getName(), new OrNotLikeHandler());
  }

  public static void register(String criterion, CriterionHandler criterionHandler) {
    registry.put(criterion, criterionHandler);
  }

  public static CriterionHandler getCriterionHandler(String criterion) {
    return registry.get(criterion);
  }
}
