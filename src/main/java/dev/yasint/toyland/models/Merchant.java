package dev.yasint.toyland.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private String name;

    @ManyToOne
    private Location location;

    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "merchant_products",
//            joinColumns = @JoinColumn(name = "merchant_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JoinTable(name = "product",
            joinColumns = @JoinColumn(name = "merchantId"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Product> products;
}
