package com.task.controller;

import com.task.annotation.spring.DeleteMapping;
import com.task.annotation.spring.GetMapping;
import com.task.annotation.spring.PutMapping;
import com.task.annotation.spring.RequestMapping;
import com.task.annotation.spring.RestController;
import com.task.entity.ProductEntity;
import com.task.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService = new ProductService();

  @GetMapping
  public List<ProductEntity> findAllProducts() {

    return productService.getAllProducts();
  }

  @PutMapping
  public List<ProductEntity> saveOrUpdateAll(List<ProductEntity> productEntities) {

    return productService.saveOrUpdateAll(productEntities);
  }

  @DeleteMapping("/{id}")
  public ProductEntity deleteById(String id) {

    return productService.deleteById(id);
  }
}
