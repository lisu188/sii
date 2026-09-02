package com.task.controller;

import com.task.entity.ProductEntity;
import com.task.service.ProductService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<ProductEntity> findAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  public ProductEntity findById(@PathVariable("id") String id) {
    return productService.getProduct(id);
  }

  @PostMapping
  public ResponseEntity<ProductEntity> create(@Valid @RequestBody ProductEntity productEntity) {
    ProductEntity createdProduct = productService.create(productEntity);
    return ResponseEntity
        .created(URI.create("/products/" + createdProduct.getProductId()))
        .body(createdProduct);
  }

  @PutMapping("/{id}")
  public ProductEntity update(
      @PathVariable("id") String id,
      @Valid @RequestBody ProductEntity productEntity) {
    return productService.update(id, productEntity);
  }

  @PutMapping
  public List<ProductEntity> saveOrUpdateAll(
      @Valid @RequestBody List<ProductEntity> productEntities) {
    return productService.saveOrUpdateAll(productEntities);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {
    productService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
