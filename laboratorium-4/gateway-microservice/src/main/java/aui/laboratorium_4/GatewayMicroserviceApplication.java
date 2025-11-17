package aui.laboratorium_4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/*
New Spring Boot application based on Spring Cloud Gateway should be created.
The application should contain routing rules for category and elements
management applications.

(2 point).
*/

@SpringBootApplication
public class GatewayMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMicroserviceApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Kierowac do mikroserwisu gatunkow
                .route("genres-route", r -> r

                        // Przechwytujemy zadania na ta sciezke
                        .path("/api/genres/**")

                        // ... przekierowujemy je na adres mikroserwisu ganunkow
                        .uri("http://localhost:8081"))

                // Kierowac do mikroserwisu filmow
                .route("movies-route", r -> r

                        // Przechwytujemy zadania na ta sciezke
                        .path("/api/movies/**")

                        // ... przekierowujemy je na adres mikroserwisu filmow
                        .uri("http://localhost:8082"))

                .build();

    }

}
