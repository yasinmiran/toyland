package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findLocationByLatAndLon(Long lat, Long longitude);
}
