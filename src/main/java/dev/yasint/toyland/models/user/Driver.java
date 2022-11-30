package dev.yasint.toyland.models.user;

import dev.yasint.toyland.models.Location;
import dev.yasint.toyland.models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "fk_user_id",
            referencedColumnName = "id"
    )
    private User user;

    private String licensePlate;

    /**
     * A driver's persistent contact. This can only be
     * changed by an admin.
     */
    @OneToOne
    @JoinColumn(
            name = "fk_driver_contact",
            referencedColumnName = "id"
    )
    private Contact contact;

    @OneToOne
    @JoinColumn(
            name = "fk_driver_location",
            referencedColumnName = "id"
    )
    private Location location;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "driver_to_order_mapping",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "delivery_order_id")
    )
    private Set<Order> deliveries = new HashSet<>();

}
