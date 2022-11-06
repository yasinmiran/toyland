package dev.yasint.toyland.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductRequestDTO {

    @NotNull
    @JsonProperty("product_id")
    private Long productId;

}
