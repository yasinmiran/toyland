package dev.yasint.toyland.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.yasint.toyland.dtos.Transformable;
import dev.yasint.toyland.models.user.Payment;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PaymentUpdateDTO implements Transformable<Payment> {

    @JsonProperty
    @NotBlank
    private String name;

    @JsonProperty
    @NotBlank
    private String number;

    @JsonProperty
    @NotBlank
    private String cvv;

    @JsonProperty
    @NotBlank
    private String expireDate;

    @Override
    public Payment transform() {
        return Payment.builder()
                .name(name)
                .number(number)
                .cvv(cvv)
                .expireDate(LocalDate.parse(expireDate))
                .build();
    }

}
