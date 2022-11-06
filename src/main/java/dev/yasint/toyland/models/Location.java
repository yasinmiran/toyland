package dev.yasint.toyland.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 32)
    private String addressLine1;

    @NotBlank
    @Size(max = 32)
    private String addressLine2;

    @NotBlank
    @Size(max = 32)
    private String county;

    @NotBlank
    @Size(max = 32)
    private String country;

    @NotBlank
    @Size(max = 32)
    private String postcode;

}
