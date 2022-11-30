package dev.yasint.toyland.services;

import dev.yasint.toyland.models.Location;

public interface LocationService {

    Location findByLatAndLong(Long lat, Long longitude);
}
