package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.AddProductRequestDTO;
import dev.yasint.toyland.dtos.DeleteProductRequestDTO;
import dev.yasint.toyland.dtos.EditProductRequestDTO;
import dev.yasint.toyland.dtos.GetProductsRequestDTO;
import org.springframework.http.ResponseEntity;

public interface ProductController {



    ResponseEntity<?> deleteProduct(DeleteProductRequestDTO deleteProductRequestDTO);

    ResponseEntity<?> addProduct(AddProductRequestDTO addProductRequestDTO);

    ResponseEntity<?> editProduct(EditProductRequestDTO editProductRequestDTO);

    ResponseEntity<?> getProducts(GetProductsRequestDTO getProductsRequestDTO);

    ResponseEntity<?> viewOrders();

}
