package dev.yasint.toyland.services;

import dev.yasint.toyland.models.InformationSubscription;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.models.enumerations.Event;
import dev.yasint.toyland.repositories.InformationSubscriptionRepository;
import dev.yasint.toyland.models.Observer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InformationSubscriptionServiceImpl implements InformationSubscriptionService {

    private final InformationSubscriptionRepository informationSubscriptionRepository;

    @Override
    public InformationSubscription addSubscription(User subject, User observer, Event event) {
        InformationSubscription subscription = InformationSubscription.builder()
                .subject(subject)
                .observer(observer)
                .event(event)
                .build();
        return informationSubscriptionRepository.save(subscription);
    }

    @Override
    public void removeSubscription(User subject, User observer, Event event) {
        List<InformationSubscription> subscription = informationSubscriptionRepository
                .findBySubjectAndObserverAndEvent(subject, observer, event);

        informationSubscriptionRepository.deleteAll(subscription);
    }

    @Override
    public List<Observer> getSubscribersByEvent(User subject, Event event) {
        List<InformationSubscription> informationSubscriptions = informationSubscriptionRepository
                .findAllBySubjectAndEvent(subject, event);

        List<Observer> observers = new ArrayList<>();

        informationSubscriptions.forEach(subscription -> observers.add(subscription.getObserver()));

        return observers;
    }
}
