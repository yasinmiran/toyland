package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerByUser(User user);

}
