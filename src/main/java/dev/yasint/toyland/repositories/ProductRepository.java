package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.user.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);

    Product findByIdAndMerchant(Long productId, Merchant merchant);
}
