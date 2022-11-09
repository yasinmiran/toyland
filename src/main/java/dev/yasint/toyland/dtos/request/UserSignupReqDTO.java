package dev.yasint.toyland.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.yasint.toyland.constants.Validation;
import dev.yasint.toyland.models.Contact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupReqDTO {

    @JsonProperty
    private boolean isMerchant;

    @JsonProperty
    @Size(
            max = Validation.MAX_PERSON_NAME_LENGTH,
            min = Validation.MIN_PERSON_NAME_LENGTH
    )
    private String name;

    @JsonProperty
    @Email
    @Size(max = Validation.MAX_EMAIL_LENGTH)
    private String username;

    @JsonProperty
    @Size(
            max = Validation.MAX_PASSWORD_LENGTH,
            min = Validation.MIN_PASSWORD_LENGTH
    )
    private String password;

    private Contact contact;

}
