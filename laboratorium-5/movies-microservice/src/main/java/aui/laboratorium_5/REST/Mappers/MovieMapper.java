package aui.laboratorium_5.REST.Mappers;

import aui.laboratorium_5.Entities.Movie;
import aui.laboratorium_5.REST.Requests.MovieRequest;
import aui.laboratorium_5.REST.Responses.MovieResponse;

import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    // MovieDTO -> Movie
    public Movie toMovie(MovieRequest request) {
        return new Movie.Builder().title(request.getTitle()).rating(request.getRating()).pegi(request.getPegi())
                .build();
    }

    // Movie -> MovieDTO
    public MovieResponse toDTO(Movie movie) {
        return new MovieResponse.Builder().id(movie.getId()).title(movie.getTitle())
                .rating(movie.getRating()).pegi(movie.isPegi())
                .build();
    }

}