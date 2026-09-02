package com.task.controller;

import com.task.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductControllerTest {

  @Test
  void productEndpoints() {

    ProductController productController = new ProductController();

    Assertions.assertEquals(
        List.of("Laptop", "PC", "Smartphone"),
        productController.findAllProducts().stream()
            .map(ProductEntity::getProductName)
            .collect(Collectors.toList()));

    productController.saveOrUpdateAll(List.of(new ProductEntity("4", "Tablet")));
    Assertions.assertEquals(4, productController.findAllProducts().size());

    ProductEntity deletedProduct = productController.deleteById("2");
    Assertions.assertEquals("PC", deletedProduct.getProductName());
    Assertions.assertEquals(3, productController.findAllProducts().size());
  }
}
