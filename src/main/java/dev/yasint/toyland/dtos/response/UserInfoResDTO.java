package dev.yasint.toyland.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResDTO {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String username;

    @JsonProperty
    private List<String> roles;

}
