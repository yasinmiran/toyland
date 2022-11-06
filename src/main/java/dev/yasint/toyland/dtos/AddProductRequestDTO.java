package dev.yasint.toyland.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequestDTO {

    @NotNull
    private String name;

    @NotNull
    private Double price;

}
