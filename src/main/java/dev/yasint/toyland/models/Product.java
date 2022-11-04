package dev.yasint.toyland.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long merchantId;

    private String name;

    private Double price;

    public Product(Long merchantId, String name, Double price) {
        this.merchantId = merchantId;
        this.name = name;
        this.price = price;
    }
}
