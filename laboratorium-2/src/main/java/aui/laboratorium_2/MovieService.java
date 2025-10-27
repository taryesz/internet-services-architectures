package aui.laboratorium_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
Implementation of service for each business class as Spring @Service. Each
service should utilize repository instance provided by the container. At this moment
each service should delegate repository methods. At this point this can look as
services does not introduce any added value but this decomposition can be crucial
in developing much more complex applications.

(2 points)
*/

@Service
public class MovieService {

    @Autowired
    private MovieRepo repository;

    public List<Movie> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    public List<Movie> findByRating(int rating) {
        return repository.findByRating(rating);
    }

    public List<Movie> findByPegi(boolean pegi) {
        return repository.findByPegi(pegi);
    }

    public List<Movie> findByGenre(String genre) {
        return repository.findByGenre(genre);
    }

    public void save(Movie movie) {
        repository.save(movie);
    }

    public List<Movie> showAllMovies() {
        return repository.findAll();
    }

    public void delete(Movie movie) {
        repository.delete(movie);
    }

}
