package dev.yasint.toyland.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.yasint.toyland.models.enumerations.EVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationUpdateDTO {

    @JsonProperty
    private Long merchantId;

    @JsonProperty
    private EVerificationStatus status;

}
