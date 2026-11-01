# ${artifactId}

由 `tech.zqxx:ssh-archetype` 生成的 SSH 脚手架项目。

- Java 根包：`${package}`
- Spring MVC + Spring + Hibernate（非 Spring Boot）
- FreeMarker、JSP、JSON 三种视图
- HikariCP + Hibernate 7，支持 MariaDB / PostgreSQL / SQL Server
- Spring Security 表单登录（默认 `admin / admin123`）
- Spring Session + Redis 共享会话
- Redisson Hibernate 二级缓存

<h2>环境要求</h2>

- JDK 17+
- Maven 3.9+
- Redis（默认 `127.0.0.1:6379`，无密码）
- 对应数据库已创建，默认库名 `ssh`，连接参数见 `src/main/resources/props/jdbc*.properties`

<h2>启动</h2>

默认使用 MariaDB：

```bat
mvn -B -DskipTests clean package
mvn -B jetty:run
```

PostgreSQL：

```bat
set MAVEN_OPTS=-Dspring.profiles.active=pgsql
mvn -B jetty:run
```

SQL Server：把 `pgsql` 换成 `sqlserver`。

访问 <http://127.0.0.1:8080/>。

<h2>目录约定</h2>

- `${package}.common`：Controller
- `${package}.common.entity`：JPA 实体
- `${package}.common.service`：事务/服务
- `${package}.common.config`：Redisson 等配置类
- `src/main/resources/spring`：Spring XML
- `src/main/resources/props`：数据库、Redis、Hibernate 配置

<h2>Hibernate 二级缓存</h2>

`spring-core.xml` 按 Profile 加载不同的 Hibernate 属性文件：

- `props/hibernate.properties`：MariaDB，缓存区域前缀 `mariadb`
- `props/hibernate-pgsql.properties`：PostgreSQL，前缀 `pgsql`
- `props/hibernate-sqlserver.properties`：SQL Server，前缀 `sqlserver`

不同数据库使用独立的 `hibernate.cache.region_prefix`，避免切换数据源后从 Redis 读到另一个数据库的缓存。

Spring Session 的 Redis 命名空间为 `${artifactId}:session`。
