package com.vipkid.auto.example;

import com.vipkid.auto.example.interceptor.AutoExampleInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author hujun1
 * @date 2019-08-18 11:15
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class AutoExampleAutoConfiguration {

  @Autowired
  private List<SqlSessionFactory> sqlSessionFactoryList;

  @PostConstruct
  public void addPageInterceptor() {
    AutoExampleInterceptor interceptor = new AutoExampleInterceptor();
    for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
      sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
    }
  }

}
