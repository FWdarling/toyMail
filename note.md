# 基础环境

## mysql

略

## redis

```bash
mkdir -p ~/mydata/redis/conf
touch ~/mydata/redis/conf/redis.conf
docker run -p 6379:6379 --name redis -v ~/mydata/redis/data:/data -v ~/mydata/redis/conf/redis.conf:/etc/redis/redis.conf -d redis redis-server /etc/redis/redis.conf
docker exec -it redis redis-cli
```

# spring-cloud-alibaba

## 引入依赖

[文档](https://github.com/alibaba/spring-cloud-alibaba/blob/master/README-zh.md)

注意引入时按照 spring cloud、jdk、springboot版本来选择版本

## Nacos

[nacos 官方文档](https://nacos.io/zh-cn/docs/quick-start.html)

### Nacos Server 获取

按照[文档](https://nacos.io/zh-cn/docs/quick-start.html)操作即可

可以将启动命令添加 alisa 到 `~/.zshrc` 或  `~/.bash_profile` 中

```bash
alias nacos-startup='sh ${NACOS_HOME}/bin/startup.sh -m standalone'
alias nacos-shutdown='sh  ${NACOS_HOME}/bin/shutdown.sh'
```

### Nacos服务注册与发现

#### 服务注册及发现的接入

1. 按照[文档](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-discovery-example/readme-zh.md) 引入 pom 依赖，在配置文件中添加配置，在`application`上添加注解

2. 给应用添加名字和端口后启动应用

3. 如果启动失败显示缺少 loadbalancer 需要将第一步引入依赖部分修改为 (因为新版本 springcloud 使用 loadbalancer 替换了ribbon )

    ```xml
    <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
      	<exclusions>
          <exclusion>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
          </exclusion>
      	</exclusions>
    </dependency>
    
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-loadbalancer</artifactId>
      <version>2.2.2.RELEASE</version>
    </dependency>
    ```

#### 服务注册及发现的使用

1. 引入`open-feign`

2. 创建一个接口 声明为 feign client

    ```java
    @FeignClient(name = "${service-provider}")
    ```

3. 将要远程调用的 http 接口对应的 controller 方法 的完整签名粘贴到上面创建的接口中 作为方法声明 （注意 url 要修改为外部访问的url）

4. 在调用方的 `application`上标注为启用远程调用，并标注 feign client 的包路径

    ```java
    @EnableFeignClients(basePackages = "${feign-package}")
    ```

5. 可以直接通过调用此接口中的此方法来完成远程服务调用

### Nacos 配置中心

#### 使用

1. 按照[文档](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme-zh.md)引入依赖 创建配置文件`bootstrap.properties`

2. 按照启动日志中的配置名字(一般为${application-name)作为 `data id`创建配置

3. 如果配置没有生效 可以尝试添加依赖（由于springboot版本导致）

    ```xml
    <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-bootstrap -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-bootstrap</artifactId>
        <version>${latest-version}</version>
    </dependency>
    ```

4. controller上面加refresh scope 开启即时刷新

5. 配置好之后即可以使用 `@Value(${xxx})`来获取配置中 xxx 的值 （优先从配置中心获取 如果没找到会从配置文件获取）

#### 细节

1. nacos 配置有命名空间的概念 可以将配置隔离， 新增的配置都在默认的 public 空间
    通过在 `bootstrap.properties`中添加配置即可更换读取配置的命名空间

    ```properties
    spring.cloud.nacos.config.namespace=${命名空间id}
    ```

2. 配置拥有自己的分组 默认所有配置都属于默认分组： `DEFAULT_GROUP`
    通过在 `bootstrap.properties`中添加配置即可更换读取配置的组

    ```properties
    spring.cloud.nacos.config.group=${group-name}
    ```

    项目中每个微服务创建自己的命名空间， 使用配置分组区分环境

3. nacos 可以使用配置集来将多个配置文件一次读取
    通过在 `bootstrap.properties`中添加`ext-config`配置可以使用配置集
    注意此配置是个 list 因此可以读取 nacos 中的多个配置

    如果`ext-config`配置过期可以尝试使用`extension-configs`

    ```properties
    spring.cloud.nacos.config.ext-config[0].data-id=${config-data-id}
    spring.cloud.nacos.config.ext-config[0].group=${config-group-name}
    spring.cloud.nacos.config.ext-config[0].refresh=true
    
    spring.cloud.nacos.config.ext-config[1].data-id=${config-data-id}
    spring.cloud.nacos.config.ext-config[1].group=${config-group-name}
    spring.cloud.nacos.config.ext-config[1].refresh=true
    ```

     项目中可以将application.yml拆分成 数据源 service 和 mybatis-plus 三个配置



## Spring Cloud Gateway

[官方文档](https://spring.io/projects/spring-cloud-gateway)

### 简介

按照文档 spring cloud gateway 主要有三个组件 route（路由）、 predicate（断言）、filter（过滤器）

路由定义了一些 uri 断言和过滤器， 如果断言成功则匹配次路由 进入对应的过滤器找到服务地址

网关层可以为 application 使用注解来排除掉数据库

```java
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
```

### 使用示例

对于给定的请求 如果`predicates`匹配成功， 则会进入`filters`而后转发到 `uri`

其中路由的匹配是从上到下 找到第一个匹配的为准

```yaml
routes:
    - 	id: admin_route
        uri: lb://renren-fast
        predicates:
        	- Path=/api/**
        filters:
        	- RewritePath=/api/?(?<segment>.*), /renren-fast/$\{segment}
```

# note

## cors问题

### 描述

浏览器对于非简单请求进行跨域检查， 主机名协议端口号有任何差异都禁止访问

简单请求是请求方法为get、post或head， 且请求头不超出以下字段的请求

-   Accept
-   Accept-Language
-   Content-Language
-   Last-Event-ID
-   Content-Type：只限于三个值`application/x-www-form-urlencoded`、`multipart/form-data`、`text/plain`

### 解决方案

1.  使用nginx部署为同一域|
    主要原理是使用 nginx 将前端和后端部署到同一个域下， 静态请求直接发给前端， 动态请求转发给后端网关， 这样建立用户-前端-后端的通信链 前后端都在nginx域下 不存在跨域
2.  开发期间或为了方便 可以在网关层配置跨域响应头
    