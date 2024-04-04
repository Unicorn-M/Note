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















