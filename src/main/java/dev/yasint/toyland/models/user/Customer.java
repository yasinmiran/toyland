package dev.yasint.toyland.models.user;

import dev.yasint.toyland.models.Cart;
import dev.yasint.toyland.models.enumerations.ECustomerTier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "fk_user_id",
            referencedColumnName = "id"
    )
    private User user;

    @OneToOne
    @JoinColumn(
            name = "fk_contact_id",
            referencedColumnName = "id"
    )
    private Contact contact;

    @OneToOne
    @JoinColumn(
            name = "fk_payment_detail_id",
            referencedColumnName = "id"
    )
    private Payment payment;

    @OneToOne
    @JoinColumn(
            name = "fk_user_cart_id",
            referencedColumnName = "id")
    private Cart cart;

    @Enumerated(EnumType.STRING)
    private ECustomerTier tier;

}
