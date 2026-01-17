# 重命名Product和Tour实体类为Products和Tours

## 1. 重命名实体类文件

* 将 `entity/Product.java` 重命名为 `entity/Products.java`

* 将 `entity/Tour.java` 重命名为 `entity/Tours.java`

## 2. 修改实体类内容

在 [Products.java](file:///d:\东北旅游\Northeast-travel\src\main\java\com\example\travelservice\entity\Product.java) 中：

* 类名改为 `Products`

* 表名注解改为 `@TableName("products")`

在 [Tours.java](file:///d:\东北旅游\Northeast-travel\src\main\java\com\example\travelservice\entity\Tour.java) 中：

* 类名改为 `Tours`

* 表名注解改为 `@TableName("tours")`

## 3. 重命名Mapper接口文件

* 将 `mapper/ProductMapper.java` 重命名为 `mapper/ProductsMapper.java`

* 将 `mapper/TourMapper.java` 重命名为 `mapper/ToursMapper.java`

## 4. 修改Mapper接口内容

在 [ProductsMapper.java](file:///d:\东北旅游\Northeast-travel\src\main\java\com\example\travelservice\mapper\ProductMapper.java) 中：

* 接口名改为 `ProductsMapper`

* 泛型改为 `BaseMapper<Products>`

在 [ToursMapper.java](file:///d:\东北旅游\Northeast-travel\src\main\java\com\example\travelservice\mapper\TourMapper.java) 中：

* 接口名改为 `ToursMapper`

* 泛型改为 `BaseMapper<Tours>`

## 5. 更新引用

在 [PaymentSuccessListener.java](file:///d:\东北旅游\Northeast-travel\src\main\java\com\example\travelservice\domain\listener\PaymentSuccessListener.java) 中：

* 导入改为 `com.example.travelservice.entity.Products`

* 导入改为 `com.example.travelservice.entity.Tours`

* 注入改为 `ProductsMapper`

* 注入改为 `ToursMapper`

* 方法调用中的类引用改为 `Products` 和 `Tours`

## 文件清单

* 重命名：`entity/Product.java` → `entity/Products.java`

* 重命名：`entity/Tour.java` → `entity/Tours.java`

* 重命名：`mapper/ProductMapper.java` → `mapper/ProductsMapper.java`

* 重命名：`mapper/TourMapper.java` → `mapper/ToursMapper.java`

* 修改：`domain/listener/PaymentSuccessListener.java`

