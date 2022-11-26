package dev.yasint.toyland.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yasint.toyland.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationBox {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "fk_user_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private User user;

    private String message;
}
