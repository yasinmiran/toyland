package dev.yasint.toyland.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.yasint.toyland.constants.Validation;
import dev.yasint.toyland.dtos.Transformable;
import dev.yasint.toyland.models.user.Contact;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ContactUpdateDTO implements Transformable<Contact> {

    @JsonProperty
    @NotBlank
    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String addressLine1;

    @JsonProperty
    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String addressLine2;

    @JsonProperty
    @NotBlank
    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String county;

    @JsonProperty
    @NotBlank
    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String country;

    @JsonProperty
    @NotBlank
    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String postcode;

    @JsonProperty
    @NotBlank
    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String mobileNo;

    @Override
    public Contact transform() {
        return Contact.builder()
                .addressLine1(addressLine1)
                .addressLine2(addressLine2)
                .county(county)
                .country(country)
                .postcode(postcode)
                .mobileNo(mobileNo)
                .build();
    }

}
