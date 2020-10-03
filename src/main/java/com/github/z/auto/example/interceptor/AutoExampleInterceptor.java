package com.github.z.auto.example.interceptor;

import com.github.z.auto.example.generator.ExampleGenerator;
import com.github.z.auto.example.annotation.AutoExample;
import com.github.z.auto.example.generator.builder.ExampleGeneratorBuilder;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;
import java.util.Properties;

/**
 * 将入参自动转成Example的核心拦截器
 *
 * @author hujun
 * date 2019-08-16 15:03
 */
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class AutoExampleInterceptor implements Interceptor {

  @Override
  @SuppressWarnings("unchecked")
  public Object intercept(Invocation invocation) throws Throwable {
    Object[] args = invocation.getArgs();
    MappedStatement ms = (MappedStatement) args[0];
    //不拦截insert方法
    if (SqlCommandType.INSERT.equals(ms.getSqlCommandType())) {
      return invocation.proceed();
    }
    Object parameter = args[1];
    if(null == parameter){
      //无参直接放行
      return invocation.proceed();
    }
    Object exampleParam = parameter;
    if (parameter instanceof Map) {
      //入参是多参数的情况下，mybatis会自动将参数组装成Map，这里写死为"example"
      //所以入参为多参的时候，修饰Example的注解要为@Param("example")
      //tk-mybatis的多参数入参遵循这个规则
      Map parameterMap = (Map) parameter;
      exampleParam = parameterMap.get("example");
        if (null == exampleParam) {
            //无参直接放行
            return invocation.proceed();
        }
    }
    AutoExample autoExample = exampleParam.getClass().getAnnotation(AutoExample.class);
    //没有加@AutoExample修饰的参数不拦截
    if (null == autoExample) {
      return invocation.proceed();
    }
    ExampleGenerator exampleGenerator = ExampleGeneratorBuilder.buildCachingSimpleExampleGenerator();
    Example example = exampleGenerator.generate(exampleParam, autoExample);
    if (parameter instanceof Map) {
      Map parameterMap = (Map) parameter;
      parameterMap.put("example", example);
      args[1] = parameterMap;
    }else {
      args[1] = example;
    }
    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {

  }
}
