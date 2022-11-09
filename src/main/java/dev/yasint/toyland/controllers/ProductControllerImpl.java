package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.ProductDTO;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.services.ProductService;
import dev.yasint.toyland.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO body) {
        User user = Common.getUserDetailsFromContext().getUser();
        Product product = productService.saveProduct(user, body.transform());
        return ResponseEntity.ok().body(product);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable("id") Long productId) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<?> getProducts() {
        return null;
    }

    @Override
    @PutMapping
    public ResponseEntity<?> editProduct(@Valid @RequestBody Product product) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId) {
        return null;
    }

}
