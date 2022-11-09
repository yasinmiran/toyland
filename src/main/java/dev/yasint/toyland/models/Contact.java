package dev.yasint.toyland.models;

import dev.yasint.toyland.constants.Validation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contacts")
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String addressLine1;

    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String addressLine2;

    @NotBlank
    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String county;

    @NotBlank
    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String country;

    @NotBlank
    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String postcode;

    @NotBlank
    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String mobileNo;

}
