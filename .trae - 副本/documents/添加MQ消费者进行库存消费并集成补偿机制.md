# 添加MQ消费者进行库存消费并集成补偿机制

## 1. 创建PaymentSuccessMsg消息DTO
在 `dto` 包中创建 `PaymentSuccessMsg.java`，包含：
- orderId（订单ID）
- payNo（支付号）

## 2. 创建BizConsumeLog实体类
在 `entity` 包中创建 `BizConsumeLog.java`，包含：
- id（主键，自增）
- eventType（事件类型）
- bizKey（业务键）
- status（状态：SUCCESS）
- createdAt（创建时间）

## 3. 创建BizConsumeLogMapper接口
在 `mapper` 包中创建 `BizConsumeLogMapper.java`，继承 `BaseMapper<BizConsumeLog>`

## 4. 创建BizConsumeLogService服务类
在 `service` 包中创建 `BizConsumeLogService.java`，包含：
- `tryAcquire(String eventType, String bizKey)` - 尝试获取（幂等）
  - 插入成功日志
  - 唯一键冲突返回false

## 5. 创建StockService服务类
在 `service` 包中创建 `StockService.java`，包含：
- `deductStockAfterPayment(Long orderId)` - 支付后扣库存
  - 查询订单获取productType、productId、quantity
  - 复用PaymentSuccessListener中的deductTourStock和deductProductStock方法
  - 成功：调用 `stockDeductLogService.markSuccess()`
  - 失败：调用 `stockDeductLogService.recordFail()`

## 6. 创建StockDeductConsumer消费者
在 `mq` 包中创建 `StockDeductConsumer.java`，包含：
- `@RocketMQMessageListener` 注解，监听 `stock.deduct` 主题
- 实现 `RocketMQListener<MessageExt>`
- `onMessage(MessageExt msg)` 方法
  - 解析消息为 `PaymentSuccessMsg`
  - 调用 `bizConsumeLogService.tryAcquire()` 进行幂等检查
  - 如果已处理过，直接返回
  - 调用 `stockService.deductStockAfterPayment()` 扣库存
  - 失败不抛异常，避免RocketMQ重试+业务补偿双重重试

## 文件清单
- 新建：`dto/PaymentSuccessMsg.java`
- 新建：`entity/BizConsumeLog.java`
- 新建：`mapper/BizConsumeLogMapper.java`
- 新建：`service/BizConsumeLogService.java`
- 新建：`service/StockService.java`
- 新建：`mq/StockDeductConsumer.java`