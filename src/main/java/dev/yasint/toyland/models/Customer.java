package dev.yasint.toyland.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private Long userId;

    @Size(max = 32)
    private String mobileNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "user_addresses",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
    private Location address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "user_payments",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_id"))
    private Payment payment;

}
