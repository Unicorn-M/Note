



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





