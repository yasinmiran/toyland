package dev.yasint.toyland.services;

import dev.yasint.toyland.ToylandApplication;
import dev.yasint.toyland.configs.H2JpaConfiguration;
import dev.yasint.toyland.exceptions.ProfileInCompleteException;
import dev.yasint.toyland.models.Product;
import dev.yasint.toyland.models.user.Contact;
import dev.yasint.toyland.models.user.Merchant;
import dev.yasint.toyland.models.user.Payment;
import dev.yasint.toyland.models.user.User;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
public class OrderServiceImplTest {

    private static final String PRODUCT_1_NAME = "Product 1";
    private static final String PRODUCT_2_NAME = "Product 2";
    private static final int PRODUCT_1_INIT_QTY = 120;
    private static final double PRODUCT_1_PRICE = 14.99;
    private static final int PRODUCT_2_INIT_QTY = 840;
    private static final double PRODUCT_2_PRICE = 26.99;
    private static final int PRODUCT_1_CHECKING_OUT_QUANTITY = 4;
    private static final int PRODUCT_2_CHECKING_OUT_QUANTITY = 2;

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
    private ContactService contactService;
    @Autowired
    private PaymentService paymentService;

    @Before
    public void addSomeProductsAndSetupUser() {

        Optional<User> user = userRepository.findByUsername("merchant@toyland.com");
        assertThat(user).isPresent();
        Merchant merchant = merchantRepository.findMerchantByUser(user.get());
        assertThat(merchant).isNotNull();

        Product product1 = new Product();
        product1.setName(PRODUCT_1_NAME);
        product1.setQuantity(PRODUCT_1_INIT_QTY);
        product1.setPrice(PRODUCT_1_PRICE);

        Product product2 = new Product();
        product2.setName(PRODUCT_2_NAME);
        product2.setQuantity(PRODUCT_2_INIT_QTY);
        product2.setPrice(PRODUCT_2_PRICE);

        productService.saveAllProducts(
                user.get(),
                Arrays.asList(
                        product1,
                        product2
                )
        );

        assertThat(productRepository.count()).isEqualTo(2);
        assertThat(merchant.getProducts().size()).isEqualTo(2);

    }

    @Test(expected = ProfileInCompleteException.class)
    @WithUserDetails("customer@toyland.com")
    public void shouldFailIfContactInformationIsIncomplete() throws Exception {

        List<Product> all = productRepository.findAll();

        for (Product product : all) {
            cartService.addItemToCart(
                    product.getId(),
                    product.getName().equals(PRODUCT_1_NAME)
                            ? PRODUCT_1_CHECKING_OUT_QUANTITY
                            : PRODUCT_2_CHECKING_OUT_QUANTITY
            );
        }

        cartService.checkout();

    }

    @Test
    @WithUserDetails("customer@toyland.com")
    public void customerCanCheckoutTheCart() throws Exception {

        Contact contact = Contact.builder()
                .addressLine1("Address Line 1")
                .addressLine2("Address Line 2")
                .country("Ireland")
                .postcode("V95 32T0")
                .mobileNo("+3531234567890")
                .county("Limerick")
                .build();

        Payment payment = Payment.builder()
                .cvv("777")
                .expireDate(LocalDate.now())
                .name("YASIN MIRAN")
                .number("1234 4567 7890 1234")
                .build();

        contactService.updateContact(
                Common.getUserDetailsFromContext().getUser(),
                contact
        );

        paymentService.updatePayment(
                Common.getUserDetailsFromContext().getUser(),
                payment
        );

        List<Product> all = productRepository.findAll();

        for (Product product : all) {
            cartService.addItemToCart(
                    product.getId(),
                    product.getName().equals(PRODUCT_1_NAME)
                            ? PRODUCT_1_CHECKING_OUT_QUANTITY
                            : PRODUCT_2_CHECKING_OUT_QUANTITY
            );
        }

        cartService.checkout();

        Product product1 = productRepository.findByName(PRODUCT_1_NAME);

        assertThat(product1).isNotNull();
        assertThat(product1.getQuantity())
                .isEqualTo(PRODUCT_1_INIT_QTY - PRODUCT_1_CHECKING_OUT_QUANTITY);

        Product product2 = productRepository.findByName(PRODUCT_2_NAME);

        assertThat(product2).isNotNull();
        assertThat(product2.getQuantity())
                .isEqualTo(PRODUCT_2_INIT_QTY - PRODUCT_2_CHECKING_OUT_QUANTITY);

    }

}