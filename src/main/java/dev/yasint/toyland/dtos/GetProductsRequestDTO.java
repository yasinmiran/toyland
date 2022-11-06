package dev.yasint.toyland.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductsRequestDTO {

    @NotNull
    @JsonProperty("merchant_id")
    private Long merchantId;

}
