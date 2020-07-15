package com.wave.greenboxrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* TODO
*  1. Entities (Order, Item)
*  2. Controllers
*  3. Join Entity (Position)
*  4. Map Order->Position->Item
*  6. Cascade deletion on order->positions
*  5. Composite key in Position class
*  6. Store date in Order class
*  7. Complete order
*  8. Response Entities
*  9. Throw and handle errors when item/order not found from repository
*  10. Correct calculation errors, round prices
*  11. Create services
*  12. Implement CollectionType{WEIGHT, AMOUNT, BOTH}
*  13. Create and return DTOs
*/

@SpringBootApplication
public class GreenBoxRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenBoxRestApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer configureCors() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
