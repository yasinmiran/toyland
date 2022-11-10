package dev.yasint.toyland.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MerchantVerificationDTO {

    @JsonProperty
    private String taxId;

}
