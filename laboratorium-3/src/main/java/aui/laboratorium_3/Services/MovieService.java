package aui.laboratorium_3.Services;

import aui.laboratorium_3.Entities.Movie;
import aui.laboratorium_3.REST.Requests.MovieRequest;
import aui.laboratorium_3.REST.Responses.NotFound;
import aui.laboratorium_3.Repositories.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {

    @Autowired
    private MovieRepo repository;

    @Transactional(readOnly = true)
    public List<Movie> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Transactional(readOnly = true)
    public List<Movie> findByRating(int rating) {
        return repository.findByRating(rating);
    }

    @Transactional(readOnly = true)
    public List<Movie> findByPegi(boolean pegi) {
        return repository.findByPegi(pegi);
    }

    @Transactional(readOnly = true)
    public List<Movie> findByGenre(String genre) {
        return repository.findByGenre(genre);
    }

    @Transactional
    public void save(Movie movie) {
        repository.save(movie);
    }

    @Transactional(readOnly = true)
    public List<Movie> showAllMovies() {
        return repository.findAll();
    }

    @Transactional
    public void delete(Movie movie) {
        repository.delete(movie);
    }

    // REST:

    @Transactional
    public Movie createMovie(Movie movie) {
        return repository.save(movie);
    }

    @Transactional(readOnly = true)
    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Movie getMovieById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFound("Nie znaleziono filmu o ID=" + id));
    }

    @Transactional(readOnly = true)
    public List<Movie> getMoviesByGenre(String genre) {
        return repository.findByGenre(genre);
    }

    @Transactional
    public void patchMovie(UUID id, MovieRequest patchRequest) {

        Movie existingMovie = getMovieById(id);

        if (patchRequest.getTitle() != null) {
            existingMovie.setTitle(patchRequest.getTitle());
        }

        if (patchRequest.getRating() != null) {
            existingMovie.setRating(patchRequest.getRating());
        }

        if (patchRequest.getPegi() != null) {
            existingMovie.setPegi(patchRequest.getPegi());
        }

        repository.save(existingMovie);

    }

    @Transactional
    public void deleteMovie(UUID id) {
        repository.delete(getMovieById(id));
    }

}
