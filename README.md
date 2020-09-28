## 一、插件简介

​    将过滤条件从客户端到SpringMVC，然后自动到MyBatis，免去从SpringMVC中拿出过滤条件，再组装成MyBatis需要的样子的代码；
本插件主要功能是完成自动生成Example，在日常开发中，服务端接收客户端发送的请求，Spring MVC将客户端传递的json参数字符串反序列化为java对象，这些java对象中包含的数据大多数情况下是请求数据库的参数，开发人员通过这些参数动态拼装SQL语句，在原生MyBatis的环境中，可以使用动态sql语句解决；在tk-mybatis的环境下，则可以使用if-else来判断这些参数，构建Example进行查询；本插件是基于tk-mybatis，完全使用注解驱动，根据java对象中的数据自动生成Example，减少开发人员的代码量，只需1个注解，就能完成自动生成Example，操作简直不要太简单 🤩；

## 二、入门示例

1、引入auto-example-spring-boot-starter包，代码如下，版本号目前可以选择1.0.2-SNAPSHOT，在引入本包之前，确保项目中已经有了mybatis, tk-mybatis的包，尽量使用最新版本：

```xml
<dependency>
  <groupId>com.freeperch</groupId>
  <artifactId>auto-example-spring-boot-starter</artifactId>
  <version>1.0.2-SNAPSHOT</version>
</dependency>
```

2、在需要自动生成Example的RequestDto类上面加上@AutoExample()注解，并在注解上面指明跟数据库表对应的Bean的Class对象，假设有如下RequestDto(定义：springMVC将json串反序列化到RequestDto上面)：

```java
@AutoExample(AdamResource.class)
public class ManyEqualToDto {
  private String name;
  private String chineseName;
  private Integer parentId;
  private String description;
  private Integer ownerId;
  private String ownerEmail;
  private String updatePerson;
  //getter and setter
}
```

3、调用tk-mybatis的使用Example进行查询的接口的方法，比如selectByExample()，将该RequestDto作为参数传递，auto-example插件会自动将该RequestDto转为Example，代码如下：

```java
@PostMapping("present/many/equal/to")  
public PageResponse simpleManyEqualToTest(@RequestBody ManyEqualToDto manyEqualToDto) {
    return PageResponse.ok(adamResourceMapper.selectByExample(manyEqualToDto));
  }
```

​	在不使用auto-example的情况下，该查询需要自己动态拼装Example，代码如下：

```java
@PostMapping("present/normal/many/equal/to")
public PageResponse simpleNormalManyEqualToTest(@RequestBody NormalManyEqualToDto rDto) {
    Example example = new Example(AdamResource.class);
    Example.Criteria criteria = example.createCriteria();
    if (null != rDto.getChineseName() && !"".equals(rDto.getChineseName())) {
      criteria.andEqualTo("chineseName", rDto.getChineseName());
    }
    if (null != rDto.getName() && !"".equals(rDto.getName())) {
      criteria.andEqualTo("name", rDto.getName());
    }
    if (null != rDto.getParentId()) {
      criteria.andEqualTo("parentId", rDto.getParentId());
    }
    if (null != rDto.getDescription() && !"".equals(rDto.getDescription())) {
      criteria.andEqualTo("description", rDto.getDescription());
    }
    if (null != rDto.getOwnerEmail() && !"".equals(rDto.getOwnerEmail())) {
      criteria.andEqualTo("ownerEmail", rDto.getOwnerEmail());
    }
    if (null != rDto.getOwnerId()) {
      criteria.andEqualTo("ownerId", rDto.getOwnerId());
    }
    if (null != rDto.getUpdatePerson() && !"".equals(rDto.getUpdatePerson())) {
      criteria.andEqualTo("updatePerson", rDto.getUpdatePerson());
    }
    return PageResponse.ok(adamResourceMapper.selectByExample(example));
  }
```

Auto-example可以自动进行非null和空串判断，可以看到auto-example帮我们节省了大量重复的判断代码，自动生成Example在某种程度上可以理解为自动生成动态sql，有了auto-example，从此不用再写这些烦人的if-else来手动生成动态sql啦；解放你的双手🤲O(∩_∩)O~~；

## 三、更多功能

​    如果读者观察仔细，会发现入门示例中的Example只使用了criteria.andEqualTo()方法构建Example，读者可能会问：如果我想要更加丰富的过滤条件怎么办呢？

​    (≖ ◡ ≖)嘿嘿，auto-example提供了24个添加在字段上的注解（@AndLike(int)，@AndIn(int)，@AndGreaterThan(int)，@AndGreaterThanOrEqualTo(int)，@AndLessThan(int)，@AndLessThanOrEqualTo(int)，@AndIsNotNull(int)，@AndIsNull(int)，@AndNotLike(int)，@AndNotIn(int)，@AndNotEqualTo(int)，@AndEqualTo(int)，@OrLike(int)，@OrdIn(int)，@OrGreaterThan(int)，@OrGreaterThanOrEqualTo(int)，@OrLessThan(Integer)，@OrLessThanOrEqualTo(int)，@OrIsNotNull(int)，@OrIsNull(int)，@OrNotLike(int)，@OrNotIn(int)，@OrNotEqualTo(int)，@OrEqualTO(int)），跟Example$Criteria上面大部分方法对应起来，足够覆盖日常99%的开发需求，具体使用这些注解的简单案例参考本项目的测试项目auto_example_demo，项目地址：
https://github.com/Miles-Hu/auto_example_demo
下载该项目，使用IDEA打开，找到test/java目录下的com.fengxiao.auto.example.BasicFunctionTests类(对应的Controller是com.fengxiao.auto.example.controller.AutoExampleBasicController)，该类包含28个测试用例，尝试运行这些测试用例，并且观察项目运行打印的sql语句，能帮助你更好地理解和使用auto-example插件喔；😃

其中test27()测试用例是updateByExampleSelective()的使用，该用例涉及到一个新注解@Ignore，该注解的含义是告知auto-example忽略该字段

like查询，auto-example自动会在参数后面加"%"，所以是模糊参数的suffix查询，如果prefix也想模糊，需要读者自己在参数前面加"%"；

in查询，目前只支持使用Collection，不支持使用数组，因为tk-mybatis的Example实现是不支持数组的；

​    如果读者运行完上面的测试用例，会发现这些测试用例都是使用一个Example$Criteria完成的查询，如果我想要2个，3个，甚至更多Example$Criteria完成查询，该怎么办呢？还是在上面的auto-example-test项目中，尝试运行com.fengxiao.auto.example.AdvancedTests.test1()(对应的Controller是com.fengxiao.auto.example.api.AutoExampleAdvancedServiceApi)，并且仔细观察控制台打印的sql语句，会发现实现多个Example$Criteria完成查询也是非常简单的；

​    在com.fengxiao.auto.example.AdvancedTests中，test2()展示了如何完成orderBy查询，test3()展示了如何完成distinct查询，test4()展示了如何使用auto-example的二级缓存(一级缓存是默认开启的)，test5()展示了使用一级缓存、二级缓存、不使用auto-example的性能对比，在作者的机器上，刚开始运行时一级缓存性能较弱，二级缓存和不使用auto-example的性能几乎相同，随着项目运行时间增长，一级缓存性能开始变好，接近其他两者，test6()展示了auto-example跟PageHelper分页插件兼容使用，test7(), test8()则是入门示例的展示；

## 四、注意事项

1、由于auto-example是根据RequestDto字段的顺序来生成Example的，所以RequestDto中的字段顺序是很重要的，读者在使用的时候需要特别注意；

## 五、生命周期

​    Auto-example是基于mybatis的Interceptor实现的，Interceptor由mybatis的Configuration保存，Configuration保存在SqlSessionFactory中，所以auto-example的Interceptor实例是一个SqlSessionFactory只有一个的；

​    一、二级缓存，由静态字段引用，第一次使用时加载完成，程序关闭被回收，一级缓存自动开启，且读者不能进行配置操作，二级缓存可以配置开启，参考com.fengxiao.auto.example.AdvancedTests.test4()；

## 六、最后  

​    希望读者一定要运行完com.fengxiao.auto.example.BasicFunctionTests、com.fengxiao.auto.example.AdvancedTests上面的所有测试用例，这样才能非常熟练地使用auto-example；当然这些都只是作者想到的一些简单测试，读者还能在此基础上自由发挥创造力，将auto-example使用在更多的场景下，enjoy! 😋