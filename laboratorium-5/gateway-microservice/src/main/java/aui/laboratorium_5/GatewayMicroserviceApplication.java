package aui.laboratorium_5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

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
