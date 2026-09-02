package com.task.service;

import com.task.entity.ProductEntity;
import com.task.exception.ProductNotFoundException;
import com.task.repository.implementation.ProductRepositoryImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

  @Test
  void getAllProductNames_correctNamesReturned() {
    ProductService productService = new ProductService(new ProductRepositoryImpl());

    List<String> foundProductNames = productService.getAllProductNames();

    Assertions.assertLinesMatch(List.of("Laptop", "PC", "Smartphone"), foundProductNames);
  }

  @Test
  void saveOrUpdateAll() {
    ProductService productService = new ProductService(new ProductRepositoryImpl());
    List<ProductEntity> productEntities = List.of(
        new ProductEntity("1", "Gaming Laptop"),
        new ProductEntity("4", "Tablet"));

    List<ProductEntity> savedProducts = productService.saveOrUpdateAll(productEntities);

    Assertions.assertEquals(productEntities, savedProducts);
    Assertions.assertLinesMatch(
        List.of("Gaming Laptop", "PC", "Smartphone", "Tablet"),
        productService.getAllProductNames());
  }

  @Test
  void create_withoutId_generatesId() {
    ProductService productService = new ProductService(new ProductRepositoryImpl());

    ProductEntity product = productService.create(new ProductEntity(null, "Tablet"));

    Assertions.assertNotNull(product.getProductId());
    Assertions.assertEquals("Tablet", product.getProductName());
    Assertions.assertEquals(product, productService.getProduct(product.getProductId()));
  }

  @Test
  void getProduct_unknownId_throwsNotFound() {
    ProductService productService = new ProductService(new ProductRepositoryImpl());

    Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProduct("999"));
  }
}
