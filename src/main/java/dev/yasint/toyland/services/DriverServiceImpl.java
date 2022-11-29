package dev.yasint.toyland.services;

import dev.yasint.toyland.models.Location;
import dev.yasint.toyland.models.Order;
import dev.yasint.toyland.models.user.Driver;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.DriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    private final LocationService locationService;

    @Override
    public Driver getDriverByLowestDeliveries() {
        Driver driver = driverRepository.findByLowestDeliveries();
        if (driver == null) {
            driver = driverRepository.findOneRandom();
        }
        return driver;
    }

    @Override
    public Driver updateDriverLocation(User driverUser, Long lat, Long longitude) {
        Driver driver = driverRepository.findDriverByUser(driverUser);
        Location location = locationService.findByLatAndLong(lat, longitude);
        driver.setLocation(location);
        return driverRepository.save(driver);
    }

    @Override
    public boolean isDriverAnOrderOwner(User driverUser, Order order) {
        Driver driver = driverRepository.findDriverByUser(driverUser);
        return driver.getDeliveries().contains(order);
    }


}
