package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByNameAndMerchantId(String name, Long merchantId);

    List<Product> findAllByMerchantId(Long id);
}
