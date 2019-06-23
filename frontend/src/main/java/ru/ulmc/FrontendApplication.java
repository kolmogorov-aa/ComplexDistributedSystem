package ru.ulmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.io.IOException;

/**
 * Spring boot web application initializer.
 */
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
//@EnableJpaRepositories
public class FrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }


    //@Bean
    //public ServletWebServerFactory servletContainer() {
    //    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    //    return tomcat;
    //}
//
//
  // @Override
  // protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
  //     return application.sources(FrontendApplication.class);
  // }

}
