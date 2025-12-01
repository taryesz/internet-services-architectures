package aui.laboratorium_5.REST.Controllers;

import aui.laboratorium_5.Entities.SimplifiedGenre;
import aui.laboratorium_5.Entities.Movie;
import aui.laboratorium_5.REST.Mappers.MovieMapper;
import aui.laboratorium_5.REST.Requests.MovieRequest;
import aui.laboratorium_5.REST.Responses.MovieResponse;
import aui.laboratorium_5.Services.GenreService;
import aui.laboratorium_5.Services.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private GenreService genreService;

    // [ GET -> 200 OK ] - Wczytac wszystkie filmy
    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies(
            @RequestParam(name = "genreId", required = false) UUID genreId
    ) {

        List<Movie> movies;

        // Jesli nalezy wczytac filmy o konkretnym gatunku
        if (genreId != null) {
            movies = movieService.getMoviesByGenreId(genreId);
        }
        // Jesli nalezy wczytac wszystkie filmy
        else {
            movies = movieService.getAllMovies();
        }

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

    // [ POST -> 201 Created ] - Utworzyc nowy film DLA KONKRETNEGO GATUNKU
    @PostMapping
    public ResponseEntity<MovieResponse> createMovieOfGenre(@RequestBody MovieRequest createRequest) {

        UUID genreId = createRequest.getGenreId();

        // Pobieranie gatunku
        SimplifiedGenre genre = genreService.getGenreById(genreId);

        // MovieDTO -> Movie
        Movie newMovie = movieMapper.toMovie(createRequest);

        // Ustawienie gatunku filmu
        newMovie.setGenre(genre);

        // Zapisywanie filmu
        Movie savedMovie = movieService.createMovie(newMovie);

        // Budowanie sciezki
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/movies/{id}")
                .buildAndExpand(savedMovie.getId())
                .toUri();

        // Zwrocenie 201 Created
        return ResponseEntity.created(location).body(movieMapper.toDTO(savedMovie));

    }

}
