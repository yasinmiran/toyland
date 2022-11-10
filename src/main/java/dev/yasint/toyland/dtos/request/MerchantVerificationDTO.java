package dev.yasint.toyland.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MerchantVerificationDTO {

    @JsonProperty
    private String taxId;

}
