package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<Cart.CartItem, Long> {

}
