package dev.yasint.toyland.services;

import dev.yasint.toyland.ToylandApplication;
import dev.yasint.toyland.configs.H2JpaConfiguration;
import dev.yasint.toyland.dtos.request.ProductDTO;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.Merchant;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.CustomerRepository;
import dev.yasint.toyland.repositories.MerchantRepository;
import dev.yasint.toyland.repositories.ProductRepository;
import dev.yasint.toyland.repositories.UserRepository;
import dev.yasint.toyland.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {ToylandApplication.class, H2JpaConfiguration.class}
)
@ActiveProfiles("test")
@Transactional
public class CartServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void addSomeProducts() {

        Optional<User> user = userRepository.findByUsername("merchant@toyland.com");
        assertThat(user).isPresent();
        Merchant merchant = merchantRepository.findMerchantByUser(user.get());
        assertThat(merchant).isNotNull();

        ProductDTO product1 = new ProductDTO();
        product1.setName("Toy Car");
        product1.setQuantity(100);
        product1.setPrice(14.99);

        ProductDTO product2 = new ProductDTO();
        product2.setName("Spider Man Figurine");
        product2.setQuantity(890);
        product2.setPrice(26.99);

        productService.saveAllProducts(
                user.get(),
                Arrays.asList(
                        product1.transform(),
                        product2.transform()
                )
        );

        assertThat(productRepository.count()).isEqualTo(2);
        assertThat(merchant.getProducts().size()).isEqualTo(2);

    }

    @Test
    @WithUserDetails("customer@toyland.com")
    public void canCustomerAddItemToCart() throws Exception {

        List<Product> all = productRepository.findAll();
        log.info("found {} products", all.size());

        for (Product product : all) {
            cartService.addItemToCart(product.getId(), 1);
        }

        Customer customer = customerRepository.findCustomerByUser(
                Common.getUserDetailsFromContext().getUser()
        );

        assertThat(customer.getCart().getItems().size()).isEqualTo(2);

    }

    @Test
    @WithUserDetails("customer@toyland.com")
    public void canCustomerRemoveItemFromCart() throws Exception {

        List<Product> all = productRepository.findAll();
        log.info("found {} products", all.size());

        for (Product product : all)
            cartService.addItemToCart(product.getId(), 1);

        Customer customer = customerRepository.findCustomerByUser(
                Common.getUserDetailsFromContext().getUser()
        );

        cartService.removeItemFromCart(all.get(0).getId());

        assertThat(customer.getCart().getItems().size()).isEqualTo(1);

    }

    @Test
    @WithUserDetails("customer@toyland.com")
    public void canCustomerEmptyCart() throws Exception {

        List<Product> all = productRepository.findAll();
        log.info("found {} products", all.size());

        for (Product product : all)
            cartService.addItemToCart(product.getId(), 1);

        Customer customer = customerRepository.findCustomerByUser(
                Common.getUserDetailsFromContext().getUser()
        );

        cartService.emptyCart();

        assertThat(customer.getCart().getItems().size()).isEqualTo(0);

    }

}