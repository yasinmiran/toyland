package dev.yasint.toyland.services;

import dev.yasint.toyland.dtos.request.OrderProductDTO;
import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.models.Order;
import dev.yasint.toyland.models.user.User;

import java.util.List;

public interface OrderService {

    Order saveOrder(User customerUser, List<OrderProductDTO> products, String description);

    List<Order> getAllOrders();

    List<Order> getMerchantOrders(Long merchantId);

    List<Order> getCustomerOrders(Long customerId) throws ResourceNotFoundException;
}
