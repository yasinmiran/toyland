package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.CreateOrderDTO;
import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.models.Order;
import dev.yasint.toyland.models.OrderDetail;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.services.OrderDetailService;
import dev.yasint.toyland.services.OrderService;
import dev.yasint.toyland.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    private final OrderDetailService orderDetailService;

    @Override
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderDTO body) {
        User user = Common.getUserDetailsFromContext().getUser();
        Order order = orderService.saveOrder(user, body.getProducts(), body.getDescription());
        return ResponseEntity.ok().body(order);
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
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT', 'CUSTOMER')")
    public ResponseEntity<?> getOrderDetails(@PathVariable("id") Long orderId) {
        try {
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);
            return ResponseEntity.ok().body(orderDetails);
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
