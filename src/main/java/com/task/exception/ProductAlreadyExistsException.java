package com.task.exception;

public class ProductAlreadyExistsException extends RuntimeException {

  public ProductAlreadyExistsException(String id) {
    super("Product with id '" + id + "' already exists");
  }
}
