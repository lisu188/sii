package com.task.repository;

import com.task.entity.ProductEntity;
import java.util.List;

public interface ProductRepository {

  List<ProductEntity> findAll();

  ProductEntity saveOrUpdate(String id, ProductEntity entity);

  ProductEntity findById(String id);

  ProductEntity deleteById(String id);
}
