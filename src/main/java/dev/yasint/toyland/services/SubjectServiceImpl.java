package dev.yasint.toyland.services;

import dev.yasint.toyland.models.enumerations.Event;
import dev.yasint.toyland.models.Observer;
import dev.yasint.toyland.models.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService{

    private final ObserverService observerService;

    @Override
    public void notifyObservers(Subject subject, Event event) {
        List<Observer> observers = subject.getObservers(event);

        observers.forEach(observer -> observerService.update(observer, event, subject));
    }
}
