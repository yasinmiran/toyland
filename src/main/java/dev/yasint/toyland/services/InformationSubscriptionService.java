package dev.yasint.toyland.services;

import dev.yasint.toyland.models.InformationSubscription;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.models.enumerations.Event;

public interface InformationSubscriptionService {

    InformationSubscription addSubscription(User subject, User observer, Event event);

    void removeSubscription(User subject, User observer, Event event);
}
