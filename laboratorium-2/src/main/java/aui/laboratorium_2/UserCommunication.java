package aui.laboratorium_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

/*
Implementation of command line runner as Spring @Component. The runner should
communicate with the user using standard input and output streams and allow for

- listing available commands
- listing all categories
- listing all elements
- adding new element (with category selection)
- delete existing element
- stopping the application

The runner should utilize services instances provided by the container.

(1 point)
*/

@Component
@Order(4)
public class UserCommunication implements CommandLineRunner {

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;

    private Scanner scanner;

    public UserCommunication() {
        this.scanner = new Scanner(System.in);
    }

    private void printMenu() {

        System.out.println("Dostepne polecenia:");

        System.out.println("(1) - Wyswietl wszystkie gatunki");
        System.out.println("(2) - Wyswietl wszystkie filmy");
        System.out.println("(3) - Dodaj nowy film");
        System.out.println("(4) - Usun film");
        System.out.println("(5) - Wyjscie z aplikacji");

    }

    @Override
    public void run(String... args) {

        printMenu();

        boolean running = true;
        while (running) {
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    List<Genre> genres = genreService.showAllGenres();
                    for (int i = 0; i < genres.size(); i++) {
                        System.out.println(genres.get(i).getName());
                    }
                }
                case "2" -> {
                    List<Movie> movies = movieService.showAllMovies();
                    for (int i = 0; i < movies.size(); i++) {
                        System.out.println(movies.get(i).getTitle());
                    }
                }
                case "3" -> addNewMovie();
                case "4" -> deleteMovie();
                case "5" -> {
                    running = false;
                    System.out.println("Zamykam aplikacje...");
                }
                default -> System.out.println("Nieprawidlowy input");
            }
        }
        scanner.close();

    }

    private void addNewMovie() {

        printMovieMenu();

        String title = "";
        int rating = 0;
        boolean pegi = false;
        Genre genre = null;

        boolean running = true;
        while (running) {
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    title = scanner.nextLine();
                }
                case "2" -> {
                    rating = readRating();
                }
                case "3" -> {
                    pegi = readPegi();
                }
                case "4" -> {
                    genre = readGenre();
                }
                case "5" -> {
                    running = false;
                    movieService.save(new Movie.Builder().name(title).genre(genre).rating(rating).pegi(pegi).build());
                    System.out.println("Zapisuje nowy film...");
                }
                case "6" -> {
                    running = false;
                    System.out.println("Zamykam aplikacje...");
                }
                default -> System.out.println("Nieprawidlowy input");
            }
        }

    }

    private void printMovieMenu() {

        System.out.println("Dodaj nowy film:");

        System.out.println("(1) - Tytul");
        System.out.println("(2) - Ocena");
        System.out.println("(3) - PEGI");
        System.out.println("(4) - Gatunek:");
        System.out.println("(5) - ZAPISZ");
        System.out.println("(6) - WYJSCIE");

    }

    private int readRating() {
        Scanner scanner = new Scanner(System.in);
        int number = -1;

        while (number < 0 || number > 10) {
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if (number < 0 || number > 10) {
                    System.out.println("Nieprawidlowy input");
                }
            } else {
                System.out.println("Nieprawidlowy input");
                scanner.next();
            }
        }

        return number;

    }

    private boolean readPegi() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("tak")) {
                return true;
            }
            else if (input.equalsIgnoreCase("nie")) {
                return false;
            }
            else {
                System.out.println("Nieprawidlowy input");
            }
        }
    }

    private Genre readGenre() {

        List<Genre> genres = genreService.showAllGenres();

        System.out.print("Wybierz gatunek:");

        for (int i = 0; i < genres.size(); i++) {
            System.out.println("(" + (i + 1) + ") - " + genres.get(i).getName());
        }

        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= genres.size()) {
                    return genres.get(choice - 1);
                } else {
                    System.out.println("Nieprawidlowy input");
                }
            } else {
                System.out.println("Nieprawidlowy input");
                scanner.next();
            }
        }
    }

    private void deleteMovie() {

        List<Movie> movies = movieService.showAllMovies();

        System.out.println("Wybierz film:");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println("(" + (i + 1) + ") - " + movies.get(i).getTitle());
        }

        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= movies.size()) {
                    Movie movie = movies.get(choice - 1);
                    movieService.delete(movie);
                    System.out.println("Usunieto");
                    break;
                } else {
                    System.out.println("Nieprawidlowy input");
                }
            } else {
                System.out.println("Nieprawidlowy input");
                scanner.next();
            }
        }

    }

}
