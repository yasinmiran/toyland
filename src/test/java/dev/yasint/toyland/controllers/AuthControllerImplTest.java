package dev.yasint.toyland.controllers;

import dev.yasint.toyland.ToylandApplication;
import dev.yasint.toyland.configs.H2JpaConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {ToylandApplication.class, H2JpaConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class AuthControllerImplTest {

    @Autowired
    private AuthControllerImpl controller;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(port).isGreaterThan(0);
        assertThat(restTemplate).isNotNull();
        log.debug(getUrl());
    }

    private String getUrl() {
        return "http://localhost:" + port + "/api/auth";
    }

    @Test
    public void canCustomerLogin() {

    }

    @Test
    public void canAdminLogin() {

    }

    @Test
    public void canMerchantLogin() {

    }

}
