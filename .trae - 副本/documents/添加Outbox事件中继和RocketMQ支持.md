# 在pom.xml中添加RocketMQ依赖

## 修改pom.xml
在项目根目录的 [pom.xml](file:///d:\东北旅游\Northeast-travel\pom.xml) 中添加RocketMQ依赖：

```xml
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-spring-boot-starter</artifactId>
    <version>2.2.3</version>
</dependency>
```

添加到dependencies部分，在fastjson依赖之后。

## 文件清单
- 修改：`pom.xml`