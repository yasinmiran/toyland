package dev.yasint.toyland.models;

import dev.yasint.toyland.models.enumerations.EOrderStatus;
import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.Merchant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "fk_order_customer_id",
            referencedColumnName = "id"
    )
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "order_to_merchants_mapping",
            joinColumns = @JoinColumn(name = "mapping_order_id"),
            inverseJoinColumns = @JoinColumn(name = "mapping_merchant_id")
    )
    private Set<Merchant> merchants = new HashSet<>();

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "fk_cart_id",
            referencedColumnName = "id"
    )
    private Cart cart;

    private BigDecimal price = new BigDecimal(0);

    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    // Builder ===============>

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Customer customer;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private Cart cart;
        private BigDecimal price;
        private Set<Merchant> merchants;
        private EOrderStatus status;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder modifiedAt(LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public Builder cart(Cart cart) {
            this.cart = cart;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder status(EOrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder merchants(Set<Merchant> merchants) {
            this.merchants = merchants;
            return this;
        }

        public Builder merchant(Merchant merchant) {
            if (merchants == null) {
                this.merchants = new HashSet<>();
            }
            this.merchants.add(merchant);
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.setCustomer(customer);
            order.setCreatedAt(createdAt);
            order.setModifiedAt(modifiedAt);
            order.setCart(cart);
            order.setPrice(price);
            order.setMerchants(merchants);
            order.setStatus(status);
            return order;
        }

    }

}
