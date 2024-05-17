# SpringMVC

## 	1.配置环境

### 		1.1 需要的依赖

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.mmm</groupId>
        <artifactId>springMVC</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <artifactId>quickstart</artifactId>
    <packaging>war</packaging>
    <name>untitled Maven Webapp</name>
    <url>http://maven.apache.org</url>

    <properties>
        <spring.version>5.0.2.RELEASE</spring.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8.1</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>


    <!--tomcat插件-->
    <build>
        <finalName>springmvc-project-quickstart</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
                <configuration>
                    <path>/</path>
                    <port>8888</port>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### 1.2 配置tomcat

![tomcat.jpg](https://img2.imgtp.com/2024/05/01/d08z1icw.jpg)

### 1.3 在web.xml里面配置前端控制器

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>

  <!--配置前端控制器-->
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <!--间接继承了httpServlet-->
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

    <!--加载springmvc的核心配置文件-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <!--提前DispatcherServlet前端控制器的加载时机-->
    <load-on-startup>1</load-on-startup>
  </servlet>
  
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <!--表示所有发送过来的请求都要处理-->
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>
```

### 1.4 配置springmvc的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context
          http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--配置包扫描-->
    <context:component-scan base-package="org.mmm"/>

    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/pages/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <mvc:annotation-driven></mvc:annotation-driven>
</beans>

```

### 1.5 宏观解释入门案例

![SpringMVC入门案例解释.png](https://img2.imgtp.com/2024/05/01/28SyNct9.png)

## 2. SpringMVC的执行流程

### 	2.1 SpringMVC执行流程图解

![SpringMVC执行流程.png](https://img2.imgtp.com/2024/05/01/wqbgfMTb.png)

## 3. RequestMapping标签注解的使用

### 	3.1 RequestMapping标签不但可以用来修饰方法,还可以用来修饰类

<h4 style="color:gray">好处就是符合企业开发的规范的前提下实现风格统一</h4>
![RequestMapping注解.png](https://img2.imgtp.com/2024/05/07/qvzYsMRa.png)

### 3.2 RequestMapping标签的属性

```java
    /**
     * path/value:描述的是请求资源的url路径
     * method:描述的是指定以什么请求方式来进行资源的访问
     * params:表示必须传递的参数
     * @return
     */
    @RequestMapping(value = "hello", method = RequestMethod.GET, params = {"username", "age"})
    public String hello(){
        return "success";
    }
```

### 3.3 参数绑定

<h4 style="color:gray">对于普通的类型,可以直接通过方法的形参的方式传递,但是形参的名字和传递的参数名必须一致</h4>
```java
//其中username和age是传递的参数    

@RequestMapping(value = "hello", method = RequestMethod.GET, params = {"username", "age"})
    public String hello(String username, Integer age){
        System.out.println(username);
        System.out.println(age);
        return "success";
    }

```

<h4 style="color:gray">pojo类型的参数必须使用表单来传递</h4>
```java
//表单输入
<form action="${pageContext.request.contextPath}/demo1/getAccount" method="post">
    用户编号:<input type="text" name="id"><br>
    用户名称:<input type="text" name="username"><br>
    用户金额:<input type="text" name="money"><br>
    <input type="submit" value="提交">
</form>


//pojo类

public class Account {

    private Integer id;

    private String username;

    private Double money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", money=" + money +
                '}';
    }
}

//处理器
  @RequestMapping("getAccount")
    public String getAccount(Account account){
        System.out.println(account);
        return "success";
    }

```

### 3.4 解决中文乱码的问题

<h4 style="color:gray">解决post请求中文乱码的问题</h4>
```xml
在使用Servlet的时候,我们都是用Request.setCharacterEncoding("utf-8")来解决表单提交数据乱码的问题
    
在Springmvc中,使用的是CharacterEncodingFiler来解决中文乱码问题

  <!--配置编码过滤器-->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <!--设置编码格式-->
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <!--启用编码过滤器-->
    <init-param>
      <param-name>forceEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <!--拦截过滤所有的请求-->
    <url-pattern>/*</url-pattern>
  </filter-mapping>

```

<h4 style="color:gray">解决get请求中文乱码的问题</h4>
```xml
<!--如果使用的是外部的tomcat-->
<Connector port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectport="8443" userBodyEncodingForURI="true"/>

<!--如果使用的是tomcat插件的话-->
<!--tomcat插件-->
    <build>
        <finalName>springmvc-project-quickstart</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
                <configuration>
                    <path>/</path>
                    <port>8888</port>
                    <!--加上下面这一句就可以解决get传参乱码问题了-->
                    <uriEncoding>utf-8</uriEncoding>
                </configuration>
            </plugin>
        </plugins>
    </build>


```

###3.5 集合类型的参数传递

```java
//场景是一个用户有多个账户,账户用集合来存储

//前端的表单
<form action="${pageContext.request.contextPath}/demo1/getUserList" method="post">
    用户名: <input type="text" name="username"><br>
    密码: <input type="password" name="passwd"><br>

    账户1:
    <input type="text" name="accountList[0].username"><br>
    <input type="text" name="accountList[0].money"><br>

    账户2:
    <input type="text" name="accountList[1].username"><br>
    <input type="text" name="accountList[1].money"><br>

    账户3:
    <input type="text" name="accountMap['one'].username"><br>
    <input type="text" name="accountMap[one].money"><br>

    账户4:
    <input type="text" name="accountMap['two'].username"><br>
    <input type="text" name="accountMap['two'].money"><br>

    <input type="submit" name="submit" value="提交">
</form>
        
//处理器处理请求
       @RequestMapping(value = "getUserList", method = RequestMethod.POST)
    public String getAccountList(User user){
        System.out.println(user);
        return "success";
    }     

//User实体类
package org.mmm.pojo;

import java.util.List;
import java.util.Map;

public class User {

    private String username;

    private String passwd;

    private List<Account> accountList;

    private Map<String, Account> accountMap;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public Map<String, Account> getAccountMap() {
        return accountMap;
    }

    public void setAccountMap(Map<String, Account> accountMap) {
        this.accountMap = accountMap;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", passwd='" + passwd + '\'' +
                ", accountList=" + accountList +
                ", accountMap=" + accountMap +
                '}';
    }
}


```

### 3.6 集合传递日期格式的对象

```java
//方法一.使用@DateTimeFormat来解决    


@RequestMapping(value = "getDate", method = RequestMethod.GET)
    public String getDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        System.out.println(date);
        return "success";
    }

//方法二.我们自己去实现Convert(SpringMVC用来强制转换的一个)接口

//实现接口的类
package org.mmm.convert;


import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatConvert implements Converter<String, Date> {

    /**
     * 进行数据类型转换的方法
     * @param source 前端传入的参数
     * @return 返回参数的日期类型
     */
    @Override
    public Date convert(String source) {
        try {
            if (StringUtils.isEmpty(source)){
                throw new NullPointerException("前端传入参数不能为空");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("输入的日期格式有误");
        }
    }
}

//在SpringMVC的配置文件里面配置ConversionServiceFactoryBean
    <!--配置数据类型转换器-->
    <bean id="convert" class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <array>
                <bean class="org.mmm.convert.DateFormatConvert"/>
            </array>
        </property>
    </bean>

//处理器映射器中药加一个conversion-service属性                    
<!--开启处理器映射器 处理器适配器的自动配置-->
    <!--conversion-service="convert"这里需要的是我们ConversionServiceFactoryBean的id-->
    <mvc:annotation-driven conversion-service="convert"></mvc:annotation-driven>

```

## *Springmvc的常用注解

### 		*1.5.1 RequestParam注解

```java
@RequestParam注解解决了传参参数名和形参名不同的问题
    value属性的值和前端传入的参数一致
    required属性表示该属性是不是强制要求,也就是能不能为null
    defaultValue属性表示如果该参数没有传参,那么久默认为指定的值

@RequestMapping(value = "testRequestParam", method = RequestMethod.GET)
    public String testRequestParam(@RequestParam(value = "username", required = true, defaultValue = "李四") String name,
                                   @RequestParam(value = "password", required = true, defaultValue = "123456") String password){
        System.out.println(name);
        System.out.println(password);
        return "success";
    }
```



### 		*1.5.2 Requestbody注解

### 		1.5.3 RequestHeader注解

### 		1.5.4 CookieValue注解

### 		1.5.5 SessionAttributes注解

### 		1.5.6 ModelAttributes注解

### 		1.5.7 MaxtrixVariable注解

### 		1.5.8 mvc:view-controller注解

### 		*1.5.9 PathVariable注解





