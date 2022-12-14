package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceAccessException;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.enumerations.Event;
import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.models.user.Merchant;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.MerchantRepository;
import dev.yasint.toyland.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;
    private final SubjectService subjectService;

    @Override
    public Product findProductById(Long id) throws ResourceAccessException {
        return productRepository
                .findById(id)
                .orElseThrow(ResourceAccessException::new);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean isProductOwnedByMerchant(
            Long productId,
            Long userId
    ) throws ResourceAccessException {
        Product product = findProductById(productId);
        return Objects.equals(product.getMerchant().getUser().getId(), userId);
    }

    @Override
    public Product saveProduct(User owner, Product partial) throws UnableToSatisfyException {
        if (owner.hasRole(ERole.MERCHANT)) {
            Merchant merchant = merchantRepository.findMerchantByUser(owner);
            partial.setMerchant(merchant);
            subjectService.notifyObservers(owner, Event.NEW_PRODUCT);
            return productRepository.save(partial);
        }
        throw new UnableToSatisfyException();
    }

    @Override
    public List<Product> saveAllProducts(User owner, List<Product> products) {
        Merchant merchant = merchantRepository.findMerchantByUser(owner);
        products.forEach(product -> product.setMerchant(merchant));
        merchant.setProducts(products);
        return productRepository.saveAll(products);
    }

    @Override
    public Product editProduct(User owner, Product partial, Long productId) throws UnableToSatisfyException, ResourceAccessException {
        if (owner.hasRole(ERole.MERCHANT)) {
            Merchant merchant = merchantRepository.findMerchantByUser(owner);
            Product product = productRepository.findByIdAndMerchant(productId, merchant);
            if (product == null) {
                throw new ResourceAccessException();
            }
            partial.setMerchant(merchant);
            partial.setId(productId);
            subjectService.notifyObservers(owner, Event.EDIT_PRODUCT);
            return productRepository.save(partial);
        }
        throw new UnableToSatisfyException();
    }

}
