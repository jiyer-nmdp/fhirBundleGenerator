package org.nmdp.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
@ComponentScan({"io.swagger.api", "org.nmdp"})
public class FhirBundleGenerator {
    public static void main(String[] args) {
        SpringApplication.run(FhirBundleGenerator.class, args);
    }
}
