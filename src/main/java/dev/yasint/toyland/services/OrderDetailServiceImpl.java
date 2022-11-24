package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.models.Order;
import dev.yasint.toyland.models.OrderDetail;
import dev.yasint.toyland.repositories.OrderDetailRepository;
import dev.yasint.toyland.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService{

    private final OrderDetailRepository orderDetailRepository;

    private final OrderRepository orderRepository;

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(ResourceNotFoundException::new);
        return orderDetailRepository.findAllByOrder(order);
    }
}
