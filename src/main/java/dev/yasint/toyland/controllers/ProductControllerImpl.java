package dev.yasint.toyland.controllers;

import dev.yasint.toyland.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductControllerImpl implements ProductController {

    @Override
    @PostMapping("/")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {



        return null;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable("id") Long productId) {
        return null;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<?> getProducts() {
        return null;
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<?> editProduct(@Valid @RequestBody Product product) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId) {
        return null;
    }

}
