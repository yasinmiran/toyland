package dev.yasint.toyland.services;

import dev.yasint.toyland.models.enumerations.Event;
import dev.yasint.toyland.models.Observer;
import dev.yasint.toyland.models.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ObserverServiceImpl implements ObserverService{

    private final NotificationBoxService notificationBoxService;

    @Override
    public void update(Observer observer, Event event, Subject subject) {
        if (event.equals(Event.NEW_PRODUCT)) {
            notificationBoxService.createNewNotification(observer.getId(), "A new product has been added by " + subject.getName());
        }
    }
}
