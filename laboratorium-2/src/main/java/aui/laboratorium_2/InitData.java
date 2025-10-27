package aui.laboratorium_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
Implementation of example data initializer launched automatically after start as
Spring @Component. The initializer should utilize services instances provided by
the container.

(1 point)
*/

@Component
@Order(1)
public class InitData implements CommandLineRunner {

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private GenreRepo genreRepo;

    @Override
    public void run(String... args) {

        System.out.println("\nTest automatycznego zapisu danych do repozytoriow:");

        Genre action = new Genre.Builder().name("Action").actionIndex(100).build();
        Genre comedy = new Genre.Builder().name("Comedy").actionIndex(5).build();

        genreRepo.save(action);
        genreRepo.save(comedy);

        movieRepo.save(new Movie.Builder().name("Inception").genre(action).rating(9).pegi(true).build());
        movieRepo.save(new Movie.Builder().name("The Matrix").genre(action).rating(10).pegi(false).build());
        movieRepo.save(new Movie.Builder().name("The Hangover").genre(comedy).rating(8).pegi(true).build());

        System.out.println("    " + movieRepo.findAll());
        System.out.println("    " + genreRepo.findAll());

    }

}
