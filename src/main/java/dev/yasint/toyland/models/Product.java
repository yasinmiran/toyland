package dev.yasint.toyland.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "fk_merchant_id",
            referencedColumnName = "id"
    )
    private Merchant merchant;

    private String name;

    private double price;

    private int quantity;

    public Product(Merchant merchant, String name, double price) {
        this.merchant = merchant;
        this.name = name;
        this.price = price;
    }
}
