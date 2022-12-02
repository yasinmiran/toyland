package dev.yasint.toyland.services;

import dev.yasint.toyland.models.enumerations.Event;
import dev.yasint.toyland.models.Subject;

public interface SubjectService {

    void notifyObservers(Subject subject, Event event);
}
