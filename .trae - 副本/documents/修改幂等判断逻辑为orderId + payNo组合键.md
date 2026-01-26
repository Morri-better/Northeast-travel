# 修改幂等判断逻辑为orderId + payNo组合键

## 1. 修改BizConsumeLog实体类

在 [BizConsumeLog.java](file:///d:\东北旅游\Northeast-travel\src\main\java\com\example\travelservice\entity\BizConsumeLog.java) 中添加：

* orderId（订单ID）字段

## 2. 修改BizConsumeLogService服务类

在 [BizConsumeLogService.java](file:///d:\东北旅游\Northeast-travel\src\main\java\com\example\travelservice\service\BizConsumeLogService.java) 中修改：

* `tryAcquire(String eventType, String bizKey)` 改为 `tryAcquire(String eventType, Long orderId, String payNo)`

* 设置orderId、eventType、payNo到日志对象

* bizKey设置为orderId + payNo的组合（如："123456:P20250119123456"）

## 3. 修改StockDeductConsumer消费者

在 [StockDeductConsumer.java](file:///d:\东北旅游\Northeast-travel\src\main\java\com\example\travelservice\mq\StockDeductConsumer.java) 中修改：

* 调用 `bizConsumeLogService.tryAcquire(EVENT_TYPE, orderId, payNo)` 传入orderId和payNo

* 移除EVENT\_TYPE常量（如果不需要）

## 文件清单

* 修改：`entity/BizConsumeLog.java` - 添加orderId字段

* 修改：`service/BizConsumeLogService.java` - 修改tryAcquire方法

* 修改：`mq/StockDeductConsumer.java` - 修改幂等判断调用

