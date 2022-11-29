package dev.yasint.toyland.models.user;

import dev.yasint.toyland.models.Order;
import dev.yasint.toyland.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "fk_user_id",
            referencedColumnName = "id"
    )
    private User user;

    @OneToOne
    @JoinColumn(
            name = "fk_merchant_contact",
            referencedColumnName = "id"
    )
    private Contact contact;

    @OneToOne
    @JoinColumn(
            name = "fk_payment_detail_id",
            referencedColumnName = "id"
    )
    private Payment payment;

    @ManyToMany
    private Set<Order> orders = new HashSet<>();

    @OneToMany
    @JoinTable(
            name = "merchant_to_product_mapping",
            joinColumns = @JoinColumn(name = "merchant_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    private Boolean verified = false;

    private String taxId = null;

}
