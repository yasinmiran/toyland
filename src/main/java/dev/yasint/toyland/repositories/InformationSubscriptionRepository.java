package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.InformationSubscription;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.models.enumerations.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationSubscriptionRepository extends JpaRepository<InformationSubscription, Long> {

    List<InformationSubscription> findAllBySubjectAndEvent(User subject, Event event);

    List<InformationSubscription> findBySubjectAndObserverAndEvent(User subject, User observer, Event event);
}
