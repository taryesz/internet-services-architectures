package aui.laboratorium_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
Implementation of JPA repositories for each business class as Spring
@Repository. Repository for elements should allow for querying them by
category.

(2 points)
*/

@Component
@Order(2)
public class TestRepo implements CommandLineRunner {

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private GenreRepo genreRepo;

    @Override
    public void run(String... args) {

        System.out.println("\nTest dzialania przeszukiwania w repozytoriach:");

        System.out.println("    findByTitle('Inception') -> " + movieRepo.findByTitle("Inception"));
        System.out.println("    findByGenre('Action') -> " + movieRepo.findByGenre("Action"));
        System.out.println("    findByRating(8) -> " + movieRepo.findByRating(8));
        System.out.println("    findByIsPEGI(true) -> " + movieRepo.findByPegi(true));

        System.out.println("    findByActionIndex(100) -> " + genreRepo.findByActionIndex(100));

    }
}