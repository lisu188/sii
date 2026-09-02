package com.task.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public class ProductEntity {

  @Pattern(
      regexp = "[A-Za-z0-9._-]{1,64}",
      message = "productId must contain only letters, digits, '.', '_' or '-' and be at most 64 characters")
  private String productId;

  @NotBlank(message = "productName must not be blank")
  @Size(max = 120, message = "productName must be at most 120 characters")
  private String productName;

  public ProductEntity() {
  }

  public ProductEntity(String productId, String productName) {
    this.productId = productId;
    this.productName = productName;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof ProductEntity)) {
      return false;
    }
    ProductEntity that = (ProductEntity) object;
    return Objects.equals(productId, that.productId)
        && Objects.equals(productName, that.productName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, productName);
  }
}
