package com.task.service;

import com.task.entity.ProductEntity;
import com.task.repository.ProductRepository;
import com.task.repository.implementation.ProductRepositoryImpl;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

  private final ProductRepository productRepository = new ProductRepositoryImpl();

  public List<String> getAllProductNames() {

    return productRepository.findAll().stream()
        .map(ProductEntity::getProductName)
        .collect(Collectors.toList());
  }

  public List<ProductEntity> getAllProducts() {

    return productRepository.findAll();
  }

  public List<ProductEntity> saveOrUpdateAll(List<ProductEntity> productEntities) {

    productEntities.forEach(
        productEntity -> productRepository.saveOrUpdate(productEntity.getProductId(), productEntity));
    return productEntities;
  }

  public ProductEntity deleteById(String id) {

    return productRepository.deleteById(id);
  }
}
