package dev.yasint.toyland.dtos.request;

import dev.yasint.toyland.constants.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAddDTO {

    @NotBlank
    @Size(
            min = Validation.MIN_PRODUCT_NAME_LENGTH,
            max = Validation.MAX_PRODUCT_NAME_LENGTH
    )
    private String name;

    @NotBlank
    private double price;

    @NotBlank
    private int quantity;

}
