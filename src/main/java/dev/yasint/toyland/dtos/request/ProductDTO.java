package dev.yasint.toyland.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.yasint.toyland.constants.Validation;
import dev.yasint.toyland.dtos.Transformable;
import dev.yasint.toyland.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Transformable<Product> {

    @JsonProperty
    @NotBlank
    @Size(
            min = Validation.MIN_PRODUCT_NAME_LENGTH,
            max = Validation.MAX_PRODUCT_NAME_LENGTH
    )
    private String name;

    @JsonProperty
    private double price;

    @JsonProperty
    private int quantity;

    @Override
    public Product transform() {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        return product;
    }

}
