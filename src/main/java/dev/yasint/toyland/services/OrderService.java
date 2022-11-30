package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ProfileInCompleteException;
import dev.yasint.toyland.exceptions.ResourceAccessException;
import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.models.Cart;
import dev.yasint.toyland.models.Order;
import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.User;

import java.util.List;

public interface OrderService {

    Order createOrder(Customer customer, Cart cart) throws ProfileInCompleteException;

    List<Order> getAllOrders();

    List<Order> getMerchantOrders(Long merchantId);

    List<Order> getCustomerOrders(Long customerId) throws ResourceNotFoundException;

    Order findOrderById(Long orderId) throws ResourceNotFoundException;

    Order updateStatus(User user, Long orderId) throws ResourceNotFoundException, ResourceAccessException;

}
