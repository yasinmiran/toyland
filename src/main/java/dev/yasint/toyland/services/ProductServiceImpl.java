package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceAccessException;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.repositories.MerchantRepository;
import dev.yasint.toyland.repositories.ProductRepository;
import dev.yasint.toyland.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final MerchantRepository merchantRepository;

    @Override
    public Product findProductById(Long id) throws ResourceAccessException {
        return productRepository
                .findById(id)
                .orElseThrow(ResourceAccessException::new);
    }

    @Override
    public boolean isProductOwnedByMerchant(
            Long productId,
            Long userId
    ) throws ResourceAccessException {
        Product product = findProductById(productId);
        return Objects.equals(product.getMerchant().getUser().getId(), userId);
    }



}
