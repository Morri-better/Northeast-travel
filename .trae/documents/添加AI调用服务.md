# 添加AI调用服务实现计划

## 1. 添加依赖
在[pom.xml](file:///d:\东北旅游\Northeast-travel\pom.xml)中添加`spring-boot-starter-webflux`依赖，以支持WebClient

## 2. 创建AI响应DTO
在`dto/response`包中创建`AiChatResponse.java`，使用Lombok注解简化代码

## 3. 创建聊天请求DTO
在`dto/request`包中创建`ChatRequest.java`，使用Lombok注解和验证注解

## 4. 创建AI服务客户端
在`service`包中创建`TourismAiClient.java`，使用WebClient调用AI服务（30秒超时）

## 5. 创建聊天控制器
在`controller`包中创建`ChatController.java`，使用项目统一的`ApiResponse`格式返回结果

## 6. 配置WebClient Bean（可选）
如果需要更灵活的配置，可以在config包中创建WebClient配置类

## 文件清单
- 修改：`pom.xml`
- 新建：`dto/response/AiChatResponse.java`
- 新建：`dto/request/ChatRequest.java`
- 新建：`service/TourismAiClient.java`
- 新建：`controller/ChatController.java`