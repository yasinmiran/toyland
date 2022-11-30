package dev.yasint.toyland.services;

import dev.yasint.toyland.models.Order;
import dev.yasint.toyland.models.user.Driver;
import dev.yasint.toyland.models.user.User;

public interface DriverService {

    Driver getDriverByLowestDeliveries();

    Driver updateDriverLocation(User driverUser, Long lat, Long longitude);

    boolean isDriverAnOrderOwner(User driverUser, Order order);
}
