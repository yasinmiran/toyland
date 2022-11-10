package dev.yasint.toyland.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fk_user_id")
    @JsonIgnore
    private User user;

    /**
     * By default, the cart is empty. Hence, an empty
     * array list is initialized.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "cart_items",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_item_id")
    )
    private List<CartItem> items = new ArrayList<>();

    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CartItem {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private Long productId;

        private Integer quantity;

    }

    public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    }

}
