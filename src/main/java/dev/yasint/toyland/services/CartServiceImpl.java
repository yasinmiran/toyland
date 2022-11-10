package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.Cart;
import dev.yasint.toyland.models.Customer;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.repositories.CartRepository;
import dev.yasint.toyland.repositories.CustomerRepository;
import dev.yasint.toyland.repositories.ProductRepository;
import dev.yasint.toyland.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Cart getCart() {
        User user = Common.getUserDetailsFromContext().getUser();
        Customer customer = customerRepository.findCustomerByUser(user);
        Cart cart = cartRepository.findCartByUser(user);
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setItems(new ArrayList<>());
            newCart.setUser(user);
            cart = cartRepository.save(newCart);
            customer.setCart(cart);
            customerRepository.save(customer);
            return cart;
        }
        return cart;
    }

    @Override
    public Cart addItemToCart(Long productId, Integer quantity) throws Exception {

        Cart cart = getCart();

        Product product = productRepository
                .findById(productId)
                .orElseThrow(ResourceNotFoundException::new);

        // Pre-Condition to check the quantity before adding
        // it to the user's cart.
        if (quantity > product.getQuantity()) {
            throw new Exception("Requested quantity is invalid.");
        }

        List<Cart.CartItem> items = cart.getItems();

        items.removeIf(item -> item.getProductId().equals(productId));

        // FIXME: remove the stale items from table

        Cart.CartItem cartItem = new Cart.CartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);

        items.add(cartItem);

        Cart updatedCart = cartRepository.save(cart);

        if (updatedCart.getItems().contains(cartItem)) {
            log.info("Successfully added cart item.");
        }

        return updatedCart;

    }

    @Override
    public Cart removeItemFromCart(Long productId) {

        Cart cart = getCart();

        List<Cart.CartItem> items = cart.getItems();
        items.removeIf(item -> Objects.equals(item.getProductId(), productId));
        cart.setItems(List.copyOf(items));

        return cartRepository.save(cart);

    }

    @Override
    public Cart emptyCart() throws UnableToSatisfyException {

        Cart cart = getCart();

        if (cart == null)
            throw new UnableToSatisfyException();

        cart.setItems(new ArrayList<>());

        return cartRepository.save(cart);

    }

}
