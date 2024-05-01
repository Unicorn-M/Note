



# mybatis

## 	什么是mybatis

```说明
1.mybatis是一个作用于JavaEE三层开发中持久层的一个框架
2.什么是框架:框架主要是提供了一套解决办法的思想(例如Spring中的IOC和AOP),我们用别人写好的一组组组件来实现功能,简单来说就是一个半成品,帮助我们更注重软件的设计,而非功能的实现
3.mybatis封装了jdbc框架,让我们只用去关注sql语句本身,而不用去关心数据库的驱动,statement构造,创建连接等
```

## 	怎么使用mybatis来执行语句

```说明
这是只是从概念上来理解,后面用的时候会加上代码

mybatis通过xml或者注解的方式,将各种要执行statement配置起来。并通过java对象和statement中的sql的动态参数映射成最终的sql语句,然后通过mybatis框架执行了
```

##  mybatis的核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--配置mybatis的环境-->
    <environments default="mysql">
        <!--id和default的值必须要保持一致-->
        <environment id="mysql">
            <!--配置事务类型 JDBC-->
            <transactionManager type="JDBC"/>
            <!--配置数据源, POOLED(使用数据源) 和 UNPOOLED(不使用数据源)-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatis?serverTimezone=Asia/Shanghai"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>

    <!--在核心配置文件里导入接口映射文件-->
    <!--mapper是映射器-->
    <mappers>
        <mapper resource="com/mmm/dao/UserDao.xml"/>
    </mappers>
</configuration>
```

## 	mybatis需要的依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>mybatis-demo01</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>dom4j</groupId>
            <artifactId>dom4j</artifactId>
            <version>1.6.1</version>
        </dependency>

        <dependency>
            <groupId>jaxen</groupId>
            <artifactId>jaxen</artifactId>
            <version>1.1.6</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.11</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.12</version>
        </dependency>

    </dependencies>

</project>

```

## log4j.properties配置文件

```properties
# Set root category priority to INFO and its only appender to CONSOLE.
#log4j.rootCategory=INFO, CONSOLE            debug   info   warn error fatal
log4j.rootCategory=debug, CONSOLE, LOGFILE

# Set the enterprise logger category to FATAL and its only appender to CONSOLE.
log4j.logger.org.apache.axis.enterprise=FATAL, CONSOLE

# CONSOLE is set to be a ConsoleAppender using a PatternLayout.
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=%d{ISO8601} %-6r [%15.15t] %-5p %30.30c %x - %m\n

# LOGFILE is set to be a File appender using a PatternLayout.
log4j.appender.LOGFILE=org.apache.log4j.FileAppender
log4j.appender.LOGFILE.File=d:\axis.log
log4j.appender.LOGFILE.Append=true
log4j.appender.LOGFILE.layout=org.apache.log4j.PatternLayout
log4j.appender.LOGFILE.layout.ConversionPattern=%d{ISO8601} %-6r [%15.15t] %-5p %30.30c %x - %m\n
```

## mabatis的映射器(映射的路径要和接口一致)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--namespace: 命名空间,要求值要唯一,一般命名为全限定名即可-->
<mapper namespace="org.mmm.dao.UserDao">
    <!--描述接口的标签,如果是查询就使用select,增加就使用insert,删除就使用delete,修改就使用update标签
        ,标签内部就写我们要执行的sql语句-->
    <!--id必须是我们要实现的接口名-->
    <!--parameterType是传入参数参数类型,我们这里没有参数就不写-->
    <!--resultType写我们返回值类型,如果是List 或者 set集合就使用泛型的类型-->
    <select id="findAll" resultType="org.mmm.pojo.User">
        select * from user;
    </select>

</mapper>
```

## mybatis是怎么执行的

```xml
1.继续dom4j技术,解析xml核心配置文件(驱动,url,username,password,sql语句,实现的接口)

2.产生代理对象,代理对象通过selectList方法对接口进行增强

3.selectList方法执行需要两个参数
参数1: 连接数据库的链接信息 Connection
参数2: sql语句本身 select * from user
	  封装sql结果集的类全限定名 com.mmm.pojo.User


```

## mybatis之单表操作

### 	简单查询

 ``` xml
1.导入依赖
2.创建实体类(对应数据库的表)
3.创建核心配置文件
4.创建映射器(路径要和实现的接口相同)
5.核心配置文件引入映射器
6.在映射里写sql语句

映射器:
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.mmm.dao.UserDao">
    <select id="findAll" resultType="org.mmm.pojo.User">
        select * from user
    </select>

    <select id="findUserById" parameterType="int" resultType="org.mmm.pojo.User">
        #{id}在mysql的底层其实就是?
        其中的id不能乱写,保证和实体字段名称一样就行
        select * from user where id = #{id}
    </select>
</mapper>


 ```

### 将mybatis加载配置文件用方法封装

``` java
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mmm.dao.UserDao;
import org.mmm.pojo.User;

import java.io.IOException;
import java.io.InputStream;

public class TestMybatis {
    
    private SqlSession session;

    public <T> T loadConfigFile(Class<T> clazz){
        //从配置文件中读入信息
        InputStream in = null;
        try {
            in = Resources.getResourceAsStream("sqlMapConfig.xml");
            //构建SqlSessionFactoryBuilder对象
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            //构建SqlSessionFactory对象
            SqlSessionFactory factory = builder.build(in);
            //构建SqlSession对象
            session = factory.openSession();
            //创建代理对象
            return session.getMapper(clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @after(@after标签会在@Test标签执行完执行)
    public void close(){
        session.close();
    }
    

    @Test
    public void test01(){

        UserDao mapper = loadConfigFile(UserDao.class);

        User user = mapper.findUserById(1);
            
        System.out.println(user);
    }
}
```

### 修改操作

```java
    @Test
    public void test01(){

        UserDao mapper = loadConfigFile(UserDao.class);

        User user = new User();
        user.setUsername("贝吉塔");
        user.setSex("男");
        user.setBirthday(new Date());
        user.setAddress("地球online");
        mapper.addUser(user);
        //只要是修改了表的数据,就需要手动提交事务
        session.commit();   
    }
```

#### 	1.获取自增长主键的标签(selectKey和useGeneratedKeys属性)

```xml

<!--
	resultType : 表示主键的类型
	keyColumn : 表示数据表中字段的名称
	keyProperty : 表示实体类中主键的属性名
-->

<insert id="addUser" parameterType="org.mmm.pojo.User">
        <selectKey resultType="int" keyColumn="id" keyProperty="id">
            select last_insert_id()
        </selectKey>
        insert into user(username, birthday, sex, address) values(#{username}, #{birthday}, #{sex}, #{address})
    </insert>


<!--
	把useGeneratedKeys设为true
	keyColumn : 表示数据表中字段的名称
	keyProperty : 表示实体类中主键的属性名
-->
    <insert id="addUser" parameterType="org.mmm.pojo.User" useGeneratedKeys="true" keyColumn="id" keyProperty="id">
        insert into user(username, birthday, sex, address) values(#{username}, #{birthday}, #{sex}, #{address})
    </insert>
```

### mybatis从代码分析存在sql注入的地方

```xml
<!--通过名字模糊查询-->
<select id="findUserByName" parameterType="string" resultType="org.mmm.pojo.User">
        select * from user where username like '%${value}%'
</select>

当我们使用${value}的时候,mybatis底层是不会走预编译的,value就变得不可控很危险


```

下面是sql注入的payload：![sql注入模糊查询.png](https://img2.imgtp.com/2024/04/13/JqxxSaq0.png)

## mybatis解决方法多个传参的问题

```xml
使用${arg0,1,2....}或者${param0,1,2......}

<select id="findUserBySexAndAddress" resultType="org.mmm.pojo.User">
        select * from user where sex = ${arg0} and address = ${arg1}
    </select>

使用map来进行传参
	<select id="findUserBySexAndAddress" resultType="org.mmm.pojo.User">
        //#{}里面的名字是map的key值
        select * from user where sex = #{sex} and address = #{address}
    </select>
测试代码:
@Test
    public void argSelectTest(){
        UserDao userDao = loadConfigFile(UserDao.class);
        Map<String, Object> map = new HashMap<>();
        map.put("sex", "男");
        map.put("address", "上海");
        List<User> userList = userDao.findUserBySexAndAddress(map);
        userList.forEach(System.out::println);
    }




```

## 关于#{}占位符的一些问题

```java
1.如果实体类的属性是String或者其他基本数据类型,占位符的名称可以是任意的
2.如果实体类的属性是我们的自定义类型，那么占位符的名称必须和我们的属性名一样

parameterType属性如果是String或者是基本数据类型的话，我们可以写别名(别名在TypeAliasRegistry类中)

```

## 实现集合传参的问题(使用foreach标签)

```xml
<select id="findUserByQueryVo" resultType="org.mmm.pojo.User">
        select * from user where id in
        <!--collection的名字必须和我们传入的类中的集合属性名相同-->
    	<!--id是集合每个元素的代词-->
    	<!--separator表示已,分隔每个集合中的元素-->
    	<!--open和close分别表示以什么开始和以什么结尾-->
    <foreach collection="ids" item="id" separator="," open="(" close=")">
            #{id}    
    </foreach>
</select>

测试代码:
    @Test
    public void QueryVoTest(){
        UserDao userDao = loadConfigFile(UserDao.class);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(4);
        list.add(6);
        QueryVo queryVo = new QueryVo();
        queryVo.setIds(list);
        List<User> userList = userDao.findUserByQueryVo(queryVo);
        userList.forEach(System.out::println);
    }

```

## 解决数据表的字段名和实体类的属性名不一致,导致数据为null的问题

```xml
1.直接在写sql语句的时候给数据表的字段名取别名(别名和实体类的属性名一致)
例如:
	<select id="findAll" resultType="org.mmm.pojo.User">
		<!--select * from user-->
        select id as userId, username as username, birthday as userBirthday,
        sex as userSex, address as userAddress from user
	</select>
但是有弊端:如果属性名很多,并且很多语句都要查询,就要写很多语句,就要取很多别名            

2.使用resultType标签来解决
例如:
    <resultMap id="userMap" type="org.mmm.pojo.User">
        <!--id标签是用来做主键字段的映射的-->
        <id property="userId" column="id"/>
        <result property="userName" column="username"/>
        <result property="userBirthday" column="birthday"/>
        <result property="userSex" column="sex"/>
        <result property="userAddress" column="address"/>
    </resultMap>

	<!--返回值属性就不用resultType了,而是resultMap-->
    <select id="findAll" resultMap="userMap">
        select * from user
    </select>

	<!--并且resultType里面的属性可以复用-->
例如:
	<!--本来返回值应该是User-->
	<select id="findUserByName" resultMap="userMap" parameterType="String">
        select * from user where username = #{username}
    </select>

```

## mybatis中传统DAO开发实现(了解)

```java
public class UserDaoImpl implements UserDao{
    
    private SqlsessionFactory sqlSessionFactory;
    
    private UserDAoImpl(SqlSessionFactory sqlSesssionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
    
    public List<User> findAll(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //通过全限定名的方式找到映射器
        List<User> UserList = sqlSession.selectList("org.mmm.dao.UserDao.findAll");
        sqlSession.close();
        return UserList;
    }
}
```

## sqlMapConfig.xml标签讲解

```xml
1.读取properties文件使用<properties resource=""></properties>
比如:
<properties resource="db.properties"/>
    <!--配置环境mybatis的事务管理,还有数据源-->
    <environments default="mybatis">
        <environment id="mybatis">
            <transactionManager type="JDBC"></transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbcDriver}"/>
                <property name="url" value="${jdbcUrl}"/>
                <property name="username" value="${jdbcUserName}"/>
                <property name="password" value="${jdbcPassword}"/>
            </dataSource>
        </environment>
    </environments>

2.用<typeAliases></typeAliases>标签给我们自定义的类型加别名(注意这个标签必须写在properties和settings标签后面)
比如:
	<typeAliases>
        <!--这里一个typeAlias对象一个类的别名-->
        <typeAlias type="org.mmm.pojo.User" alias="user"></typeAlias>
        <!--这里表示包下的所以类的别名默认就是类名-->
        <package name="org.mmm.pojo"/>
    </typeAliases>

3.使用Mappers标签来导入映射文件
第一种(只能引入一个xml文件):
	<!--引入映射文件-->
    <mappers>
        <mapper resource="org/mmm/dao/UserDao.xml"/>
    </mappers>
第二种(引入包下所有的xml文件,此方法要求mapper接口名称和mapper映射文件名称相同,且放在同一个目录)
	<mappers>
        <package name="org.mmm.dao"/>
    </mappers>
第三张(使用mapper接口类路径。如果我们使用注解开发的时候，就需要这个路径)
	<mappers>
        <mapper name="org.mmm.dao"/>
    </mappers>
```

## mybatis中的连接池

```java
mybatis中的数据源DataSource分为以下几类:
//控制服务器连接的对象,必须在web项目中才可以使用
JndiDataSourceFactory
//使用连接池方式连接数据库
PooledDataSourceFactory
//每次连接数据库创建新的连接对象
UnpooledDataSourceFactory    
```

<h4 style="color:gray;">UnpooledDataSourceFactory在做什么?</h4>
```java
1.使用反射的机制加载数据库驱动
2.获取数据库连接对象
```

<h4 style="color:gray;">PooledDataSourceFactory在做什么?</h4>
```java
1.判断连接池是否还有空闲连接
    如果有空闲的连接就直接从集合中取出连接

    如果没有空闲的链接就判断活动连接池的数量是否小于最大活动的数量
   		如果小于,就在创建一个链接出来使用
		
    	如果大于,就把最老的连接对象(空闲连接池里面最先创建的对象)拿出来使用
```

## mybatis的事务管理

```java
1.mybatis开启事务默认是需要手动提交事务的

    //如果要自动提交就把openSession传入的参数设置为true
session = factory.openSession(true);
    
private SqlSession session;

    public <T> T loadConfigFile(Class<T> clazz){
        //从配置文件中读入信息
        InputStream in = null;
        try {
            in = Resources.getResourceAsStream("sqlMapConfig.xml");
            //构建SqlSessionFactoryBuilder对象
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            //构建SqlSessionFactory对象
            SqlSessionFactory factory = builder.build(in);
            //构建SqlSession对象
            session = factory.openSession(true);
            //创建代理对象
            return session.getMapper(clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }




```

##mybatis中标签动态查询

<h4 style="color:gray;">为什么要使用动态查询什么?</h4>
```xml
因为我们sql语句的查询条件可能不是固定的
	比如:
	select * from user where .....
	where后面的条件是不固定的
```

<h4 style="color:gray;">怎么解决?</h4>
```xml
使用if标签
比如:(这里传入的参数一般是映射数据表的实体)
	<select id="findUserByCondition" parameterType="user" resultType="user">
        select * from user where 1=1
        <if test="username != null and username != ''">
            and username = #{username}
        </if>
        <if test="id != null and id != ''">
            and id = #{id}
        </if>
        <if test="birthday != null and birthday != ''">
            and birthday = #{birthday}
        </if>
        <if test="sex != null and sex != ''">
            and sex = #{sex}
        </if>
        <if test="address != null and address != ''">
            and address = #{address}
        </if>
    </select>
```

<h4 style="color:gray;">使用where标签,去掉1=1</h4>
```xml
我们上面因为不知道哪个是第一个条件,所以不知道哪个不加and,为了解决这个问题,我们使用 1=1来解决
而where标签就是自动去除第一个满足条件的and

<select id="findUserByCondition" parameterType="user" resultType="user">
        select * from user
        <where>
            <if test="username != null and username != ''">
                and username = #{username}
            </if>
            <if test="id != null and id != ''">
                and id = #{id}
            </if>
            <if test="birthday != null and birthday != ''">
                and birthday = #{birthday}
            </if>
            <if test="sex != null and sex != ''">
                and sex = #{sex}
            </if>
            <if test="address != null and address != ''">
                and address = #{address}
            </if>
        </where>
    </select>
```

<h4 style="color:gray;">使用set标签</h4>
```xml
	注意每个语句后面的,不能少

    <update id="UpdateUserByCondition" parameterType="user">
        update user
        <set>
            <if test="username != null and username != ''">
                 username = #{username},
            </if>
            <if test="birthday != null and birthday != ''">
                 birthday = #{birthday},
            </if>
            <if test="sex != null and sex != ''">
                 sex = #{sex},
            </if>
            <if test="address != null and address != ''">
                address = #{address},
            </if>
        </set>
        where id = #{id}
    </update>
```

<h4 style="color:gray;">sql标签,trim标签,include标签</h4>
```xml
<!--sql标签主要用来定义sql片段-->
<sql></sql>

<!--trim标签主要用来过滤-->
<trim></trim>

<!--include标签主要用来引用sql片段-->
<inlcude></inlcude>

案例:
<!--id就是找到sql片段的标记,只要取名唯一即可-->
    <sql id="key">
        <!--suffixOverrides=","表示取出末尾的,-->
        <trim suffixOverrides=",">
            <if test="username != null and username != ''">
                username,
            </if>
            <if test="birthday != null and birthday != ''">
                birthday,
            </if>
            <if test="sex != null and sex != ''">
                sex,
            </if>
            <if test="address != null and address != ''">
                address,
            </if>
        </trim>
    </sql>

    <sql id="values">
        <trim suffixOverrides=",">
            <if test="username != null and username != ''">
                #{username},
            </if>
            <if test="birthday != null and birthday != ''">
                #{birthday},
            </if>
            <if test="sex != null and sex != ''">
                #{sex},
            </if>
            <if test="address != null and address != ''">
                #{address},
            </if>
        </trim>
    </sql>

    <insert id="InsertUserSelective" parameterType="user">
        insert into user(<include refid="key"/>) values(<include refid="values"/>)
    </insert>

```

<h4 style="color:gray;">choose标签,when标签,otherwise标签</h4>
```xml
<select id="findUserByCondition1" parameterType="user" resultType="user">
        select * from user
        <where>
            <choose>
                <when test="username != null and username != ''">
                    username = #{username}
                </when>
                <when test="birthday != null and birthday != ''">
                    birthday = #{birthday}
                </when>
                <when test="sex != null and sex != ''">
                    sex = #{sex}
                </when>
                <when test="address != null and address != ''">
                    username = #{address}
                </when>
                <otherwise>
                    id = #{id}
                </otherwise>
            </choose>
        </where>
    </select>
```

<h4 style="color:gray;">foreach标签</h4>
```xml
	<delete id="deleteUserInIds">
        delete from user1 where id in (
            <!--如果传的是数组,则用map集合来传递的参数,集合的key是array,
            而如果传的是集合本身,那参数名就是集合名本身-->
            <foreach collection="array" item="id" separator=",">
                #{id}
            </foreach>
        )
    </delete>
```

## mybatis的批量处理

```xml
开启mybatis批处理的两种方式

第一种方式:在mybatis核心配置文件上开启
<settings>
	<setting name="defaultExecutorType" value="BATCH"/>
</settings>

第二种方式:在创建SqlSession对象的时候,指定开启批处理
sqlSession = sessionFactory.openSession(ExecutoryType.BATCH, false);


```

## mybatis的多表查询

<h4 style="color:gray;">一对一时处理方式</h4>
```xml
主体是account,一个account只对应一个user，所以是一对一的关系

方法一:

一对一时,我们可以在主体实体中加一个附属实体的属性,然后使用<association></association>标签来取我们的属性(我们sql语句中没有查询的字段,在查询结果集展示的时候,没查询的值表示为null。下面的例子中,除了username和address,User中的其他字段结果都是null)

<mapper namespace="org.mmm.dao.UserDao">
    <resultMap id="accountMap" type="org.mmm.pojo.Account">
        <id column="ID" property="ID"/>
        <result column="UID" property="UID"/>
        <result column="MONEY" property="MONEY"/>

        <!--1对1进行关联表查询的时候对字段进行映射-->
        <association property="user" javaType="org.mmm.pojo.User">
            <id column="id" property="id"/>
            <result property="username" column="username"/>
            <result property="birthday" column="birthday"/>
            <result property="sex" column="sex"/>
            <result property="address" column="address"/>
        </association>
    </resultMap>
    
    <select id="findAll" resultMap="accountMap">
        SELECT account.*,`user`.username, `user`.address FROM `user`,account WHERE `user`.id = account.UID
    </select>
</mapper>

方法二:

使用继承的方式,我们新建一个类,继承要查询主体类(这个例子是Account),然后在子类中加入我们要查询附加类(这个例子中是User)的属性

public class AccountUser extends  Account{

    private String username;

    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AccountUser{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                "} " + super.toString();
    }
}
```

<h4 style="color:gray;">一对多的查询</h4>
```xml
我们在主体实体里面加入一个list的集合来映射一对多的关系

<mapper namespace="org.mmm.dao.UserDao">
    <resultMap id="userDao" type="org.mmm.pojo.User">
        <id property="id" column="id"/>
        <result column="username" property="username"/>
        <result column="birthday" property="birthday"/>
        <result column="sex" property="sex"/>
        <result column="address" property="address"/>
        <collection property="list" ofType="org.mmm.pojo.Account">
            <id property="ID" column="ID"/>
            <result column="UID" property="UID"/>
            <result column="MONEY" property="MONEY"/>
        </collection>
    </resultMap>


    <select id="findAll" resultMap="userDao">
        SELECT account.*,`user`.username, `user`.address FROM `user`,account WHERE `user`.id = account.UID
    </select>
    
</mapper>

<!--主体类是User-->


public class User {
    private Integer id;

    private String username;

    private Date birthday;

    private String sex;

    private String address;
	<!--一个用户对应多个账户-->
    private List<Account> list;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Account> getList() {
        return list;
    }

    public void setList(List<Account> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", list=" + list +
                '}';
    }
}

```

## mybatis延迟加载

什么是延迟加载:就只先只执行查询主体,不执行关联查询

<h4 style="color:gray;">association懒加载配置</h4>
```xml
1.在mybatis的核心配置文件里面配置(全局都是延迟加载加载)
核心配置文件:
	<settings>
        <setting name="lazyLoadingEnabled" value="true"/>
        <!--mybatis3.4.1版本后可以不写,默认为false-->
        <setting name="aggressiveLazyLoading" value="false"/>
    </settings>

主体映射器(mapper),这里是通过账户查用户为例:
	<resultMap id="accountMap" type="org.mmm.pojo.Account">
        <id column="ID" property="ID"/>
        <result column="UID" property="UID"/>
        <result column="MONEY" property="MONEY"/>

        <!--1对1进行关联表查询的时候对字段进行映射-->
        <!--column表示关联查询需要的参数-->
        <!--select表示要执行关联查询的接口，填接口的全限定名-->
        <association property="user" column="UID" 	select="org.mmm.dao.UserDao.findUserById"/>
    </resultMap>

    <select id="findAll" resultMap="accountMap">
        select * from account
    </select>

关联查询的映射器:
	<select id="findUserById" parameterType="int" resultType="user">
        select * from user where id = #{id}
    </select>

	如果不使用延迟查询,就是先执行select * from account，再执行select * from user where id = #{id}

2.在主映射器里面配置
	<!--关联的接口配置一个fetchType属性为lazy-->
	<association property="user" column="UID" select="org.mmm.dao.UserDao.findUserById" fetchType="lazy"/>

```

<h4 style="color:gray;">collection懒加载配置</h4>
```xml
1.配置文件配置(一样的)
	<settings>
        <setting name="lazyLoadingEnabled" value="true"/>
        <!--mybatis3.4.1版本后可以不写,默认为false-->
        <setting name="aggressiveLazyLoading" value="false"/>
    </settings>

user的映射器(主体映射器)
<mapper namespace="org.mmm.dao.UserDao">

    <resultMap id="userDao" type="org.mmm.pojo.User">
        <id column="id" property="id"/>
        <result property="username" column="username"/>
        <result property="birthday" column="birthday"/>
        <result property="sex" column="sex"/>
        <result property="address" column="address"/>
        <collection property="list" column="id" 		select="org.mmm.dao.AccountDao.findAccountByUid"/>
    </resultMap>


    <select id="findAll" resultMap="userDao" resultType="user">
        select * from user;
    </select>
</mapper>

account的映射器(附属映射器)
<mapper namespace="org.mmm.dao.AccountDao">

    <select id="findAccountByUid" resultType="account">
        select * from account;
    </select>
</mapper>

2.在collection标签里面配置fetch属性

<collection property="list" column="id" select="org.mmm.dao.AccountDao.findAccountByUid" fetchType="lazy"/>


```

## mybatis的缓存

```txt
哪些数据适合缓存:
	不应该变化的数据
	反之,经常变化的数据就不适合缓存	
```

<h4 style="color:gray;">一级缓存的分析</h4>
```java
一级缓存存在于SqlSession创建代理对象时
    一级缓存就是,查询的时候,如果缓存里面有就直接从缓存里面取(只执行一次sql)
	
如果SqlSession在中间被close过,那么一级缓存就会被清空,那么就会重新取数据库查

当我们对数据库里面的数据进行修改时,mysql发现数据库的数据和缓存中的数据库不一致,然后就会清空缓存,因为让我们对数据库执行了修改操作的时候,一级缓存会被清空(所以SqlSession不会缓存同步)

```

## *mybatis的注解开发

<h4 style="color:gray;">mybatis的常用注解说明</h4>
```java
@Insert:实现新增
@Update:实现更新    
@Delete:实现删除
@Select:实现查询
@Result:实现结果集封装    
@Results:可以与@Result一起使用,封装多个结果集
@ResultMap:实现引用@Results定义的封装
@One:实现一对一结果集封装
@Many:实现一对多结果集封装    
@SelectProvider:实现动态SQL映射    
@CacheNamespace:实现注解二级缓存的使用    
```

### 	mybatis注解开发CRUD简单使用

```java
@Select标签的使用:
	直接在括号里面写sql语句

    @Select("select * from user")
    List<User> findAll();
	
	@Select("select * from user where id = #{id}")
    User findUserById(Integer id);


@Update标签的使用
    @Update("update user set username = #{username} where id = #{id}")
    void updateUser(User user);

@Insert标签的使用
@SelectKey标签的使用  
    
    @SelectKey属性的解释:
		keyColumn:主键对应的实体字段名称
    	keyProperty:实体对应的属性名
        resultType: 主键的数据类型
        before: 生成主键的时机 false 在新增之后, 生成主键
        statement: 查询主键的sql
    @Insert("insert into user(username,birthday,sex,address) values(#{username}," +
            "#{birthday},#{sex},#{address})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = Integer.class,
            before = false, statement = {"select last_insert_id()"})
    void addUser(User user);
```

### 	使用注解的方式解决数据表的字段和实体的属性名不一致的问题

```java
使用@Results和@Result标签来解决
	
	@Results(id = "userMap", value = {
           //ID表示是不是主键,默认为false,column是数据表里的名称,property实体类的属性名
        	@Result(id = true, column = "id", property = "UserId"),
            @Result(column = "username", property = "username"),
            @Result(column = "birthday", property = "userBirthday"),
            @Result(column = "sex", property = "UserSex"),
            @Result(column = "address", property = "address"),
    })
    @Select("select * from user")
    List<User> findAll();
	
	//ResultMap引用的是Results的ID
    @ResultMap("userMap")
    @Select("select * from user where id = #{id}")
    User findUserById(Integer id);
```

### 	使用注解解决一对一关联表查询的问题

```java
主体(accountDao)查询的注解:
    
@Results(id = "userMap" , value = {
            @Result(id = true, column = "id", property = "ID"),
            @Result(column = "UID", property = "UID"),
            @Result(column = "MONEY", property = "MONEY"),
    		//UID是从account表查出的数据,当做参数传入findUserById方法
    		//fetchType = FetchType.LAZY表示懒查询,去掉的话就不执行懒查询
            @Result(column = "UID", property = "user", one = @One(fetchType = FetchType.LAZY,select = "org.mmm.dao.UserDao.findUserById")),
    })
    @Select("select * from account")
    List<Account> findAll();

附加(userDao)查询的注解

    @Results(id = "userMap", value = {
            @Result(id = true, column = "id", property = "UserId"),
            @Result(column = "username", property = "username"),
            @Result(column = "birthday", property = "userBirthday"),
            @Result(column = "sex", property = "UserSex"),
            @Result(column = "address", property = "address"),
    })
    @Select("select * from user")
    List<User> findAll();

    @ResultMap("userMap")
    @Select("select * from user where id = #{id}")
    User findUserById(Integer id);    

测试类:
@Test
    public void test02(){
        AccountDao accountDao = session.getMapper(AccountDao.class);
        accountDao.findAll();
    }

```

### 	使用mybatis注解解决一对多的问题

```java

和一对多不同的是,one属性变成了many属性,@one标签变成了@many

@Results(id = "userMap", value = {
            @Result(id = true, column = "id", property = "UserId"),
            @Result(column = "username", property = "username"),
            @Result(column = "birthday", property = "userBirthday"),
            @Result(column = "sex", property = "UserSex"),
            @Result(column = "address", property = "address"),
            @Result(column = "id", property = "list",
                    many = @Many(select = "org.mmm.dao.AccountDao.findAccountByUid"))
    })
    @Select("select * from user")
    List<User> findAll();

附属接口
@Select("select * from account where id = #{UserId}")
    List<Account> findAccountByUid(Integer id);
```

##mybatis逆向工程

### 逆向工程需要的依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.mmm</groupId>
    <artifactId>mybatis-demo10-generator</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.9</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>

        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.0</version>

                <configuration>
                    <overwrite>true</overwrite>
                </configuration>

                <dependencies>
                    <dependency>
                        <groupId>org.mybatis.generator</groupId>
                        <artifactId>mybatis-generator-core</artifactId>
                        <version>1.3.2</version>
                    </dependency>

                    <dependency>
                        <groupId>com.mchange</groupId>
                        <artifactId>c3p0</artifactId>
                        <version>0.9.2</version>
                    </dependency>

                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>8.0.33</version>
                    </dependency>
                </dependencies>

            </plugin>
        </plugins>
    </build>

</project>
```

### mybatis的核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--mybatis的配置标签<configuration-->
<configuration>
    <properties resource="db.properties"/>
    <!--    <settings>-->
    <!--        <setting name="defaultExecutorType" value="BATCH"/>-->
    <!--    </settings>-->
    <typeAliases>
        <package name="com.mmm.pojo"/>
    </typeAliases>
    <!--配置环境mybatis的事务管理,还有数据源-->
    <environments default="mybatis">
        <environment id="mybatis">

            <transactionManager type="JDBC"></transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>

    <!--引入映射文件-->
    <mappers>
        <package name="com/mmm/mapper"/>
    </mappers>

</configuration>
```

### 逆向工程的核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator
Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!--
    targetRuntime: 执行生成的逆向工程的版本
    MyBatis3Simple: 生成基本的CRUD
    MyBatis3: 生成带条件的CRUD
    -->
    <context id="DB2Tables" targetRuntime="MyBatis3Simple">
        <!-- 数据库的连接信息 -->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://127.0.0.1:3306/mybatis?serverTimezone=Asia/Shanghai"
                        userId="root"
                        password="root">
        </jdbcConnection>
        <!-- javaBean的生成策略-->
        <javaModelGenerator targetPackage="com.mmm.pojo"
                            targetProject=".\src\main\java">
            <!--
            是否生成子包。如果为true com.xq.pojo生成的保姆那个带有层级
            目录
            false com.xq.pojo就是一个包名
            -->
            <property name="enableSubPackages" value="true" />
            <!--
            通过数据表字段生成pojo。如果字段名称带空格，会去掉空格
            -->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>
        <!-- SQL映射文件的生成策略 -->
        <sqlMapGenerator targetPackage="com.mmm.mapper"
                         targetProject=".\src\main\resources">
            <property name="enableSubPackages" value="true" />
        </sqlMapGenerator>
        <!-- Mapper接口的生成策略 -->
        <javaClientGenerator type="XMLMAPPER"
                             targetPackage="com.mmm.mapper"
                             targetProject=".\src\main\java">
            <property name="enableSubPackages" value="true" />
        </javaClientGenerator>
        <!-- 逆向分析的表 -->
        <!-- tableName设置为*号，可以对应所有表，此时不写
        domainObjectName -->
        <!-- domainObjectName属性指定生成出来的实体类的类名 -->
        <table tableName="user" domainObjectName="User"/>
    </context>
</generatorConfiguration>

```

### db.properties文件,mybatis核心配置文件加载

```proper
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/mybatis?serverTimezone=Asia/Shanghai
jdbc.username=root
jdbc.password=root
```

<h4 style="color:gray;">逆向工程配置文件里(generatorConfig.xml),红框位置改成MyBatis3会生成更复杂的查询语句</h4>

![mybatis.png](https://img2.imgtp.com/2024/05/01/4ebbJwjP.png)

### 复杂逆向工程生成的接口使用方法

```java
//查询所有数据selectByExample(null)
  @Test
    public void test01(){
        try {
            InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(in);
            SqlSession session = build.openSession();
            UserMapper mapper = session.getMapper(UserMapper.class);
            //得到一个List集合
            mapper.selectByExample(null).forEach(System.out::println);
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//添加条件查询要使用Example对象
    @Test
    public void test01(){
        try {
            InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(in);
            SqlSession session = build.openSession();
            UserMapper mapper = session.getMapper(UserMapper.class);

            //查询id为2的用户
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(2);
            mapper.selectByExample(example).forEach(System.out::println);

            session.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//多个条件查询,链式操作(在example.createCriteria.后面一直and加条件)

    @Test
    public void test01(){
        try {
            InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(in);
            SqlSession session = build.openSession();
            UserMapper mapper = session.getMapper(UserMapper.class);

            //查询在上海的男性用户
            UserExample example = new UserExample();
            example.createCriteria().andSexEqualTo("男").andAddressEqualTo("上海");
            mapper.selectByExample(example).forEach(System.out::println);

            session.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//使用or条件
  @Test
    public void test01(){
        try {
            InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(in);
            SqlSession session = build.openSession();
            UserMapper mapper = session.getMapper(UserMapper.class);

            //查询在上海或者重庆的男性用户
            UserExample example = new UserExample();
            example.createCriteria().andSexEqualTo("男").andAddressEqualTo("上海");
            example.or().andAddressEqualTo("重庆");
            mapper.selectByExample(example).forEach(System.out::println);

            session.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//更新操作

1.updateByPrimaryKey(user) //指定id修改用户信息,这个方法如果有属性没有传值,那么默认是null
    
    @Test
    public void test01(){
        try {
            InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(in);
            SqlSession session = build.openSession();
            UserMapper mapper = session.getMapper(UserMapper.class);

            //修改id为7的用户的数据
            User user = new User();
            user.setBirthday(new Date());
            user.setUsername("测试数据");
            user.setSex("男");
            user.setId(7);
            mapper.updateByPrimaryKey(user);
            session.commit();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

2.updateByPrimaryKeySelective(user) //传什么字段就修改什么字段,不传值就维持原样
    
    @Test
    public void test01(){
        try {
            InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(in);
            SqlSession session = build.openSession();
            UserMapper mapper = session.getMapper(UserMapper.class);

            //修改id为7的用户的username字段的数据
            User user = new User();
            user.setUsername("admin");
            user.setId(7);
            mapper.updateByPrimaryKeySelective(user);
            session.commit();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

```

