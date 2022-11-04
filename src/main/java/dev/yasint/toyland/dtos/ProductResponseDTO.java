package dev.yasint.toyland.dtos;

import dev.yasint.toyland.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO extends Product {

    private Long id;
    private Long merchantId;
    private String name;
    private Double price;

}
