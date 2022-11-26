package dev.yasint.toyland.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yasint.toyland.models.enumerations.Event;
import dev.yasint.toyland.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "fk_user_subject_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private User subject;

    @ManyToOne
    @JoinColumn(
            name = "fk_user_observer_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private User observer;

    @Enumerated(EnumType.STRING)
    private Event event;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private User subject;
        private User observer;
        private Event event;

        public Builder subject(User subject) {
            this.subject = subject;
            return this;
        }

        public Builder observer(User observer) {
            this.observer = observer;
            return this;
        }

        public Builder event(Event event) {
            this.event = event;
            return this;
        }

        public InformationSubscription build() {
            InformationSubscription informationSubscription = new InformationSubscription();
            informationSubscription.setSubject(subject);
            informationSubscription.setObserver(observer);
            informationSubscription.setEvent(event);
            return informationSubscription;
        }

    }
}
