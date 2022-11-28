package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ProfileInCompleteException;
import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.models.Cart;
import dev.yasint.toyland.models.CartItem;
import dev.yasint.toyland.models.Order;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.discount.tier.TierDiscountFactory;
import dev.yasint.toyland.models.enumerations.EOrderStatus;
import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.repositories.CustomerRepository;
import dev.yasint.toyland.repositories.OrderRepository;
import dev.yasint.toyland.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final TierDiscountFactory tierDiscountFactory;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            TierDiscountFactory tierDiscountFactory) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.tierDiscountFactory = tierDiscountFactory;
    }

    public Order createOrder(Customer customer, Cart cart) throws ProfileInCompleteException {

        // Pre-conditions

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

        List<CartItem> items = cart.getItems();

        Order order = Order.builder()
                .cart(cart)
                .customer(customer)
                .merchants(items.stream()
                        .map(item -> item.getProduct().getMerchant())
                        .collect(Collectors.toSet()))
                .status(EOrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now()).build();

        // Calculate the gross total.

        Optional<BigDecimal> grossTotal = items.stream()
                .map(item -> {
                    BigDecimal price = BigDecimal.valueOf(item.getProduct().getPrice());
                    BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
                    return quantity.multiply(price);
                })
                .reduce(BigDecimal::add);

        if (grossTotal.isEmpty()) {
            log.error("Gross total is not present. Unable to proceed with the order.");
            throw new RuntimeException("Unable to process the order.");
        }

        // Calculate the total price with discounts.

        double discountedTotal = tierDiscountFactory.tierDiscount(
                customer.getUser(),
                grossTotal.get().doubleValue()
        ).getTotalDiscount();

        order.setPrice(new BigDecimal(discountedTotal));

        // Decrement the product count from the inventory.

        items.forEach((item) -> {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.saveAndFlush(product);
        });

        // TODO:
        // Do a dummy payment authorization.
        // i.e., paymentService.deduct();

        // TODO:
        // Set a dummy payment reference.
        // i.e., order.setPaymentReference("REFERENCE-123");

        // TODO:
        // Once the payment is succeeded, the system should
        // auto allocate a Driver to pick up the items and
        // do the delivery.

        return orderRepository.save(order);

    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getMerchantOrders(Long merchantId) {
        return new ArrayList<>();
    }

    @Override
    public List<Order> getCustomerOrders(Long customerId) throws ResourceNotFoundException {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(ResourceNotFoundException::new);
        return orderRepository.findAllByCustomer(customer);
    }

}
