package com.freeperch.auto.example.handler.criterion;

import com.freeperch.auto.example.annotation.AndEqualTo;
import com.freeperch.auto.example.annotation.AndGreaterThan;
import com.freeperch.auto.example.annotation.AndIn;
import com.freeperch.auto.example.annotation.AndLessThan;
import com.freeperch.auto.example.annotation.AndNotIn;
import com.freeperch.auto.example.annotation.OrGreaterThanOrEqualTo;
import com.freeperch.auto.example.annotation.OrIn;
import com.freeperch.auto.example.annotation.OrIsNotNull;
import com.freeperch.auto.example.annotation.OrLessThan;
import com.freeperch.auto.example.annotation.OrLessThanOrEqualTo;
import com.freeperch.auto.example.annotation.OrNotLike;
import com.freeperch.auto.example.handler.criterion.impl.AndGreaterThanOrEqualToHandler;
import com.freeperch.auto.example.handler.criterion.impl.AndIsNotNullHandler;
import com.freeperch.auto.example.handler.criterion.impl.AndIsNullHandler;
import com.freeperch.auto.example.handler.criterion.impl.AndLessThanHandler;
import com.freeperch.auto.example.handler.criterion.impl.AndLessThanOrEqualToHandler;
import com.freeperch.auto.example.handler.criterion.impl.AndNotEqualToHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrEqualToHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrIsNotNullHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrLikeHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrNotLikeHandler;
import com.freeperch.auto.example.annotation.AndGreaterThanOrEqualTo;
import com.freeperch.auto.example.annotation.AndIsNotNull;
import com.freeperch.auto.example.annotation.AndIsNull;
import com.freeperch.auto.example.annotation.AndLessThanOrEqualTo;
import com.freeperch.auto.example.annotation.AndLike;
import com.freeperch.auto.example.annotation.AndNotEqualTo;
import com.freeperch.auto.example.annotation.AndNotLike;
import com.freeperch.auto.example.annotation.OrEqualTo;
import com.freeperch.auto.example.annotation.OrGreaterThan;
import com.freeperch.auto.example.annotation.OrIsNull;
import com.freeperch.auto.example.annotation.OrLike;
import com.freeperch.auto.example.annotation.OrNotEqualTo;
import com.freeperch.auto.example.annotation.OrNotIn;
import com.freeperch.auto.example.handler.criterion.impl.AndEqualToHandler;
import com.freeperch.auto.example.handler.criterion.impl.AndGreaterThanHandler;
import com.freeperch.auto.example.handler.criterion.impl.AndInHandler;
import com.freeperch.auto.example.handler.criterion.impl.AndLikeHandler;
import com.freeperch.auto.example.handler.criterion.impl.AndNotInHandler;
import com.freeperch.auto.example.handler.criterion.impl.AndNotLikeHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrGreaterThanHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrGreaterThanOrEqualToHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrInHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrIsNullHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrLessThanHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrLessThanOrEqualToHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrNotEqualToHandler;
import com.freeperch.auto.example.handler.criterion.impl.OrNotInHandler;

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
