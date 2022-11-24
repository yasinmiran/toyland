package dev.yasint.toyland.models.verification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yasint.toyland.models.user.Merchant;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.models.enumerations.EVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "fk_merchant_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private Merchant merchant;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(
            name = "fk_admin_id",
            referencedColumnName = "id"
    )
    private User authority;

    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private EVerificationStatus status;

}
