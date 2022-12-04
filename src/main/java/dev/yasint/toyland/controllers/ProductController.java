package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductController {

    ResponseEntity<?> addProduct(ProductDTO product);

    ResponseEntity<?> addAllProducts(List<ProductDTO> product);

    ResponseEntity<?> getProductDetails(Long productId);

    ResponseEntity<?> getProducts();

    ResponseEntity<?> editProduct(ProductDTO product, Long productId);

    ResponseEntity<?> deleteProduct(Long productId);


}
