package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.*;
import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.repositories.MerchantRepository;
import dev.yasint.toyland.repositories.ProductRepository;
import dev.yasint.toyland.services.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test/product")
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    @PostMapping("/delete-product")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> deleteProduct(@Valid @RequestBody DeleteProductRequestDTO deleteProductRequest) {

        UserDetailsImpl userDetails = getUserDetails();

        Merchant merchant = merchantRepository.findByUserId(userDetails.getId());

        Product product = productRepository
                .findById(deleteProductRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));


        if (!product.getMerchantId().equals(merchant.getId())) {
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
    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductRequestDTO addProductRequest) {

        UserDetailsImpl userDetails = getUserDetails();

        Merchant merchant = merchantRepository.findByUserId(userDetails.getId());

        if (productRepository.
                findByNameAndPriceAndMerchantId(addProductRequest.getName(),
                        addProductRequest.getPrice(),
                        merchant.getId())
                .isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new MessageResponseDTO("Merchant already has a product with the same details")
                    );
        }

        Product product = makeProduct(addProductRequest, merchant.getId());
        productRepository.save(product);

        return ResponseEntity.ok(new MessageResponseDTO("Product added successfully!"));
    }

    @Override
    @PostMapping("/edit-product")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> editProduct(@Valid @RequestBody EditProductRequestDTO editProductRequest) {

        UserDetailsImpl userDetails = getUserDetails();

        Merchant merchant = merchantRepository.findByUserId(userDetails.getId());

        Product product = productRepository
                .findById(editProductRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getMerchantId().equals(merchant.getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new MessageResponseDTO("Product is not owned by merchant")
                    );
        }

        Product editedProduct = makeProduct(editProductRequest.getProductInfo(), merchant.getId());
        editedProduct.setId(editProductRequest.getProductId());
        productRepository.save(editedProduct);

        return ResponseEntity.ok(new MessageResponseDTO("Product edited successfully!"));
    }

    @Override
    @PostMapping("/get-products")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN', 'MERCHANT')")
    public ResponseEntity<List<?>> getProducts(@Valid @RequestBody GetProductsRequestDTO getProductsRequest) {

        Optional<Merchant> merchant = merchantRepository.findById(getProductsRequest.getMerchantId());

        return ResponseEntity.ok(
                        merchant.isPresent() ?
                                merchant.get().getProducts().stream()
                                .map(this::convertToDto)
                                .collect(Collectors.toList())
                                :
                                new ArrayList<>()
                );
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

    private Product makeProduct(AddProductRequestDTO request, Long merchantId) {
        return new Product(merchantId
                , request.getName()
                , request.getPrice());
    }

    private ProductResponseDTO convertToDto(Product product) {
//        ProductResponseDTO productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);
//        return productResponseDTO;
        return new ProductResponseDTO(product.getId()
                , product.getMerchantId(), product.getName(), product.getPrice());
    }

    private UserDetailsImpl getUserDetails() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return (UserDetailsImpl) authentication.getPrincipal();
    }
}
