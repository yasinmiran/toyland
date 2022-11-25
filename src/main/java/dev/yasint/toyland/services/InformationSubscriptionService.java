package dev.yasint.toyland.services;

import dev.yasint.toyland.models.InformationSubscription;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.models.enumerations.Event;
import dev.yasint.toyland.models.Observer;

import java.util.List;

public interface InformationSubscriptionService {

    InformationSubscription addSubscription(User subject, User observer, Event event);

    void removeSubscription(User subject, User observer, Event event);

    List<Observer> getSubscribersByEvent(User subject, Event event);
}
