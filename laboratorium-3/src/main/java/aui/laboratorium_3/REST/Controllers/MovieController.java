package aui.laboratorium_3.REST.Controllers;

import aui.laboratorium_3.Entities.Genre;
import aui.laboratorium_3.Entities.Movie;
import aui.laboratorium_3.REST.Mappers.MovieMapper;
import aui.laboratorium_3.REST.Requests.GenreRequest;
import aui.laboratorium_3.REST.Requests.MovieRequest;
import aui.laboratorium_3.REST.Responses.GenreResponse;
import aui.laboratorium_3.REST.Responses.MovieResponse;
import aui.laboratorium_3.Services.GenreService;
import aui.laboratorium_3.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*
Implementation of rest controllers as @RestController for each entity class.

Controllers should utilize services for business operations and translate between
business entities and DTO objects.

Each controller must allow for creating/updating, deleting and reading elements and categories
as well as reading whole collections.

The resource addresses of the REST services must be well-formed and hierarchical.

Appropriate HTTP methods must be used as well as appropriate response codes.

Removing categories must remove all elements (this can be done with appropriate JPA configuration).

>>>> Elements are always added to category.
Client can both fetch all elements and elements from single category.

Remember that fetching elements from empty and not existing categories will result in two
different responses. Situations like adding element to not existing category must result in appropriate
response code. <<<<

(4 points)
*/

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieMapper movieMapper;

    // [ GET -> 200 OK ] - Wczytac wszystkie filmy
    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {

        // Pobieranie wszystkich filmow
        List<Movie> movies = movieService.getAllMovies();

        // Lista<Movie> -> List<MovieDTO>
        List<MovieResponse> responseList = movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());

        // Zwrocenie kodu 200 OK
        return ResponseEntity.ok(responseList);

    }

    // [ GET -> 200 OK ] - Wczytac szczegoly konkretnego filmu
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable UUID id) {

        // Pobieranie filmu
        Movie movie = movieService.getMovieById(id);

        // Movie -> MovieDTO
        MovieResponse responseDto = movieMapper.toDTO(movie);

        // Zwrocenie kodu 200 OK
        return ResponseEntity.ok(responseDto);

    }

    // [ PATCH -> 204 No content ] - Zmodyfikowac konkretny film
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMovie(
            @PathVariable UUID id,
            @RequestBody MovieRequest patchRequest
    ) {

        // Znalezienie filmu i jego modyfikacja
        movieService.patchMovie(id, patchRequest);

        // Zwrocenie kodu 204 No content
        return ResponseEntity.noContent().build();

    }

    // [ DELETE -> 204 No content ] - Usunac film
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable UUID id) {

        // Usuniecie filmu
        movieService.deleteMovie(id);

        // Zwrocenie kodu 204 No content
        return ResponseEntity.noContent().build();

    }

}
