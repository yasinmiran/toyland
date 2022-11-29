package dev.yasint.toyland.services;

import dev.yasint.toyland.models.Location;
import dev.yasint.toyland.repositories.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{

    private final LocationRepository locationRepository;

    @Override
    public Location findByLatAndLong(Long lat, Long longitude) {
        return locationRepository.findLocationByLatAndLon(lat, longitude);
    }
}
