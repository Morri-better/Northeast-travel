## 修复OrderService中order_no字段缺失的问题

**问题：** orders表的order_no字段是NOT NULL且无默认值，但代码在插入后才设置orderNo，导致插入失败。

**修改方案：**
修改`OrderService.java`的createOrder方法，调整orderNo的生成时机：
- 将`generateOrderNo(order.getId())`移到`orderRepository.insert(order)`之前
- 但由于需要orderId来生成orderNo，需要先插入获取ID，再生成orderNo，然后更新

**具体修改步骤：**
1. 保持现有逻辑不变，因为orderNo生成依赖orderId
2. 在插入前，先设置一个临时的orderNo值（如空字符串或占位符）
3. 插入后，生成真正的orderNo并更新

或者更好的方案：
1. 先插入订单（不设置orderNo，但需要数据库允许NULL）
2. 由于数据库不允许NULL，需要改为：先生成临时orderNo，插入后再更新为正式orderNo

**最优方案：**
修改createPayment方法，确保在创建支付前订单已经正确设置了orderNo。