package dev.yasint.toyland.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yasint.toyland.models.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(
        name = "orders",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "orderNo")
        }
)
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "fk_customer_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private Customer customer;

    private String orderNo;

    private String description;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Customer customer;
        private String orderNo;
        private String description;

        private LocalDateTime createdAt;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder orderNo(String orderNo) {
            this.orderNo = orderNo;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.setCustomer(customer);
            order.setOrderNo(orderNo);
            order.setDescription(description);
            order.setCreatedAt(createdAt);
            order.setOrderDetails(new ArrayList<>());
            return order;
        }

    }
}
