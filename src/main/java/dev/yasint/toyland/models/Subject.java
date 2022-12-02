package dev.yasint.toyland.models;

import dev.yasint.toyland.models.enumerations.Event;

import java.util.List;

public interface Subject {

    String getName();

    Long getId();

    List<Observer> getObservers(Event event);
}
