package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Customer;
import dev.yasint.toyland.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerByUser(User user);

}
