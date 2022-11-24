package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.CreateOrderDTO;
import dev.yasint.toyland.models.Order;
import org.springframework.http.ResponseEntity;

public interface OrderController {

    ResponseEntity<?> createOrder(CreateOrderDTO body);

    ResponseEntity<?> getAllOrders();

    ResponseEntity<?> getMerchantOrders(Long merchantId);

    ResponseEntity<?> getCustomerOrders(Long customerId);

    ResponseEntity<?> getOrderDetails(Long orderId);

    ResponseEntity<?> updateOrderStatus(Long orderDetailId);
}
