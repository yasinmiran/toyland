package dev.yasint.toyland.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
            name = "fk_merchant_contact_id",
            referencedColumnName = "id"
    )
    private Contact contact;

    @OneToMany
    @JoinTable(
            name = "merchant_products",
            joinColumns = @JoinColumn(name = "merchant_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> product = new ArrayList<>();

    private Boolean verified = false;

    private String taxId = null;

}
