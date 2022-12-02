package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.user.Driver;
import dev.yasint.toyland.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query(
            value = "SELECT dom.driver_id, count(dom.driver_id) as occurrence " +
                    "FROM driver_to_order_mapping dom " +
                    "INNER JOIN orders o on o.id = dom.delivery_order_id and o.status = 'COMPLETED' " +
                    "GROUP BY dom.driver_id " +
                    "ORDER BY occurrence asc " +
                    "LIMIT 1",
            nativeQuery = true)
    Driver findByLowestDeliveries();

    Driver findDriverByUser(User driverUser);

    @Query(
            value = "SELECT * " +
                    "FROM driver " +
                    "LIMIT 1",
            nativeQuery = true)
    Driver findOneRandom();
}
