package com.example.travelservice.mapper;

import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.Products;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductMapper {
@Select("select * from products order by price")
    Page<Products> select(ProductPageQueryRequest request);
@Select("select * from products where id = #{id}")
    Products selectById(Long id);
}
