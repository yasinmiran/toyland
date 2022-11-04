package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.*;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.repositories.ProductRepository;
import dev.yasint.toyland.services.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/merchant")
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @PostMapping("/delete-product")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> deleteProduct(DeleteProductRequestDTO deleteProductRequest) {

        Product product = productRepository
                .findById(deleteProductRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));


        if (!product.getMerchantId().equals(deleteProductRequest.getUserId())) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new MessageResponseDTO("Product is not owned by merchant")
                    );
        }

        productRepository.delete(product);

        return ResponseEntity.ok(new MessageResponseDTO("Product deleted successfully!"));
    }

    @Override
    @PostMapping("/add-product")
    @PreAuthorize("hasAuthority('MERCHANT')")
    public ResponseEntity<?> addProduct(AddProductRequestDTO addProductRequest) {

        if (productRepository.
                findByNameAndPriceAndMerchantId(addProductRequest.getName(),
                        addProductRequest.getPrice(),
                        addProductRequest.getUserId())
                .isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new MessageResponseDTO("Merchant already has a product with the same details")
                    );
        }

        Product product = makeProduct(addProductRequest);
        productRepository.save(product);

        return ResponseEntity.ok(new MessageResponseDTO("Product added successfully!"));
    }

    @Override
    @PostMapping("/edit-product")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> editProduct(EditProductRequestDTO editProductRequest) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        UserDetailsImpl userDetails =
                (UserDetailsImpl) authentication.getPrincipal();

        userDetails.getId();

        Product product = productRepository
                .findById(editProductRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getMerchantId().equals(editProductRequest.getProductInfo().getUserId())) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new MessageResponseDTO("Product is not owned by merchant")
                    );
        }

        Product editedProduct = makeProduct(editProductRequest.getProductInfo());
        editedProduct.setId(editProductRequest.getProductId());
        productRepository.save(editedProduct);

        return ResponseEntity.ok(new MessageResponseDTO("Product edited successfully!"));
    }

    @Override
    @GetMapping("/get-products")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> getProducts(GetProductsRequestDTO getProductsRequest) {

        List<Product> products = productRepository
                .findAllByMerchantId(getProductsRequest.getUserId());

        return ResponseEntity.ok(
                new GetProductsResponseDTO(
                        products.stream()
                                .map(this::convertToDto)
                                .collect(Collectors.toList())
        ));

    }

    @Override
    public ResponseEntity<?> viewOrders() {
        return null;
    }

    private Product makeProduct() {
        return new Product();
    }

//    private Product makeProduct(Long merchantId, String name, Double price) {
//        return new Product(merchantId, name, price);
//    }

    private Product makeProduct(AddProductRequestDTO request) {
        return new Product(request.getUserId()
                , request.getName()
                , request.getPrice());
    }

    private ProductResponseDTO convertToDto(Product product) {
        ProductResponseDTO productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);
        return productResponseDTO;
    }
}
