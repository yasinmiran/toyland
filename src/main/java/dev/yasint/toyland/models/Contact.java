package dev.yasint.toyland.models;

import dev.yasint.toyland.constants.Validation;
import dev.yasint.toyland.models.contracts.Completable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contacts")
@Builder
public class Contact implements Completable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String addressLine1;

    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String addressLine2;

    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String county;

    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String country;

    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String postcode;

    @Size(max = Validation.MAX_GENERIC_STRING_LENGTH)
    private String mobileNo;

    @Override
    public boolean isCompleted() {
        return (addressLine1 != null && !addressLine1.isBlank())
                && (country != null && !country.isBlank())
                && (country != null && !country.isBlank())
                && (postcode != null && !postcode.isBlank())
                && (mobileNo != null && !mobileNo.isBlank() && mobileNo.startsWith("+"));
    }

}
