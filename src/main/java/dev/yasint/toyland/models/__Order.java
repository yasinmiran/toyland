package dev.yasint.toyland.models;

import dev.yasint.toyland.models.user.Customer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class __Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "fk_customer_id",
            referencedColumnName = "id"
    )
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fk_cart_id",
            referencedColumnName = "id"
    )
    private Cart cart;

    private LocalDateTime orderedAt;
    private String paymentReference;
    private double totalPrice;

}
