package dev.yasint.toyland;

import dev.yasint.toyland.configs.H2JpaConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ToylandApplication.class, H2JpaConfiguration.class})
@ActiveProfiles("test")
class ToylandApplicationTests {

    @Test
    void contextLoads() {

    }

}
