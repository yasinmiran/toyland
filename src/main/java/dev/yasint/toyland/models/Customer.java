package dev.yasint.toyland.models;

import dev.yasint.toyland.models.enumerations.ECustomerTier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fk_user_id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "fk_user_contact_id",
            referencedColumnName = "id")
    private Contact contact;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "fk_payment_details_id",
            referencedColumnName = "id")
    private Payment payment;

    @OneToOne
    @JoinColumn(
            name = "fk_user_cart_id",
            referencedColumnName = "id")
    private Cart cart;

    @Enumerated(EnumType.STRING)
    private ECustomerTier tier;

}
