package dev.yasint.toyland.controllers;

import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.models.Order;
import dev.yasint.toyland.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok().body(orders);
    }

    @Override
    @GetMapping("/merchant/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public ResponseEntity<?> getMerchantOrders(@PathVariable("id") Long merchantId) {
        List<Order> orders = orderService.getMerchantOrders(merchantId);
        return ResponseEntity.ok().body(orders);
    }

    @Override
    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
    public ResponseEntity<?> getCustomerOrders(@PathVariable("id") Long customerId) {
        try {
            List<Order> orders = orderService.getCustomerOrders(customerId);
            return ResponseEntity.ok().body(orders);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PutMapping("/update-status/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT', 'CUSTOMER', 'DRIVER')")
    public ResponseEntity<?> updateOrderStatus(Long orderDetailId) {
        return null;
    }

}
