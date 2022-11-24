package dev.yasint.toyland.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yasint.toyland.models.enumerations.EOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "order_detail")
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "fk_order_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private Order order;

    private String description;

    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    @ManyToOne
    @JoinColumn(
            name = "fk_product_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private Product product;

    private Integer quantity;

    private LocalDateTime modifiedAt;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Order order;
        private String description;

        private EOrderStatus status;

        private Product product;

        private Integer quantity;

        private LocalDateTime modifiedAt;

        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder status(EOrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder modifiedAt(LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public OrderDetail build() {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setDescription(description);
            orderDetail.setStatus(status);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(quantity);
            orderDetail.setModifiedAt(modifiedAt);
            return orderDetail;
        }

    }
}
