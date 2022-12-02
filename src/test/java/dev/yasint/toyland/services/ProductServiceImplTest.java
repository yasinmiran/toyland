package dev.yasint.toyland.services;

import dev.yasint.toyland.ToylandApplication;
import dev.yasint.toyland.configs.H2JpaConfiguration;
import dev.yasint.toyland.dtos.request.ProductDTO;
import dev.yasint.toyland.exceptions.ResourceAccessException;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.ProductRepository;
import dev.yasint.toyland.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {ToylandApplication.class, H2JpaConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@Transactional
public class ProductServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void canMerchantSaveProduct() throws UnableToSatisfyException, ResourceAccessException {

        Optional<User> user = userRepository.findByUsername("merchant@toyland.com");
        assertThat(user).isPresent();

        ProductDTO body = new ProductDTO();
        body.setName("Product Name");
        body.setPrice(1000);
        body.setQuantity(10000);

        Product snapshot = productService.saveProduct(user.get(), body.transform());
        Product saved = productService.findProductById(snapshot.getId());

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getPrice()).isEqualTo(1000);
        assertThat(saved.getQuantity()).isEqualTo(10000);
        assertThat(saved.getName()).isEqualTo("Product Name");
        assertThat(productRepository.count()).isEqualTo(1);

    }

    @Test(expected = UnableToSatisfyException.class)
    public void onlyUsersWithMerchantRoleCanAddProducts() throws UnableToSatisfyException {
        Optional<User> user = userRepository.findByUsername("customer@toyland.com");
        assertThat(user).isPresent();
        productService.saveProduct(user.get(), new ProductDTO().transform());
    }

}