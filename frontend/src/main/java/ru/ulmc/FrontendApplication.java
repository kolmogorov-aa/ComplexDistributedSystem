package ru.ulmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring boot web application initializer.
 */
@EnableJms
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class,
        scanBasePackages = "ru.ulmc")
public class FrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }

}
