package aui.laboratorium_4.Services;

import aui.laboratorium_4.Entities.Movie;
import aui.laboratorium_4.REST.Requests.MovieRequest;
import aui.laboratorium_4.REST.Responses.NotFound;
import aui.laboratorium_4.Repositories.MovieRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {

    @Autowired
    private MovieRepo repository;

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
        return repository.findById(id).orElseThrow(() -> new NotFound("Nie znaleziono filmu o ID = " + id));
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

    @Transactional(readOnly = true)
    public List<Movie> getMoviesByGenreId(UUID genreId) {
        return repository.findByGenreId(genreId);
    }

}
