package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Order;
import dev.yasint.toyland.models.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomer(Customer customer);

}
