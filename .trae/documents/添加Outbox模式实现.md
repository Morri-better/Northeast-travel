# 添加Outbox模式实现

## 1. 创建OutboxEvent实体类

在 `entity` 包中创建 `OutboxEvent.java`，包含：

* id（主键）

* eventType（事件类型：PAYMENT\_SUCCESS）

* bizKey（业务键：payNo）

* payload（JSON字符串）

* status（状态：NEW/SENT）

* retryCount（重试次数）

* nextRetryTime（下次重试时间）

* createdAt/updatedAt（时间戳）

## 2. 创建OutboxEventMapper接口

在 `mapper` 包中创建 `OutboxEventMapper.java`，继承 `BaseMapper<OutboxEvent>`

## 3. 创建OutboxEventService服务类

在 `service` 包中创建 `OutboxEventService.java`，包含：

* `savePaymentSuccessEvent()` - 保存支付成功事件到outbox表

* `buildPayload()` - 构建JSON payload

## 4. 修改PaymentService

在 [PaymentService.java](file:///d:\东北旅游\Northeast-travel\src\main\java\com\example\travelservice\service\PaymentService.java) 中：

* 注入 `OutboxEventService`

* 将原来的领域事件发布代码（第166-168行）改为调用 `outboxEventService.savePaymentSuccessEvent()`

* 传入参数：payNo、orderId、productId、quantity

## 文件清单

* 新建：`entity/OutboxEvent.java`

* 新建：`mapper/OutboxEventMapper.java`

* 新建：`service/OutboxEventService.java`

* 修改：`service/PaymentService.java`

