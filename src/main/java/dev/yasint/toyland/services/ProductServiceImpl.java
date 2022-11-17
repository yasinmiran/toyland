package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceAccessException;
import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.repositories.MerchantRepository;
import dev.yasint.toyland.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;

    @Override
    public Product findProductById(Long id) throws ResourceAccessException {
        return productRepository
                .findById(id)
                .orElseThrow(ResourceAccessException::new);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean isProductOwnedByMerchant(
            Long productId,
            Long userId
    ) throws ResourceAccessException {
        Product product = findProductById(productId);
        return Objects.equals(product.getMerchant().getUser().getId(), userId);
    }

    @Override
    public Product saveProduct(User owner, Product partial) {
        Merchant merchant = merchantRepository.findMerchantByUser(owner);
        partial.setMerchant(merchant);
        return productRepository.save(partial);
    }

    @Override
    public List<Product> saveAllProducts(User owner, List<Product> products) {
        Merchant merchant = merchantRepository.findMerchantByUser(owner);
        products.forEach(product -> {
            product.setMerchant(merchant);
        });
        return productRepository.saveAll(products);
    }

}
