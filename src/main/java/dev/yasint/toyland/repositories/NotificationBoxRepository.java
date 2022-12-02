package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.NotificationBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationBoxRepository extends JpaRepository<NotificationBox, Long> {
}
