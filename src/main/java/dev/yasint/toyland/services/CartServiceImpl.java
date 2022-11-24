package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ProfileInCompleteException;
import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.Cart;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.__Order;
import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.*;
import dev.yasint.toyland.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;
    private final ContactRepository contactRepository;
    private final PaymentRepository paymentRepository;

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

        Set<Long> removedCartItemIds = new HashSet<>();

        items.removeIf(item -> {
            if (item.getProductId().equals(productId)) {
                removedCartItemIds.add(item.getId());
                return true;
            }
            return false;
        });

        // FIXME: remove the stale items from table
        // TODO(yasinmiran):
        cartItemRepository.deleteAllById(removedCartItemIds);

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
        AtomicReference<Cart.CartItem> remove = new AtomicReference<>();

        items.removeIf(item -> {
            if (Objects.equals(item.getProductId(), productId)) {
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

    // Checkout handover -> Order

    @Override
    public void checkout() throws ProfileInCompleteException {

        // Check whether the user profile is completed

        User user = Common.getUserDetailsFromContext().getUser();
        Customer customer = customerRepository.findCustomerByUser(user);

        if (!customer.getContact().isCompleted()) {
            throw new ProfileInCompleteException(
                    "Please fill in contact details to proceed."
            );
        }

        if (!customer.getPayment().isCompleted()) {
            throw new ProfileInCompleteException(
                    "Please add your payment details to checkout."
            );
        }

        // If everything is in place then create an order.

        Cart cart = cartRepository.findCartByUser(user);
        __Order order = new __Order();

        // Set the mandatory associations.
        order.setCart(cart);
        order.setCustomer(customer);

        // First calculate the price for products.
        double price = calculatePrice(user, cart);
        // TODO:Then apply the discounts.
        // TODO:Finally, set the total price.
        order.setTotalPrice(price);

        // Set a dummy payment reference.
        order.setPaymentReference("DUMMY-PAYMENT-REFERENCE");

    }

    public double calculatePrice(User user, Cart cart) {

//        cart.getItems().stream().map(item -> {
//
//            Optional<Product> prod = productRepository.findById(item.getProductId());
//
//            if (prod.isPresent()) {
//                prod.get().get
//            }
//
//        })

        return 0;
    }

}
