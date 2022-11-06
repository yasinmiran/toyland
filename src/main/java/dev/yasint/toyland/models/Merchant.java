package dev.yasint.toyland.models;

import dev.yasint.toyland.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {

//    @Autowired
//    ProductRepository productRepository;

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

//    private void getProducts() {
//
//        List<Product> products = productRepository
//                .findAllByMerchantId(this.id);
//    }
}
