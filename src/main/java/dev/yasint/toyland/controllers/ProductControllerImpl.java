package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.ProductDTO;
import dev.yasint.toyland.dtos.response.MessageResDTO;
import dev.yasint.toyland.exceptions.ResourceAccessException;
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
import java.util.List;
import java.util.stream.Collectors;

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
    @PostMapping("/bulk/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> addAllProducts(@Valid @RequestBody List<ProductDTO> products) {
        User user = Common.getUserDetailsFromContext().getUser();
        List<Product> savedProducts = productService.saveAllProducts(user,
                products
                        .stream()
                        .map(ProductDTO::transform)
                        .collect(Collectors.toList()));
        return ResponseEntity.ok().body(savedProducts);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable("id") Long productId) {
        try {
            Product product = productService.findProductById(productId);
            return ResponseEntity.ok().body(product);
        } catch (ResourceAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok()
                .body(productService.getAllProducts());
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> editProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok()
                .body(new MessageResDTO("This feature is coming soon."));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId) {
        return ResponseEntity.ok()
                .body(new MessageResDTO("This feature is coming soon."));
    }

}