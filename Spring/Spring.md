# Spring

## 	什么是Spring

```
Spring是一个轻量级框架，为我们的开发过程提供了解决方案

MVC:
控制层作用:
1.从客户端或者浏览器接收参数
2.把参数交给业务层处理
3.把处理好的数据存到域中
4.页面完成数据转发或者重定向
(在学习Spring之前,我们都是用Servlet解决,学习了Spring就可以用SpringMVC来解决)

业务层功能:
1.接收控制层的参数
2.调用持久层获取数据
3.对获取的数据进行逻辑处理
4.将处理好的数据返回给控制层
(学习Spring之前主要是使用JavaBean实现的,而Spring提供了很多事务的解决方法)

持久层:
1.连接数据库,对数据库的数据进行CRUD操作
2.将数据返回到持久层
(学之前都是用JDBC,学了Spring之后就可以用SpringTemplate之类的)

现在可能看概念还是感受不到,等学到后面亲自用过就能感受到了
```



## Spring的优势

```
1.降低程序耦合，之前我们创建对象都是通过new关键字,Spring提供的IOC容器可以给我们管理对象
2.对程序功能的添加修改,不用直接改源代码，而是进行拓展
3.对事务进行封装(暂时不明白,后面用了再说)
4.测试代码方便
5.整合了其他优秀的框架
6.对原生JavaAPI进行了封装
```

## 程序耦合

```
耦合就是程序代码之间的依赖程度。(依赖包类与类，方法与方法之间)
比如:如果我们修改了一个功能的代码，导致其他功能不能正常运行,就是程序的耦合度比较高，所以耦合度越低越好，但是耦合度只能降低，而不能完全消除
```

## 常见的解耦的方法

```
一.基于反射的方式解耦
比如jdbc加载驱动的方式有两种:
a. DriverManager.register(new 驱动对象)
b. class.forName("全限定名")

a方式就是通过直接new驱动对象,如果驱动找不到,那么程序编译都无法通过，导致其他正常功能无法运行
b方式就是通过反射的方式获取驱动的,虽然驱动任然找不到，但可以让程序跑起来，不影响其他正常的功能使用

二.使用工厂 + 反射的方式解耦(还是以jdbc为例)

什么是工厂模式:是一个创建对象的方法。为什么叫工厂模式呢，因为和工厂生产有点像。比如我们需要一辆车,我们只需要告诉工厂我们需要工厂的型号,颜色之类的就行了,不用去关心这个车具体是怎么造出来的。同样的,如果我们通过接口创建对象，就是把我们需要东西，通过接口传参的方式，告诉工厂类(就是创建对象的类),然后工厂返回我们需要的对象。这就是工程设计模式

解决方案:
1.通过一个配置文件来管理我们的dao(接口实现类)和(业务类实现类)
  properties配置文件来管理 k=v v是唯一标识=全限定名
2.通过读取配置文件中的内容,获取v。基于反射的技术创建对象

获取资源文件的方法:
InputStream inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("config.properties");
解释:
BeanFactory.class获取了class对象
getClassLoader()表示调用class对象的方法,获取类加载器,通过类加载器可以获得类的基本信息
getResourceAsStream()表示在当前的类路径下获取资源文件config.properties,并把资源文件转换为输入流

//获取到的资源文件内容放到properties对象中
new properties().load(inputStream)


```

<p style="color: red;">主要通过子类实现接口</p>
![factory](https://img2.imgtp.com/2024/04/03/zQm9UvJz.png)

## 基于反射的方式创建对象存在的问题

```java
反射创建对象不是单例,而是多例(每次创建都是一个新的)。
原因是基于反射创建的对象 不存盘，每次用完之后，都会被垃圾回收器回收
问题：每次调用的时候 都需要重新创建对象 比较耗时 耗费性能

解决方法:
import java.io.IOException;
import java.io.InputStram;
import java.util.Properties;

/*
 * 工厂类 专门帮助我们创建对象
 */
 public class Factory{
	 //读取properties里面的配置信息
 static Properties properties;
	//创建一个静态的map,存储资源文件的键值对
 static Map<String, Object> beans;
 
	static{
		try{
			
			InputStram in = Factory.class.getClassLoader().getResourceAsStream("资源文件名称");
			properties = new properties;
			properties.load(in);
			beans = new HashMap<String, Object>();
			while(keys.hashMoreElements()){
				String key = keys.nextElement().toString();
				//通过key,获取value
				String value = properties.getProperty(key);
				Object o = Class.forName(value).newInstance;
				beans.put(key, o);
			}
			
		}catch(Exception){
			e.printStackTrace();
		}
	}
 
	/*
	 *获取Bean的方法
	 *name : 资源文件中的k(键)
	 */
	public static Object getBean(String name){
		return beans.get(name);
	}
 
	 
 }
```



## Spring的bean配置与使用（入门环境的搭建）

<h3 style="color:red;">bean标签是什么</h3>
bean标签: 就是帮助我们管理bean的一个标签,我们可以用class属性来配置该bean标签管理的是哪个Javabean,通过id属性给该bean一个键标记，我们可以在ClassPathXmlApplicationContext()对象中的getBean()方法获取到我们的javabean

```xml
在资源文件中新建一个xml文件

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="accountDao" class="com.mmm.dao.impl.AccountDaoImpl"/>

    <bean id="accountService" class="com.mmm.service.impl.AccountServiceImpl"/>
    
</beans>

```

```java
package com.mmm.test;

import com.mmm.dao.AccountDao;
import com.mmm.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBean {

    public static void main(String[] args) {
        //创建一个spring的ioc容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
        accountDao.addAccount();
        AccountService accountService = (AccountService) applicationContext.getBean("accountService");
        accountService.addAccount();

    }

}
```

### Spring入门环境的搭建的总结

```
第一步: 导入Spring的核心依赖
		spring-context
第二步: 定义dao service的接口以及实现类

第三步: 创建xml配置文件来配置bean标签

第四步: 通过ClassPathXmlApplicationContext类中的getBean("id名")方法来获取我们的javabean
```



## SpringBean中的一些细节

```
一.
我们上面使用到的ApplicationContext实际上是BeanFactory的一个子类
BeanFactory: Bean工厂(IOC容器)的 顶级接口，里面定义了bean工厂的规范和约束
			getBean("id名"),从容器中获得javabean对象
ApplicationContext: 也是一个IOC容器,该类对BeanFactory进行了拓展
			比如:getId()之类的方法
两者之间最大的区别:
	他们bean加载的时间不同
	BeanFactory类懒加载需要的bean,不会随着IOC容器的创建就创建所有的类
	ApplicationContext类则在创建IOC容器的时候就创建了所有的Bean对象
	
二.
关于Application的实现类
	ClassPathXmlApplicationContext: 加载类路径下面的spring配置文件
	FileSystemXmlApplicationContext: 加载磁盘绝对路径下面的配置文件
		比如:new FileSystemXmlApplicationContext("D:\\io\\recource.xml");
	AnnotationConfigApplicationContext 用于注解环境创建容器(还不会，后面详细说)


```



## 依赖注入

 ### 	什么是依赖注入

```
说白了就是我们前面用IOC容器创建了对象,但是我们还没有给对象赋值
依赖注入就是给我们的对象赋值
```

### 怎么依赖注入

#### 	使用setter来进行依赖注入（必须要有set函数）

```xml
分为两种:

一.
基本数据类型和字符型赋值:
	使用<property/>标签的name属性和value属性
	name: 描述bean属性的名称(就是类的属性)
	value: 给属性的赋值(基本数据类型或者字符串类型)
比如: 
<bean id="userService" class="com.mmm.service.impl.UserServiceImpl"/>

    <bean id="user" class="com.mmm.pojo.User">
        <property name="name" value="Unicorn" />
        <property name="age" value="20" />
        <property name="sex" value="男" />
    </bean>

二.
引用数据类型依赖注入:
	也是使用<property/>标签,但是我们要使用ref属性
	ref:引用另外一个bean的id

比如:
    <bean id="car" class="com.mmm.pojo.Car">
        <property name="type" value="宝马"/>
        <property name="brand" value="BMW540I"/>
    </bean>

    <bean id="user" class="com.mmm.pojo.User">
        <property name="name" value="Unicorn" />
        <property name="age" value="20" />
        <property name="sex" value="男" />
        <property name="car" ref="car" />
    </bean>
```

#### 使用构造方法的方式来进行依赖注入（必须要有带参构造函数）

```xml
利用<constructor-arg/>标签的id和value和ref属性来进行复制
	id : bean的属性名 
	value : 基本数据类型和字符串的数据值
	ref : 如果是引用数据类型的话,我们就引用其他bean的id来赋值

因为基本上和上面相同,就是标签改了一下,所以代码省略
```

#### 复杂类型数据注入

```xml
使用以下标签(都要写在<bean><property></property></bean>):

一.
注入数组类型:
<arr>
	<value>值1</value>
    <value>值2</value>
    <value>值3</value>
</arr>

二.
list类型注入:
<list>
	<value>值1</value>
    <value>值2</value>
    <value>值3</value>
</list>

三.
set类型注入:
<set>
	<value>值1</value>
    <value>值2</value>
    <value>值3</value>
</set>

四.
map类型注入:
<map>
	<entry key="key1" value="value1"></entry>
	<entry key="key2" value="value2"></entry>
    <entry key="key3" ref="其他bean的id"></entry>
</map>


```



## 搭建基于IOC的案例

<h4 style="color:red;">QueryRunner的使用和BeanListHandler的使用</h4>
```java
//操作数据库的核心类
QueryRunner类
    query() : 查询语句
	update() : 修改语句
比如:
//创建
private QueryRunner runner;
String sql = "select * from account";
List<Account> accountList = runner.query(sql,
                                      new BenaListHandler<Account>(Account.class));
解释:BeanListHandler<Account>(Account.class)表示把sql语句查询的结构转换为Account泛型的集合
	
//如果只需要返回一个用户,使用BeanHandler就可以    
```

<h4 style="color:red;">配置数据库连接相关信息的bean</h4>
```xml

		<!--配置数据源的相关信息-->
    	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">

        <!--配置数据库的核心-->
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>

        <!--配置数据库的url-->
        <property name="jdbcUrl" value="jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai";


        <!--配置数据库用户名-->
        <property name="user" value="root"/>


        <!--配置数据库的密码-->
        <property name="password" value="root"/>
    </bean>

```

具体案例请看: 



## Spring中注解的应用

### 什么是注解

```
其实在我们学javaSE的时候就接触了注解
	比如：@override
		 @WebServlet等
说白了就是标记我们的类，或者方法特殊的地方
```

### Spring中的注解有哪些

```
1.创建对象的注解@Component(类似于bean标签)
	该注解有一个value属性,如果我们不指定,该值默认是类名首字母的小写
	@component有三个衍生注解
		下面三个注解的作用和@component是一样的,只是说为了明确后面的三层开发(MVC)而衍生的
		@controller: 用于控制层
		@Service: 用于业务层
		@Repository: 用于持久层 
2.给对象赋值的注解@AutoWired(类似于property和construct-arg标签)
	
3.改变作用域的注解(类似于bean标签中的scope属性)
4.生命周期的注解(类似于bean标签中的init-method注解和destroy-method注解)
```

### 定义包扫描的规则

```xml
因为我们上面定义了注解,但是如果我们没有在配置文件中定义包扫描的规则(Spring就不会扫描我们的目录),Spring就不知道那些类是被注解的

我们可以利用<context:component-scan>标签让Spring去扫描我们指定的目录,看目录哪些类是被@Component注解了的
    base-package属性: 给他一个路径,Spring会扫描指定路径下的包，及其子包所有类
    
    比如:
    <context:component-scan base-package="com.mmm"></context:component-scan>
    Spring就会在com.mmm下去扫描所有的包及其子包,看哪些类被@component注解了
    
```



#### @component注解使用案例

```java
package org.mmm;

import org.springframework.stereotype.Controller;

@Controller
public class note {

    public void addAccount(){
        System.out.println("test");
    }
}

package org.mmm;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAccount {

    @Test
    public void test02() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        note note = (org.mmm.note) context.getBean("note");
        note.addAccount();
    }
}


```

#### @autowired注解使用案例

```java
@Service(value = "accountService")
public class AccountServiceImpl implements AccountService {
	
    //默认先看类型，再看名字(名字和bean的名字要一样)
    @Autowired
    private AccountDao AccountDaoImpl;

    @Override
    public void addAccount() {
        AccountDaoImpl.addAccount();
    }
}


```

<h4 style="color:red;">@autowired注解原理</h4>
```
autowired的原理是:先看有没有类型相同的bean,如果有且只有一个,就直接用这个bean对象注入。如果有且有多个,就看有没有键(后面会说IOC容器其实是一个map)和属性名一样的,有名字一样的就注入该bean

IOC容器实际上是一个map
	map里面存的就是键值对,而我们的value(@component注解的属性)就是键,而我们bean就是值
```

@autowired原理图解![@autowired原理.png](https://img2.imgtp.com/2024/04/04/fQZYbLXn.png)

#### @Qualifier和@Resource注解

```
@Qualifier注解主要是根据名称注解(必须配合@Autowired和@Resource使用)
	用法:
	1.配合@Autowired使用
		让容器中的bean有多个和属性的类型匹配,且属性名和bean的key(前面说的键)都不同时,可以配合使用
		比如:
		@Autowired
		@Qualifier("bean的名称")
	2.配合@Resource使用
	@Resource只要是根据类型来进行注入，所以当IOC容器中有多个bean和属性的类型相同时,可以配合使用
		比如:
        @Resource(type = AccountDao.class)
        @Qualifier("accountDaoImpl")

@Resource注解:主要是根据类型匹配,有type和name属性
	比如:@Resource(type = AccountDao.class , name = "accountDaoImpl")

@Resource的总结:
	1.如果同时配置了type和name,则在IOC找同时匹配的bean,如果没有抛异常
	2.如果只指定名称,就去找name匹配的,找不到抛异常
	3.如果只指定类型,如果找不到,或者有多个,抛异常
	4.如果都不知道,默认按名称匹配
@Resource和@Autowired的区别: 前者默认按名称匹配,后面默认按类型匹配
```

#### @Value注解

```
主要是针对字符串类型赋值(使用之前必须要有IOC环境，也就是@Component注解)
用法:
	@Value("Eggsy")
	private String name;
	
	@Value("20")
	private Integer age;
	
	@Value("男")
	private String sex;
```

#### @Scope注解

```java
主要是定义作用域的
    属性是value
	属性有两个值:singleton,prototype
    默认是singleton: 表示是单例bean(不会重复创建对象)
	prototype: 每次创建对象都是一个新的空间
    注意:如果一个bean不加@Scope标签,也是单例bean    
比如:
package org.mmm.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//默认单例bean
@Scope
//@Scope(value = "prototype")这样就是多例bean
public class Account {

    private Integer id;

    private String name;

    private Double money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}

```

#### @PostConstruct和@PreDestroy注解

```java
@PostConstruct: 注解初始化的方法
@PreDestroy: 注解对象将要销毁时的方法

比如:
/**
     * 初始化的方法
     */
    @PostConstruct
    public void init(){
        System.out.println("Account对象创建了");
    }

    /**
     * 对象销毁前执行的方法
     */
    @PreDestroy
    public void destroy(){
        System.out.println("Account对象销毁了");
    }

```

## Spring的xml配置文件和properties文件混合使用

#### 读取properties文件

```xml
在Spring的配置文件中使用<conext:property-placeholder/>标签加载properties配置文件
	使用location属性加载properties文件
	
比如:
<context:property-placeholder location="classpath:db.properties" />
classpath: 表示类路径下的指定文件




```



<h4 style="color:red;">properties配置文件书写注意,不要加双引号</h4>
错误的书写![__SO`4SZC4F1QU8VZIS@__O.png](https://img2.imgtp.com/2024/04/05/tiKXZ4Km.png)

正确的书写![ED_QXVR_TE_QX04SRLZ2__2.png](https://img2.imgtp.com/2024/04/05/03aDSgmG.png)

## 通过java配置类的方式来管理bean

上面我们都是使用xml配置文件来管理bean,我们也可以用java配置类来管理bean

### 	@Configuration注解

```java
这个注解用来修饰类,表示当前类是一个配置类

比如:
//标识这是一个配置类
@Configuration
public class SpringConfiguration {

}    
```

### @ComponentScan注解

```java
开始包扫描
    属性:basePackages
     basePackages: 主要用来管理扫描路径   
比如:
@Configuration
//扫描org.mmm下的目录及其子目录
@ComponentScan(basePackages = "org.mmm")
public class SpringConfiguration {

}

```

### @Bean标签

```java
类似于xml里面的bean标签
    用于修饰方法,方法的返回值类型就是我们要管理的bean(有点类似于工厂类的感觉)
	属性: name
        默认情况下我们不知道名称,bean的名称就是方法名
比如:
@Configuration
@ComponentScan(basePackages = "org.mmm")
public class SpringConfiguration {

    /**
     * 获取一个数据源的Bean
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource dataSource(){
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai");
            dataSource.setUser("root");
            dataSource.setPassword("root");
            return dataSource;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取一个QueryRunner的Bean
     * @param dataSource
     * @return
     */
    @Bean(name = "runner")
    public QueryRunner queryRunner(@Qualifier("dataSource") DataSource dataSource){
        return new QueryRunner(dataSource);
    }
}

解释: @Qualifier("dataSource") DataSource dataSource
	@Qualifier可以用来修饰形参
    作用:
		@Qualifier会根据我们给的Bean名字,去IOC中寻找名字相同的Bean,找到后把Bean复制给我们的形参

```



### 使用java配置类测试代码的时候需要注意的问题

```java
因为我们没有了配置文件,所以我们测试的时候就不能使用ClassPathXmlApplicationContext()
	因为我们是用的注解的方式,所以可以用AnnotationConfigApplicaitonContext()类
    
比如:
 @Test
    public void test03(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        AccountService service = (AccountService) context.getBean("accountService");
        service.addAccount();
        service.findAll();
    }
```

### @Import标签

```java
因为后面开发我们可能要配置很多配置类
    比如:
		数据库的配置类
		消息中间件的配置类
		文件上传的配置类等
如果搞这么多个配置类,就违反了单一职责的原则,所以我们可以弄一个SpringConfiruation的配置类,然后这个配置类包含了其他的配置类

    比如:
public class SqlConfiguration {

    /**
     * 获取一个数据源的Bean
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource dataSource(){
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai");
            dataSource.setUser("root");
            dataSource.setPassword("root");
            return dataSource;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取一个QueryRunner的Bean
     * @param dataSource
     * @return
     */
    @Bean(name = "runner")
    public QueryRunner queryRunner(@Qualifier("dataSource") DataSource dataSource){
        return new QueryRunner(dataSource);
    }
    
}

@Configuration
@ComponentScan(basePackages = "org.mmm")
@Import(value = {SqlConfiguration.class})
public class SpringConfiguration {
    
}
           

```

### @PropertySource标签

```java
该标签主要是读取properties配置文件,类似于xml中的<context:property-placeholder/>标签

比如:
/**
 * 子配置类 专门描述数据库的配置信息
 */
@PropertySource("classpath:db.properties")
public class SqlConfiguration {

    @Value("${jdbc.driverClass}")
    private String driverClass;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;



    /**
     * 获取一个数据源的Bean
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource dataSource(){
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverClass);
            dataSource.setJdbcUrl(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            return dataSource;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取一个QueryRunner的Bean
     * @param dataSource
     * @return
     */
    @Bean(name = "runner")
    public QueryRunner queryRunner(@Qualifier("dataSource") DataSource dataSource){
        return new QueryRunner(dataSource);
    }

}


```

### 关于@Configuration可不可以不要,要和不要有什么区别的问题

```
@Configuration可以不要
	如果不要的话,一个bean如果要引用另一个bean的时候,另一个bean会创建两次
		因为IOC创建的时候每一个Bean要创建一次,而被引用的时候不会直接从IOC里面取对象,而是重新创建一次
	当然,如果要的话每一个Bean被引用的时候都可以直接从IOC里取
```

## Spirng整合Junit

``` java
1.导入依赖
<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.2.0.RELEASE</version>
 </dependency>

2.配置注解
//这是测试模板类需要的一个启动器
@RunWith(SpringJUnit4ClassRunner.class)

3.在测试模板类中也引入IOC容器
@ContextConfiguration注解
    属性: classes,locations
    locations主要有xml配置文件的时候使用
	classes里面放我们的配置类的class类(没有配置文件的时候使用)
    比如:
@ContextConfiguration(classes = {SpringConfiguration.class})
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})

4.开始测试
    @RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestFrame {

    @Autowired
    private AccountService service;


    @Test
    public void test01(){
        service.findAll();
    }

}

    
```

<h4 style="color:red;">好处就是我们不用每次写test方法的时候都去new容器类</h4>


## Spring中的事务

### 	什么是事务

```
事务是描述一个逻辑的最小单位
	什么意思?
	比如说我们吃东西:需要夹东西,张嘴,然后把菜放进嘴里
	吃东西本来是可以分解为三个动作,但是吃饭看做一个事务,这三个动作就不能分开
	就是夹东西，张嘴，然后把菜放进嘴里必须要执行完
	不能夹东西,然后不张嘴,就把吃东西这个事务完成了
	
事物的特性:ACID原则(背下来,以后吹牛逼有用)
	A:原子性 事务的逻辑操作必须是最小单位  不可分割的
	C:一致性 指的是事务提交前,和事务提交后的数据必须保持一致
	I:隔离性 指的是多个事务之间必须互相独立不影响
	D:持久性 指的是事务提交之后,数据必须要持久化的保存再数据表里
	
事务的隔离级别:
	读未提交: 一个事务读到另一个事务没有提交的数据  --容易造成脏读
	读已提交: Oracle数据库默认的事务隔离级别.  -- 解决了脏读问题,但是出现了不可重复读的现象(但一个人频繁读,而另一个人在存数据。读的那个人会发现读的数据不一样)
	可重复读: mysql默认的事务隔离级别。解决了不可重复读的问题,但是出现了幻读的问题(比如用户a和用户b同时存储数据,id自增长,假如都是要增长到4,但是a先提交,就导致b看到自己的id是5)
	可串行化: 解决了所以的事务问题,但是性能很低，一般也不常用


```





<h4 style="color:red;">如何判断操作是否在同一个事务中?如果操作在同一个事务中,那么他们一定在同一个数据库连接对象中</h4>


### 	ThreadLocal: 将当前线程和数据库连接对象绑定

```java
绑定线程和数据库连接对象可以解决,操作和数据库连接对象不统一的问题

第一步(使用ThreadLocal类将数据库连接对象和当前线程绑定).    
package org.mmm.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

//变成一个Bean的目的是获取dataSource
@Component
public class ConnectionUtils {

    @Autowired
    private DataSource dataSource;

    //创建ThreadLocal的目的是为了绑定资源(这里是数据库连接)和线程
    ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 获取和线程绑定的数据库连接对象
     * @return 和线程绑定的数据库连接对象
     */
    public Connection getConnection(){
        try {
            Connection conn = threadLocal.get();
            if (conn == null){
                conn = dataSource.getConnection();
                threadLocal.set(conn);
            }
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 将当前线程和数据库连接对象解绑
     */
    public void removeConnection(){
        threadLocal.remove();
    }
}
    
第二步(定义一个事务管理的类TransactionManager).
	定以一个事务管理器类
		开启事务的方法
		提交事务的方法
		回滚事务的方法
		释放资源的方法
    package org.mmm.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

//标记被bean是为了获取ConnectionUtils
@Component
public class TransactionManager {

    @Autowired
    ConnectionUtils conn;

    /**
     * 开始事务
     */
    public void beginTransaction(){
        try {
            conn.getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 提交事务
     */
    public void commit(){
        try {
            conn.getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 事务回滚
     */
    public void rollBack(){
        try {
            conn.getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 释放连接
     */
    public void release(){
        try {
            conn.getConnection().close();
            conn.removeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
    
    
    
    
第三步(在业务类中完成下面操作).
	try{
		开启事务
        执行事务代码
        提交事务   
	}catch(Exception e){
        回滚事务
    }finally{
        释放资源
    }
		
package org.mmm.service.impl;

import org.mmm.dao.AccountDao;
import org.mmm.pojo.Account;
import org.mmm.service.AccountService;
import org.mmm.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TransactionManager manager;

    @Override
    public void transfer(String sourceName, String targetName, Double money) {
        try {
            //开启事务
            manager.beginTransaction();
            Account source = accountDao.getAccountByName(sourceName);
            Account target = accountDao.getAccountByName(targetName);
            if (sourceName != null && targetName != null){
                if (source.getMoney() >= money){
                    accountDao.updateMoney(sourceName, source.getMoney() - money);
                    int i = 10 / 0;
                    accountDao.updateMoney(targetName, target.getMoney() + money);
                }
            }
            //提交事务
            manager.commit();
        }catch (Exception e){
            e.printStackTrace();
            //事务回滚
            manager.rollBack();
        }finally {
            //释放事务
            manager.release();
        }
    }
}		
		

```



## 	代理

```java
代理模式允许你创建一个代理对象，该对象可以拦截对目标对象方法的调用，并在调用前后执行特定的逻辑。这样可以在不修改目标对象的情况下，对其方法进行增强、扩展或控制。
    
```

### 	JDK动态代理

```java
注意: JDK动态代理必须是基于接口实现
    
接口:
package org.mmm.proxy;

public interface ProductDao {

    public void product(String name);

}

实现接口的类:
package org.mmm.proxy.impl;

import org.mmm.proxy.ProductDao;

public class Producer implements ProductDao {
    @Override
    public void product(String name) {
        System.out.println(name + "购买了一台电脑.....");
    }
}

代理测试类:
package org.mmm.proxy.impl;

import org.mmm.proxy.ProductDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class consumer {

    public static void main(String[] args) {

        Producer producer = new Producer();

        producer.product("自己");

        /**
         * Producer.class.getClassLoader():类加载器,保证Producer对象创建
         * producer.getClass().getInterfaces(): 该方法返回一个数组，包含了该类所实现的所有接口。
         *
         * new InvocationHandler(): 我们调用代理实现的逻辑在invoke接口里实现
         *
         * public Object invoke(Object proxy, Method method, Object[] args)
         *  proxy:表示当前代理对象
         *  method: 表示被代理对象的方法,这里指的是Producer的方法
         *  args : 表示被代理对象的方法传入的参数
         */
        ProductDao proxy = (ProductDao) Proxy.newProxyInstance(Producer.class.getClassLoader(),
                producer.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy,
                                         Method method,
                                         Object[] args) throws Throwable {
                        if (method.getName().equals("product")) {
                            //第一个参数表示要代理的对象, 第二个参数表示要传入的参数
                            method.invoke(producer, args);
                        }
                        return null;
                    }
                });
        proxy.product("代理");

    }
}


```

### 	chlib代理

```java
基于子类进行实现,并且是懒实现,用到才会创建,JDK动态代理是启动就实现
    
代码:
package org.mmm.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProductObject{
    public static void main(String[] args) {

        Producer producer = new Producer();
        producer.product("自己");

        Producer proxy = (Producer) Enhancer.create(Producer.class, new MethodInterceptor() {
            /**
             *
             * @param o 当前代理对象的应用
             * @param method 代理对象的方法
             * @param objects   代理对象方法对应需要的参数
             * @param methodProxy   指定代理的对象一般不用(暂时我也不知道为什么)
             * @return 返回代理方法的参数
             * @throws Throwable
             */
            @Override
            public Object intercept(Object o, Method method,
                                    Object[] objects,
                                    MethodProxy methodProxy) throws Throwable {
                if ("product".equals(method.getName())){
                    method.invoke(producer, objects);
                }
                return null;
            }
        });

        producer.product("代理");
    }

}

```

## SpringAOP

### 		什么是AOP

```xml
类似于IOC，也是一个容器，只是这个容器里面装的是方法
	我们可以指定一个方法,在这个方法之前或者执行后来运行另一个方法
	我们指定的方法就叫切点,而我们在这个方法基础上运行的其他方法就叫通知
	通知在切面上
	<!--    配置业务类的bean-->
    <bean id="userService" class="org.mmm.service.impl.UserServiceImpl"/>

<!--    管理切面的类-->
    <bean id="myAspect" class="org.mmm.service.aspect.MyAspect"/>


    <aop:config>
<!--
	<aop:pointcut>标签就是定义切点的标签
		id: 表示这个切点的名字
		expression() : 定义方法的执行规则
			* : 表示返回任意参数
			(..) : 表示任意形参

-->
        <aop:pointcut id="p1" expression="execution(* org.mmm.service.impl.UserServiceImpl.deleteUser(..))"/>
        <aop:pointcut id="p2" expression="execution(* org.mmm.service.impl.UserServiceImpl.getUser(..))"/>

        
        <!--
	<aop:aspect> : 表示切面,里面可以放我们要给切点加的方法
		ref: 表示要在哪一个类里面找方法给切点加方法
	<aop:before> : 在切点前加方法
 		method : 在myAspect类里面找哪一个方法加到切点
		poincut-ref : 加在哪个切点上
-->
        <aop:aspect ref="myAspect">
            <aop:before method="checkPrivilege" pointcut-ref="p1"/>
            <aop:after method="printLog" pointcut-ref="p2"/>
        </aop:aspect>

    </aop:config>


```

### 	AOP里面的术语

![AOP术语.png](https://img2.imgtp.com/2024/04/06/yBQmeERa.png)

### AOP的一个案例

```java
//切面类
package org.mmm.service.aspect;

public class MyAspect {

    public void checkPrivilege(){
        System.out.println("执行了权限验证的方法");
    }

}
//目标类
package org.mmm.service.impl;

import org.mmm.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void addUser() {
        System.out.println("增加了一个用户....");
    }

    @Override
    public void deleteUser() {
        System.out.println("删除了一个用户....");
    }

    @Override
    public void updateUser() {
        System.out.println("更新了一个用户....");

    }

    @Override
    public void getUser() {
        System.out.println("查询了一个用户....");

    }
}

//测试类
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mmm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestAspect {

    @Autowired
    private UserService service;

    @Test
    public void test01(){

        service.deleteUser();

    }
}


```

```xml
//xml配置文件
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">


<!--    配置业务类的bean-->
    <bean id="userService" class="org.mmm.service.impl.UserServiceImpl"/>

<!--    管理切面的类-->
    <bean id="myAspect" class="org.mmm.service.aspect.MyAspect"/>


    <aop:config>
        <aop:pointcut id="p1" expression="execution(* org.mmm.service.impl.UserServiceImpl.deleteUser(..))"/>

        <aop:aspect ref="myAspect">
            <aop:before method="checkPrivilege" pointcut-ref="p1"/>
        </aop:aspect>

    </aop:config>


</beans>
```

### 通知方法的类型

```xml
//切点执行前通知
<aop:before method="checkPrivilege" pointcut-ref="p1"/>
//切点执行后通知
<aop:after method="printLog" pointcut-ref="p2"/>
//我们可以自己控制(传参ProceedingJoinPoint joinPoint, joinPoint.process()表示我们的切点方法),前和后都可以
<aop:around method="around" pointcut-ref="p2"/>
//出现异常时指定的方法
<aop:after-throwing/>



```

### 使用注解的方式执行AOP

```java
1.开启包扫描
    <aop:aspectj-autoproxy>/aop: aspectj-autoproxy>
    <context:component-scan base-package="com.xq"></context:component-scan>
2.配置切面类
        @Component
        @Aspect
3.配置增强方法
        @Before(value="execution(* org.mmm.service.impl.UserServiceImpl.deleteUser(..))")
        @AfterReturning(最终事务)
        @Around
        @AfterThrowing
        @After
		@PointCut(修饰切点方法,增强方法修饰的时候value="切点方法名就行")

//下面是切面类
package org.mmm.service.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {

    @Before(value = "execution(* org.mmm.service.impl.UserServiceImpl.deleteUser(..))")
    public void checkPrivilege(){
        System.out.println("执行了权限验证的方法");
    }

    @AfterReturning(value = "execution(* org.mmm.service.impl.UserServiceImpl.getUser())")
    public void printLog(){
        System.out.println("开启了打印日志的方法");
    }

    @Around(value = "execution(* org.mmm.service.impl.UserServiceImpl.getUser(..))")
    public void around(ProceedingJoinPoint joinPoint){

        try {
            System.out.println("1");
            joinPoint.proceed();
            System.out.println(2);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

}



```

## jdbcTemplate

### 	使用方法

```xml
1.导入依赖
<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.11</version>
</dependency>


2.配置数据源(使用配置文件的方法)
<context:component-scan base-package="org.mmm"/>

    <context:property-placeholder location="classpath:db.properties"/>

    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="${jdbc.Driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.passwd}"/>
    </bean>

    <bean id="jdbc" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
    </bean>

3.开始使用

//配置的rowMapper(用于结果集处理)
package org.mmm.pojo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyRowMap<T> implements RowMapper {
    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {

        Integer id = (Integer) resultSet.getObject("id");
        String name = (String) resultSet.getObject("name");
        Double money = (Double) resultSet.getObject("money");
        return new Account(id, name, money);
    }
}




//接口的使用
package org.mmm.dao.impl;

import org.mmm.dao.AccountDao;
import org.mmm.pojo.Account;
import org.mmm.pojo.MyRowMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private JdbcTemplate conn;

    @Override
    public void findAll() {
        String sql = "select * from account";
        List<Account> query = conn.query(sql, new MyRowMap<Account>());
        query.forEach(System.out::println);
    }

    @Override
    public void findAccountById(Integer id) {
        String sql = "select * from account where id = ?";
        List<Account> accountList = conn.query(sql, new MyRowMap<Account>(), id);
        accountList.forEach(System.out::println);
    }

    @Override
    public void addAccount(Account account) {
        String sql = "insert into account(name, money) values(?, ?)";
        int cnt = conn.update(sql, account.getName(), account.getMoney());
        System.out.println("添加了" + cnt + "个用户," + account.getName() );
    }

    @Override
    public void deleteAccountById(Integer id) {
        String sql = "delete from account where id = ?";
        int cnt = conn.update(sql, id);
        System.out.println("删除了" + cnt + "个用户");
    }

    @Override
    public void updateAccount(Account account) {
        String sql = "update account set name = ?, money = ? where id = ?";
        int cnt = conn.update(sql, account.getName(), account.getMoney(), account.getId());
        System.out.println("修改了" + cnt + "条语句");
    }
}
```



## Spring事务控制

### 	Spring事务控制需要的类和xml的Bean配置

```xml
(1)PlatformTransactionManager: 平台事务管理器
   平台事务管理器: 接口, 是Spring用于管理事务的真正的对象
		DataSourceTransactionManager : 底层使用JDBC管理事务
		HibernateTransactionManager : 底层使用Hibernate管理事务
(2)TransactionDefinition : 事务定义信息
	事务定义: 用于定义事务的相关信息, 隔离级别, 超时信息, 传播行为, 是否只读(后面用到印象深一些，现在也不是很会)
	
管理bean
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driverClass}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.passwd}"/>
    </bean>

<!--    事务管理-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

<!--    事务管理模版-->
    <bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
        <property name="transactionManager" ref="transactionManager"/>
    </bean>


```

### 	具体使用(template.execute())

```java
这个匿名对象里面的接口就是我们想要增强的方法
TransactionCallbackWithoutResult() {
    @Override
    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus){

    	//我们要事务控制的代码
    
    }
}



package org.mmm.service.impl;

import org.mmm.dao.AccountDao;
import org.mmm.pojo.Account;
import org.mmm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TransactionTemplate template;
    
    @Override
    public void transfer(String sourceName, String targetName, Double money) {

        template.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //获取扣账人的账户信息
                Account sourceAccount = accountDao.findAccountByName(sourceName);
                //获取入账人的信息
                Account targetAccount = accountDao.findAccountByName(targetName);

                if (sourceAccount != null && targetAccount != null && sourceAccount.getMoney() >= money){
                    //修改扣账人的金额
                    sourceAccount.setMoney(sourceAccount.getMoney() - money);
//                    int i = 10 / 0;
                    //修改入账人的金额
                    targetAccount.setMoney(targetAccount.getMoney() + money);

                    //更新数据库的金额
                    accountDao.updateMoneyByAccount(sourceAccount);
                    accountDao.updateMoneyByAccount(targetAccount);
                }
            }
        });
    }
}
```



### 使用注解的方法把业务代码和事务管理的代码分离

```xml
使用@Transactional注解
	1.该注解既可以放在类上面,也可以放在方法上面
	2.如果放在业务类上面 意味着当前业务类中的所有方法都会被事务控制，并且应用的都是同一种事务隔离界别和事务传播行为
	3.如果放在业务方法上面 意味着可以对指定业务类进行事务控制,并且对不同的业务方法设置不同的事务隔离级别和传播行为
	
	isolation: 控制事务隔离级别
	propagation: 控制事务传播方法



```

















