package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository  extends JpaRepository<Driver, Long> {
}
