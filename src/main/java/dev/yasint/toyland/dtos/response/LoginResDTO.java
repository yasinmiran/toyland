package dev.yasint.toyland.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDTO {

    @JsonProperty
    private String token;

    @JsonProperty
    private UserInfoResDTO user;

}
