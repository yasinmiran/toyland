package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceAccessException;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.user.User;

import java.util.List;

public interface ProductService {

    Product findProductById(Long id) throws ResourceAccessException;

    List<Product> getAllProducts();

    boolean isProductOwnedByMerchant(Long productId, Long userId) throws ResourceAccessException;

    Product saveProduct(User owner, Product partial) throws UnableToSatisfyException;

    List<Product> saveAllProducts(User owner, List<Product> products);

}
