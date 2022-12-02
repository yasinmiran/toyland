package dev.yasint.toyland.services;

import dev.yasint.toyland.models.enumerations.Event;
import dev.yasint.toyland.models.Observer;
import dev.yasint.toyland.models.Subject;

public interface ObserverService {
    void update(Observer observer, Event event, Subject subject);
}
