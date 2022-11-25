package dev.yasint.toyland.models.user;

import dev.yasint.toyland.models.InformationSubscription;
import dev.yasint.toyland.models.Observer;
import dev.yasint.toyland.models.Subject;
import dev.yasint.toyland.models.enumerations.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yasint.toyland.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        }
)
public class User implements Subject, Observer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * {@code username} is essentially the email address of the
     * user. We want this to be unique hence, {@code uniqueConstraints}
     * were added as a DDL pre-validation. See @Table annotation.
     */
    private String username;

    @JsonIgnore
    private String password;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user_subject_id", referencedColumnName = "id")
    private List<InformationSubscription> informationSubscription;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Observer> getObservers(Event event) {
        List<Observer> observers = new ArrayList<>();
        this.getInformationSubscription().forEach(subscription -> {
            if (subscription.getEvent().equals(event)) {
                observers.add(subscription.getObserver());
            }
        });

        return observers;
    }
}
