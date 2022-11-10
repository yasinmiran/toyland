package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.Cart;

public interface CartService {

    Cart getCart() throws UnableToSatisfyException;

    Cart addItemToCart(Long productId, Integer quantity)
            throws Exception;

    Cart removeItemFromCart(Long productId) throws UnableToSatisfyException;

    Cart emptyCart() throws UnableToSatisfyException;

}
