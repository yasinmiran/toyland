package dev.yasint.toyland.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty
    private int quantity;

    @JsonProperty
    private String description;
}
