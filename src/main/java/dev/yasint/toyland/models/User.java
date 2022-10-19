package dev.yasint.toyland.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String address;
    private String payment;
    private String telephone;

    @ManyToMany
    private Collection<Role> roles = new ArrayList<>();

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public void mergeRole(Role role) {
        if (role != null && !roles.contains(role)) {
            roles.add(role);
        }
    }

}
