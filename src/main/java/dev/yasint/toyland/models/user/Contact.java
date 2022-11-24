package dev.yasint.toyland.models.user;

import dev.yasint.toyland.models.contracts.Completable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String addressLine1;
    private String addressLine2;
    private String county;
    private String country;
    private String postcode;
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
