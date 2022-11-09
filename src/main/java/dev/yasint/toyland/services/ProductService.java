package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceAccessException;
import dev.yasint.toyland.models.Product;

public interface ProductService {

    Product findProductById(Long id) throws ResourceAccessException;

    boolean isProductOwnedByMerchant(Long productId, Long userId) throws ResourceAccessException;

}
