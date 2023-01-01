## 一、Introduction

​    Transfer the filter conditions from the client to SpringMVC, and then automatically to MyBatis, eliminating the need to take out the filter conditions from SpringMVC and assemble them into the code required by MyBatis;
The main function of this plug-in is to complete the automatic generation of Example. In daily development, the server receives the request sent by the client, and Spring MVC deserializes the json parameter string passed by the client into java objects. In most cases, it is the parameters of the request database. Developers dynamically assemble SQL statements through these parameters. In the native MyBatis environment, dynamic SQL statements can be used to solve the problem; in the tk-mybatis environment, you can use if-else to judge these Parameters, build an Example to query; this plug-in is based on tk-mybatis, fully driven by annotations, automatically generates an Example according to the data in the java object, and reduces the amount of code for the developer. Only one annotation is needed to complete the automatic generation of the Example.

## 二、Example

1、Introduce the auto-example-spring-boot-starter package, the code is as follows, the version number can currently choose 1.2.4, before introducing this package, make sure that the project already has mybatis, tk-mybatis packages, try to use the latest version:

```xml
<dependency>
  <groupId>com.github.miles-hu</groupId>
  <artifactId>auto-example-spring-boot-starter</artifactId>
  <version>1.2.4</version>
</dependency>
```

2、Add the @AutoExample() annotation on the RequestDto class that needs to automatically generate an Example, and specify the Class object (or full name of the class) of the Bean corresponding to the database table on the annotation, assuming the following RequestDto (definition: springMVC reverses the json string Serialized to above RequestDto):

```java
@AutoExample(Resource.class)
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
// Or use the full name of the Class
@AutoExample(classFullName="com.github.Resource")
public class ManyEqualToDto {
...
}
```

3、Call the interface method of tk-mybatis that uses Example to query, such as selectByExample(), pass the RequestDto as a parameter, and the auto-example plug-in will automatically convert the RequestDto to Example, the code is as follows:

```java
@PostMapping("present/many/equal/to")  
public PageResponse simpleManyEqualToTest(@RequestBody ManyEqualToDto manyEqualToDto) {
    return PageResponse.ok(adamResourceMapper.selectByExample(manyEqualToDto));
  }
// Also support @GetMapping
@GetMapping("present/many/equal/to")  
public PageResponse simpleManyEqualToTest(ManyEqualToDto manyEqualToDto) {
    return PageResponse.ok(adamResourceMapper.selectByExample(manyEqualToDto));
  }
```

​	In the case of not using auto-example, we need to dynamically assemble the Example object, the code is as follows:

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
//  Or useing @GetMapping:
@GetMapping("present/normal/many/equal/to")
public PageResponse simpleNormalManyEqualToTest(NormalManyEqualToDto rDto) {
...
}
```

Auto-example can automatically check non-null and empty strings. We can see that auto-example has saved us a lot of repetitive check codes. Automatically generating Example can be understood as automatically generating dynamic SQL to some extent. With auto-example , no need to write these annoying if-else to manually generate dynamic sql; free your hands 🤲O(∩_∩)O~~;

## 三、There Are More

​    If readers observe carefully, they will find that the Example in the introductory example only uses the criteria.andEqualTo() method to construct the Example. The reader may ask: What if I want richer filter conditions?
​    (≖ ◡ ≖)haha，auto-example provides a lot of annotations added to the field (@AndLike(int), @AndIn(int), @AndGreaterThan(int), @AndGreaterThanOrEqualTo(int), @AndLessThan(int), @AndLessThanOrEqualTo(int), @AndIsNotNull(int), @AndIsNull(int), @AndNotLike(int), @AndNotIn(int), @AndNotEqualTo(int), @AndEqualTo(int), @OrLike(int), @OrdIn(int), @OrGreaterThan (int), @OrGreaterThanOrEqualTo(int), @OrLessThan(Integer), @OrLessThanOrEqualTo(int), @OrIsNotNull(int), @OrIsNull(int), @OrNotLike(int), @OrNotIn(int), @OrNotEqualTo(int ), @OrEqualTO(int)), corresponding to most of the above methods in Example$Criteria, which is enough to cover 99% of daily development needs. For a simple case of using these annotations, refer to the test project auto_example_demo of this project, project address:
https://github.com/Miles-Hu/auto_example_demo

Download the project, open it with IDEA, and find the com.freeperch.auto.example.BasicFunctionTests class in the test/java directory (the corresponding Controller is com.freeperch.auto.example.controller.AutoExampleBasicController), which contains multiple test cases , try to run these test cases, and observe the sql statement printed by the project, which can help you better understand and use the auto-example plug-in; 😃

It involves a new annotation @Ignore, which means to tell auto-example to ignore this field

'like' query, auto-example will automatically add "%" after the parameter as a wildcard, not in front.

'In' query currently only supports the use of Collection, not arrays, because the Example implementation of tk-mybatis does not support arrays;

​    If the reader finishes running the above test cases, they will find that these test cases use one Example$Criteria to complete the query. What should I do if I want 2, 3, or even more Example$Criteria to complete the query? Still in the above auto-example-test project, try to run com.freeperch.auto.example.AdvancedTests.test1() (the corresponding Controller is com.freeperch.auto.example.api.AutoExampleAdvancedServiceApi), and carefully observe the console In the printed sql statement, you will find that it is very simple to implement multiple Example$Criteria to complete the query;

​    In com.freeperch.auto.example.AdvancedTests, test2() shows how to complete the orderBy query, test3() shows how to complete the distinct query, and test4() shows how to use the auto-example's second-level cache (first-level cache is enabled by default), test5() shows the performance comparison of using the first-level cache, the second-level cache, and not using auto-example. On the author's machine, the performance of the first-level cache is weak at the beginning of operation, and the performance of the second-level cache and The performance of not using auto-example is almost the same. As the running time of the project increases, the performance of the first-level cache begins to improve, which is close to the other two. test6() shows that auto-example is compatible with the PageHelper paging plug-in, test7(), test8 () is the display of the introductory example;

## 四、Be Careful

1、Since auto-example generates Example according to the order of RequestDto fields, the order of fields in RequestDto is very important, and readers need to pay special attention when using it;

## 五、Life Cycle

​    Auto-example is implemented based on the Interceptor of mybatis, the Interceptor is saved by the Configuration of mybatis, and the Configuration is saved in the SqlSessionFactory, so the Interceptor instance of auto-example is only one SqlSessionFactory;

​    Level 1 and 2 cache, referenced by a static field, is loaded when it is used for the first time, the program is closed and recycled, the level 1 cache is automatically enabled, and readers cannot perform configuration operations, the level 2 cache can be configured and enabled, refer to com.freeperch.auto .example.AdvancedTests.test4();

## 六、The Last But Not Least  

​    Hopyfully, readers will run all the test cases above com.freeperch.auto.example.BasicFunctionTests and com.freeperch.auto.example.AdvancedTests, so that they can use auto-example very proficiently; of course, these are just some simple ideas that the author thought of. Readers can also use the auto-example to use it in more scenarios. If you have any questions, please feel free to contact miles.j.hoo@gmail.com, enjoy! 😋

