package dev.yasint.toyland.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDTO {

    private Long id;
    private String username;
    private List<String> roles;

}
