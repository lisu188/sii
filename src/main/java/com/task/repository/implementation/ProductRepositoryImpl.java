package com.task.repository.implementation;

import com.task.entity.ProductEntity;
import com.task.repository.ProductRepository;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepository {

  private final Map<String, ProductEntity> products = new HashMap<>(Map.of(
      "1", new ProductEntity("1", "Laptop"),
      "2", new ProductEntity("2", "PC"),
      "3", new ProductEntity("3", "Smartphone")
  ));

  @Override
  public List<ProductEntity> findAll() {

    return products.values().stream()
        .sorted(Comparator.comparing(ProductEntity::getProductId))
        .collect(Collectors.toList());
  }

  @Override
  public ProductEntity saveOrUpdate(String id, ProductEntity entity) {

    products.put(id, entity);
    return entity;
  }

  @Override
  public ProductEntity findById(String id) {

    return products.get(id);
  }

  @Override
  public ProductEntity deleteById(String id) {

    return products.remove(id);
  }
}
