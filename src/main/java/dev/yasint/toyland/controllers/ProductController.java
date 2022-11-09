package dev.yasint.toyland.controllers;

import dev.yasint.toyland.models.Product;
import org.springframework.http.ResponseEntity;

public interface ProductController {

    ResponseEntity<?> addProduct(Product product);

    ResponseEntity<?> getProductDetails(Long productId);

    ResponseEntity<?> getProducts();

    ResponseEntity<?> editProduct(Product product);

    ResponseEntity<?> deleteProduct(Long productId);


}
