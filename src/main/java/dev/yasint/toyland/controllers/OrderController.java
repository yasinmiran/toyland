package dev.yasint.toyland.controllers;

import org.springframework.http.ResponseEntity;

public interface OrderController {

    ResponseEntity<?> getAllOrders();

    ResponseEntity<?> getMerchantOrders(Long merchantId);

    ResponseEntity<?> getCustomerOrders(Long customerId);

    ResponseEntity<?> updateOrderStatus(Long orderDetailId);

}
