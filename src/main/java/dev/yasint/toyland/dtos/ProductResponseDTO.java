package dev.yasint.toyland.dtos;

import dev.yasint.toyland.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Long id;
    private Long merchant_id;
    private String name;
    private Double price;

}
