package dev.yasint.toyland.services;

import dev.yasint.toyland.dtos.request.OrderProductDTO;
import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.models.*;
import dev.yasint.toyland.models.enumerations.EOrderStatus;
import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final CustomerRepository customerRepository;

    private final MerchantRepository merchantRepository;

    private final ProductRepository productRepository;

    @Override
    public Order saveOrder(User customerUser, List<OrderProductDTO> products, String description) {
        Customer customer = customerRepository.findCustomerByUser(customerUser);

        Order order = Order.builder()
                .customer(customer)
                .orderNo("test")
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();

        products.forEach(productDTO -> {
            Product product = productRepository.getReferenceById(productDTO.getProductId());

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .description(productDTO.getDescription())
                    .status(EOrderStatus.CREATED)
                    .product(product)
                    .quantity(productDTO.getQuantity())
                    .modifiedAt(LocalDateTime.now())
                    .build();

            order.getOrderDetails().add(orderDetail);
        });

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getMerchantOrders(Long merchantId) {
        return orderRepository.findAllByMerchantId(merchantId);
    }

    @Override
    public List<Order> getCustomerOrders(Long customerId) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(ResourceNotFoundException::new);
        return orderRepository.findAllByCustomer(customer);
    }
}
