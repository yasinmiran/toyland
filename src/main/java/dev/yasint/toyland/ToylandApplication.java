package dev.yasint.toyland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ToylandApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToylandApplication.class, args);
    }

}
