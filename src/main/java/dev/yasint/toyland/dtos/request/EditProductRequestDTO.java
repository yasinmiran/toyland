package dev.yasint.toyland.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditProductRequestDTO {

    @NotBlank
    @JsonProperty("product_id")
    private Long productId;

    @NotBlank
    @JsonProperty("product_info")
    private AddProductRequestDTO productInfo;

}
