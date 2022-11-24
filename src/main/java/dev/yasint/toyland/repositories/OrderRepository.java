package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Customer;
import dev.yasint.toyland.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
            value = "SELECT o.id, o.description, o.order_no, o.fk_customer_id FROM ORDERS o " +
                    "INNER JOIN ORDER_DETAIL od on od.fk_order_id = o.id " +
                    "INNER JOIN PRODUCTS p on p.fk_merchant_id = ?1 " +
                    "WHERE od.fk_product_id = p.id " +
                    "GROUP BY o.id"
            , nativeQuery = true)
    List<Order> findAllByMerchantId(Long merchantId);

    List<Order> findAllByCustomer(Customer customer);
}
