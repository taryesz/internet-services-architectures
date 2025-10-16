package compose.project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Genre thriller = new Genre.Builder()
                .name("Thriller")
                .actionIndex(100)
                .build();

        Genre comedy = new Genre.Builder()
                .name("Comedy")
                .actionIndex(10)
                .build();

        Movie movie0 = new Movie.Builder()
                .name("Movie-A")
                .rating(10)
                .pegi(false)
                .genre(thriller)
                .build();

        Movie movie1 = new Movie.Builder()
                .name("Movie-B")
                .rating(5)
                .pegi(true)
                .genre(comedy)
                .build();

        Movie movie2 = new Movie.Builder()
                .name("Movie-C")
                .rating(7)
                .pegi(true)
                .genre(comedy)
                .build();

        Movie movie3 = new Movie.Builder()
                .name("Movie-C")
                .rating(7)
                .pegi(true)
                .genre(comedy)
                .build();



        System.out.println("""
                
                2. At the application start-up, collection of categories filled with elements (remember
                about two way relationships) should be created. At this moment there is no need for
                user interaction. Objects should be created in code using appropriate creation
                methods. Then using nested for each lambda print all categories and elements in
                original order.
                """);

        thriller.addMovie(movie0);
        comedy.addMovie(movie1);
        comedy.addMovie(movie2);
        comedy.addMovie(movie3);

        List<Genre> genres = new ArrayList<>();
        genres.add(thriller);
        genres.add(comedy);

        genres.forEach(genre -> {
            System.out.println("Genre: " + genre.getName());
            genre.getMovies().forEach(m -> System.out.println("-> " + m));
        });



        System.out.println("""
                
                3. Using single Stream API pipeline create Set collection all elements (from all categories).
                Then using second pipeline print it.
                """);

        Set<Movie> genresStream = genres.stream()
                .flatMap(genre -> genre.getMovies().stream())   // kazda liste filmow kazdego gatunku zamieniamy na stream i dodajemy to setu
                .collect(Collectors.toSet());

        genresStream.stream().forEach(System.out::println);



        System.out.println("""
                
                4. Using single Stream API pipeline filter elements collection created earlier (by one selected 
                property), then sort it (by one different property) and print it.
                """);

        genresStream.stream()
                .filter(m -> m.isPegi())    // pokazac tylko przyjazne dla dzieci filmy
                .sorted()                          // posortowac wg ratingu
                .forEach(System.out::println);



        System.out.println("""
                
                5. Using single Stream API pipeline transform elements collection created earlier into
                steam of DTO objects, then sort them using natural order and collect them into
                List collection. Then using second pipeline print it.
                """);

        List<MovieDTO> DTOList = genresStream.stream()
                .map(m -> new MovieDTO(m.getName(), m.getRating(), m.getGenre().getName()))
                .sorted()
                .toList();

        DTOList.stream().forEach(System.out::println);



        System.out.println("""
                
                6. Using serialization mechanism store collection of categories in the binary file, then
                read it from it and print call categories with elements.
                """);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("genres.dat"))) {
            objectOutputStream.writeObject(genres);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Genre> load = null;

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("genres.dat"))) {
            load = (List<Genre>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (load != null) {
            load.forEach(genre -> {
                System.out.println("Genre: " + genre.getName());
                genre.getMovies().forEach(m -> System.out.println("-> " + m));
            });
        }



        System.out.println("""
                
                7. Using Stream API parallel pipelines with custom thread pool execute some task on
                each category. For example task can be printing each collection elements with
                intervals using Thread.sleep() to simulate workload. Observer result with
                different custom pool sizes. For thread pool use ForkJoinPool Remember about
                closing the thread pool.
                """);

        ForkJoinPool pool = new ForkJoinPool(2);

        try {

            pool.submit(() ->
                    genres.parallelStream().forEach(genre -> {

                        System.out.println(Thread.currentThread().getName() + " is processing Genre: " + genre.getName());

                        genre.getMovies().forEach(movie -> {
                            try {
                                Thread.sleep(1000);
                                System.out.println(Thread.currentThread().getName() + " -> " + movie.getName() + " in " + genre.getName());
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        });
                    })
            ).get();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            pool.shutdown();

            boolean isTerminated = pool.awaitTermination(5, TimeUnit.SECONDS);

            if (!isTerminated) {
                System.out.println("Pool did not terminate - forcing termination...");
                pool.shutdownNow();
            }
            else {
                System.out.println("Pool is shutting down...");
                System.exit(0);
            }

            System.out.println("Pool forcibly terminated.");

        }

    }

}