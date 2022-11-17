package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.AddProductRequestDTO;
import dev.yasint.toyland.dtos.request.DeleteProductRequestDTO;
import dev.yasint.toyland.dtos.request.EditProductRequestDTO;
import dev.yasint.toyland.dtos.request.GetProductsRequestDTO;
import dev.yasint.toyland.dtos.request.ProductDTO;
import org.springframework.http.ResponseEntity;

public interface ProductController {

    ResponseEntity<?> addProduct(ProductDTO product);


    ResponseEntity<?> deleteProduct(DeleteProductRequestDTO deleteProductRequestDTO);

//    ResponseEntity<?> addProduct(AddProductRequestDTO addProductRequestDTO);

    ResponseEntity<?> editProduct(EditProductRequestDTO editProductRequestDTO);

    ResponseEntity<?> getProducts(GetProductsRequestDTO getProductsRequestDTO);

    ResponseEntity<?> viewOrders();

}
