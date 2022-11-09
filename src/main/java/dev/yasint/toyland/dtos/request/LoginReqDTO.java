package dev.yasint.toyland.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.yasint.toyland.constants.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginReqDTO {

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

}
