package com.task.service;

import com.task.entity.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

  @Test
  void getAllProductNames_correctNamesReturned() {

    //given
    ProductService productService = new ProductService();

    //when
    List<String> foundProductNames = productService.getAllProductNames();

    //then
    Assertions.assertLinesMatch(List.of("Laptop", "PC", "Smartphone"), foundProductNames);
  }

  @Test
  void saveOrUpdateAll() {

    //given
    ProductService productService = new ProductService();
    List<ProductEntity> productEntities = List.of(
        new ProductEntity("1", "Gaming Laptop"),
        new ProductEntity("4", "Tablet"));

    //when
    List<ProductEntity> savedProducts = productService.saveOrUpdateAll(productEntities);

    //then
    Assertions.assertEquals(productEntities, savedProducts);
    Assertions.assertLinesMatch(
        List.of("Gaming Laptop", "PC", "Smartphone", "Tablet"),
        productService.getAllProductNames());
  }
}
