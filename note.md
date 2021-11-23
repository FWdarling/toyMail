# PS

----

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

### Nacos Server 获取

按照[文档](https://nacos.io/zh-cn/docs/quick-start.html)操作即可

可以将启动命令添加 alisa 到 `~/.zshrc` 或  `~/.bash_profile` 中

```bash
alias nacos-startup='sh ${NACOS_HOME}/bin/startup.sh -m standalone'
alias nacos-shutdown='sh  ${NACOS_HOME}/bin/shutdown.sh'
```

### Nacos服务注册与发现

#### 服务注册

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

#### 服务发现

1. 引入`open-feign`

2. 创建一个接口 声明为 feign client

   ```java
   @FeignClient(name = "${service-provider}")
   ```

3. 将要远程调用的 http 接口对应的 controller 方法 的完整签名粘贴到上面创建的接口中 作为方法声明 （注意 url 要修改为外部访问的url 即完整的服务+url）

4. 在调用方的 `application`上标注为启用远程调用，并标注 feign client 的包路径

   ```java
   @EnableFeignClients(basePackages = "${feign-package}")
   ```

5. 可以直接通过调用此接口中的此方法来完成远程服务调用

### Nacos 配置中心

1. 按照[文档](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme-zh.md)引入依赖 创建配置文件`bootstrap.properties`

2. 按照启动日志中的配置名字(一般为${application-name.properties)作为 `data id`创建配置

3. 如果配置没有生效 可以尝试添加依赖（由于springboot版本导致）

   ```xml
   <-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-bootstrap -->
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-bootstrap</artifactId>
       <version>${latest-version}</version>
   </dependency>
   ```

4. controller上面加refresh scope 开启即时刷新

5. 配置好之后即可以使用 `@Value(${xxx})`来获取配置中 xxx 的值 （优先从配置中心获取 如果没找到会从配置文件获取）