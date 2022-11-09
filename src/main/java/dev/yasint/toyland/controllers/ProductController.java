package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.AddProductRequestDTO;
import dev.yasint.toyland.dtos.request.DeleteProductRequestDTO;
import dev.yasint.toyland.dtos.request.EditProductRequestDTO;
import dev.yasint.toyland.dtos.request.GetProductsRequestDTO;
import org.springframework.http.ResponseEntity;

public interface ProductController {



    ResponseEntity<?> deleteProduct(DeleteProductRequestDTO deleteProductRequestDTO);

    ResponseEntity<?> addProduct(AddProductRequestDTO addProductRequestDTO);

    ResponseEntity<?> editProduct(EditProductRequestDTO editProductRequestDTO);

    ResponseEntity<?> getProducts(GetProductsRequestDTO getProductsRequestDTO);

    ResponseEntity<?> viewOrders();

}
