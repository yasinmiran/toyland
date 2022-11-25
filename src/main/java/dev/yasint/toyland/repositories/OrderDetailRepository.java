package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Order;
import dev.yasint.toyland.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query(
            value = "SELECT * FROM ORDER_DETAIL od INNER JOIN PRODUCT p on p.merchant_id = ?1 WHERE od.product_id = p.id"
    , nativeQuery = true)
    List<OrderDetail> findAllByMerchantId(Long merchantId);

    List<OrderDetail> findAllByOrder(Order order);
}
