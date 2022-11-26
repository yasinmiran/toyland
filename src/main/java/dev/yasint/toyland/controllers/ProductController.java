package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.ProductDTO;
import dev.yasint.toyland.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductController {
    ResponseEntity<?> deleteProduct(Long productId);
    
    ResponseEntity<?> addAllProducts(List<ProductDTO> product);

    ResponseEntity<?> addProduct(ProductDTO product);

    
    ResponseEntity<?> getProductDetails(Long productId);
    
    
    ResponseEntity<?> editProduct(Product product);
    ResponseEntity<?> getProducts();

}
