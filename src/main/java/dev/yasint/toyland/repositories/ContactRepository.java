package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
