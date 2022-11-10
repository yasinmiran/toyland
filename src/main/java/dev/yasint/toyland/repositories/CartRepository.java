package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Cart;
import dev.yasint.toyland.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findCartByUser(User user);

}
