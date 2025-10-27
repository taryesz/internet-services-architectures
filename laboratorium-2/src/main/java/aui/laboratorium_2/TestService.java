package aui.laboratorium_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
Implementation of service for each business class as Spring @Service. Each
service should utilize repository instance provided by the container. At this moment
each service should delegate repository methods. At this point this can look as
services does not introduce any added value but this decomposition can be crucial
in developing much more complex applications.

(2 points)
*/

@Component
@Order(3)
public class TestService implements CommandLineRunner {

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;

    @Override
    public void run(String... args) {

        System.out.println("\nTest dzialania przeszukiwania w repozytoriach PRZEZ SERWIS:");

        System.out.println("    findByTitle('Inception') -> " + movieService.findByTitle("Inception"));
        System.out.println("    findByGenre('Action') -> " + movieService.findByGenre("Action"));
        System.out.println("    findByRating(8) -> " + movieService.findByRating(8));
        System.out.println("    findByIsPEGI(true) -> " + movieService.findByPegi(true));

        System.out.println("    findByActionIndex(100) -> " + genreService.findByActionIndex(100));

    }
}