package com.task.service;

import com.task.entity.ProductEntity;
import com.task.exception.ProductAlreadyExistsException;
import com.task.exception.ProductNotFoundException;
import com.task.repository.ProductRepository;
import com.task.repository.implementation.ProductRepositoryImpl;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  ProductService() {
    this(new ProductRepositoryImpl());
  }

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<String> getAllProductNames() {
    return productRepository.findAll().stream()
        .map(ProductEntity::getProductName)
        .collect(Collectors.toList());
  }

  public List<ProductEntity> getAllProducts() {
    return productRepository.findAll();
  }

  public ProductEntity getProduct(String id) {
    ProductEntity product = productRepository.findById(normalizeRequiredId(id));
    if (product == null) {
      throw new ProductNotFoundException(id);
    }
    return product;
  }

  public ProductEntity create(ProductEntity productEntity) {
    String id = normalizeOptionalId(productEntity.getProductId());
    if (id == null) {
      id = UUID.randomUUID().toString();
    } else if (productRepository.findById(id) != null) {
      throw new ProductAlreadyExistsException(id);
    }

    ProductEntity product = new ProductEntity(id, normalizeName(productEntity.getProductName()));
    return productRepository.saveOrUpdate(id, product);
  }

  public ProductEntity update(String id, ProductEntity productEntity) {
    String normalizedId = normalizeRequiredId(id);
    if (productRepository.findById(normalizedId) == null) {
      throw new ProductNotFoundException(normalizedId);
    }

    ProductEntity product = new ProductEntity(
        normalizedId,
        normalizeName(productEntity.getProductName()));
    return productRepository.saveOrUpdate(normalizedId, product);
  }

  public List<ProductEntity> saveOrUpdateAll(List<ProductEntity> productEntities) {
    if (productEntities == null) {
      throw new IllegalArgumentException("products must not be null");
    }

    return productEntities.stream()
        .map(productEntity -> {
          String id = normalizeRequiredId(productEntity.getProductId());
          ProductEntity product = new ProductEntity(id, normalizeName(productEntity.getProductName()));
          return productRepository.saveOrUpdate(id, product);
        })
        .collect(Collectors.toList());
  }

  public ProductEntity deleteById(String id) {
    String normalizedId = normalizeRequiredId(id);
    ProductEntity deletedProduct = productRepository.deleteById(normalizedId);
    if (deletedProduct == null) {
      throw new ProductNotFoundException(normalizedId);
    }
    return deletedProduct;
  }

  private String normalizeOptionalId(String id) {
    if (id == null || id.isBlank()) {
      return null;
    }
    return id.trim();
  }

  private String normalizeRequiredId(String id) {
    String normalizedId = normalizeOptionalId(id);
    if (normalizedId == null) {
      throw new IllegalArgumentException("productId must not be blank");
    }
    return normalizedId;
  }

  private String normalizeName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("productName must not be blank");
    }
    return name.trim();
  }
}
