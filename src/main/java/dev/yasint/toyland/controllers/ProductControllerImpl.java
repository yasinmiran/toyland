package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.AddProductRequestDTO;
import dev.yasint.toyland.dtos.request.DeleteProductRequestDTO;
import dev.yasint.toyland.dtos.request.EditProductRequestDTO;
import dev.yasint.toyland.dtos.request.GetProductsRequestDTO;
import dev.yasint.toyland.dtos.response.MessageResDTO;
import dev.yasint.toyland.dtos.response.ProductResponseDTO;
import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.repositories.MerchantRepository;
import dev.yasint.toyland.repositories.ProductRepository;
import dev.yasint.toyland.services.UserDetailsImpl;
import dev.yasint.toyland.dtos.request.ProductDTO;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.services.ProductService;
import dev.yasint.toyland.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantRepository merchantRepository;

//    private ModelMapper modelMapper = new ModelMapper();

    private final ProductService productService;

    @Override
    @PostMapping("/add-product")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO body) {
        User user = Common.getUserDetailsFromContext().getUser();
        Product product = productService.saveProduct(user, body.transform());
        return ResponseEntity.ok().body(product);
    }

    @Override
    @PostMapping("/delete-product")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> deleteProduct(@Valid @RequestBody DeleteProductRequestDTO deleteProductRequest) {

        UserDetailsImpl userDetails = getUserDetails();

        Merchant merchant = merchantRepository
                .findByUserId(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        Product product = productRepository
                .findById(deleteProductRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));


        if (!product.getMerchant().getId().equals(merchant.getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new MessageResDTO("Product is not owned by merchant")
                    );
        }

        productRepository.delete(product);

        return ResponseEntity.ok(new MessageResDTO("Product deleted successfully!"));
    }

//    @Override
//    @PostMapping("/add-product")
//    @PreAuthorize("hasAuthority('MERCHANT')")
//    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductRequestDTO addProductRequest) {
//
//        UserDetailsImpl userDetails = getUserDetails();
//
//        Merchant merchant = merchantRepository
//                .findByUserId(userDetails.getId())
//                .orElseThrow(() -> new RuntimeException("Merchant not found"));
//
//        if (productRepository.
//                findByNameAndMerchantId(addProductRequest.getName(),
//                        merchant.getId())
//                .isPresent()) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(
//                            new MessageResDTO("Merchant already has a product with the same details")
//                    );
//        }
//
//        Product product = makeProduct(addProductRequest, merchant);
//        productRepository.save(product);
//
//        return ResponseEntity.ok(new MessageResDTO("Product added successfully!"));
//    }

    @Override
    @PostMapping("/edit-product")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> editProduct(@Valid @RequestBody EditProductRequestDTO editProductRequest) {

        UserDetailsImpl userDetails = getUserDetails();

        Merchant merchant = merchantRepository
                .findByUserId(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        Product product = productRepository
                .findById(editProductRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getMerchant().getId().equals(merchant.getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new MessageResDTO("Product is not owned by merchant")
                    );
        }

        Product editedProduct = makeProduct(editProductRequest.getProductInfo(), merchant);
        editedProduct.setId(editProductRequest.getProductId());
        productRepository.save(editedProduct);

        return ResponseEntity.ok(new MessageResDTO("Product edited successfully!"));
    }

    @Override
    @PostMapping("/get-products")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN', 'MERCHANT')")
    public ResponseEntity<List<?>> getProducts(@Valid @RequestBody GetProductsRequestDTO getProductsRequest) {

        Merchant merchant = merchantRepository
                .findById(getProductsRequest.getMerchantId())
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        return ResponseEntity.ok(
                                merchant.getProduct().stream()
                                .map(this::convertToDto)
                                .collect(Collectors.toList())
                );
    }

    @Override
    public ResponseEntity<?> viewOrders() {
        return null;
    }

    private Product makeProduct() {
        return new Product();
    }

    private Product makeProduct(AddProductRequestDTO request, Merchant merchant) {
        return new Product(merchant
                , request.getName()
                , request.getPrice());
    }

    private ProductResponseDTO convertToDto(Product product) {
//        ProductResponseDTO productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);
//        return productResponseDTO;
        return new ProductResponseDTO(product.getId()
                , product.getMerchant().getId(), product.getName(), product.getPrice());
    }

    private UserDetailsImpl getUserDetails() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return (UserDetailsImpl) authentication.getPrincipal();
    }
}
