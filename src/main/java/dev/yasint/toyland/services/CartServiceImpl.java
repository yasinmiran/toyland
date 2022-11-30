package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ProfileInCompleteException;
import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.Cart;
import dev.yasint.toyland.models.CartItem;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.CartItemRepository;
import dev.yasint.toyland.repositories.CartRepository;
import dev.yasint.toyland.repositories.CustomerRepository;
import dev.yasint.toyland.repositories.ProductRepository;
import dev.yasint.toyland.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderServiceImpl orderService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           CartItemRepository cartItemRepository,
                           OrderServiceImpl orderService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderService = orderService;
    }

    @Override
    public Cart getCart() {
        User user = Common.getUserDetailsFromContext().getUser();
        Customer customer = customerRepository.findCustomerByUser(user);
        Cart cart = customer.getCart();
        if (cart == null) {
            Cart _cart = new Cart();
            _cart.setItems(new ArrayList<>());
            customer.setCart(cartRepository.save(_cart));
            customerRepository.save(customer);
            return customer.getCart();
        }
        return cart;
    }

    @Override
    @Transactional
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

        List<CartItem> items = cart.getItems();

        Set<Long> removingItems = new HashSet<>();

        items.removeIf(item -> {
            if (item.getProduct().getId().equals(productId)) {
                removingItems.add(item.getId());
                return true;
            }
            return false;
        });

        if (removingItems.size() > 0)
            cartItemRepository.deleteAllById(removingItems);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        List<CartItem> newList = new ArrayList<>(items);
        newList.add(cartItem);
        cart.setItems(newList);

        return cart;

    }

    @Override
    public Cart removeItemFromCart(Long productId) {

        Cart cart = getCart();

        List<CartItem> items = cart.getItems();
        AtomicReference<CartItem> remove = new AtomicReference<>();

        items.removeIf(item -> {
            if (Objects.equals(item.getProduct().getId(), productId)) {
                remove.set(item);
                return true;
            }
            return false;
        });

        if (remove.get() != null)
            cartItemRepository.deleteById(remove.get().getId());

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

    @Override
    public void checkout() throws ProfileInCompleteException {

        User user = Common.getUserDetailsFromContext().getUser();
        Customer customer = customerRepository.findCustomerByUser(user);
        orderService.createOrder(customer, customer.getCart());

    }

}
