package aui.laboratorium_3.REST.Controllers;

import aui.laboratorium_3.Entities.Genre;
import aui.laboratorium_3.Entities.Movie;
import aui.laboratorium_3.REST.Mappers.GenreMapper;
import aui.laboratorium_3.REST.Mappers.MovieMapper;
import aui.laboratorium_3.REST.Requests.GenreRequest;
import aui.laboratorium_3.REST.Requests.MovieRequest;
import aui.laboratorium_3.REST.Responses.MovieResponse;
import aui.laboratorium_3.Services.GenreService;
import aui.laboratorium_3.REST.Responses.GenreResponse;
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

Elements are always added to category.
Client can both fetch all elements and elements from single category.

Remember that fetching elements from empty and not existing categories will result in two
different responses. Situations like adding element to not existing category must result in appropriate
response code.

(4 points)
*/

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieMapper movieMapper;

    // [ POST -> 201 Created ] - Utworzyc nowy gatunek
    @PostMapping
    public ResponseEntity<GenreResponse> addGenre(@RequestBody GenreRequest createRequest) {

        // GenreDTO -> Genre
        Genre newGenre = genreMapper.toGenre(createRequest);

        // Tworzenie Genre, zapisywanie w repozytorium i zwrocic
        Genre savedGenre = genreService.createGenre(newGenre);

        // Budowanie sciezki do zapisanego i zwroconego Genre
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedGenre.getId())
                .toUri();

        // Zwrocenie kodu 201 Created + DTO odpowiedzi w body
        return ResponseEntity.created(location).body(genreMapper.toDTO(savedGenre));

    }

    // [ GET -> 200 OK ] - Wczytac wszystkie gatunki
    @GetMapping
    public ResponseEntity<List<GenreResponse>> getAllGenres() {

        // Pobieranie wszystkich gatunkow
        List<Genre> genres = genreService.getAllGenres();

        // Lista<Genre> -> List<GenreDTO>
        List<GenreResponse> responseList = genres.stream()
                .map(genreMapper::toDTO)
                .collect(Collectors.toList());

        // Zwrocenie kodu 200 OK
        return ResponseEntity.ok(responseList);

    }

    // [ GET -> 200 OK ] - Wczytac konkretny gatunek
    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> getGenreById(@PathVariable UUID id) {

        // Pobieranie gatunku
        Genre genre = genreService.getGenreById(id);

        // Genre -> GenreDTO
        GenreResponse responseDto = genreMapper.toDTO(genre);

        // Zwrocenie kodu 200 OK
        return ResponseEntity.ok(responseDto);

    }

    // [ PATCH -> 204 No content ] - Zmodyfikowac konkretny gatunek
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateGenre(
            @PathVariable UUID id,
            @RequestBody GenreRequest patchRequest
    ) {

        // Znalezienie gatunku i jego modyfikacja
        genreService.patchGenre(id, patchRequest);

        // Zwrocenie kodu 204 No content
        return ResponseEntity.noContent().build();

    }

    // [ DELETE -> 204 No content ] - Usunac gatunek
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable UUID id) {

        // Usuniecie gatunku
        genreService.deleteGenre(id);

        // Zwrocenie kodu 204 No content
        return ResponseEntity.noContent().build();

    }

    // [ POST -> 201 Created ] - Utworzyc nowy film DLA KONKRETNEGO GATUNKU
    @PostMapping("/{genreId}/movies")
    public ResponseEntity<MovieResponse> createMovieOfGenre(
            @PathVariable UUID genreId,
            @RequestBody MovieRequest createRequest) {

        // Pobieranie gatunku
        Genre genre = genreService.getGenreById(genreId);

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

    // [ GET -> 200 OK ] - Wczytac wszystkie filmy O KONKRETNYM GATUNKU
    @GetMapping("/{genreId}/movies")
    public ResponseEntity<List<MovieResponse>> getAllMoviesOfGenre(@PathVariable UUID genreId) {

        // Pobieranie gatunku
        Genre genre = genreService.getGenreById(genreId);

        // Pobieranie wszystkich filmow O PEWNYM GATUNKU
        List<Movie> movies = movieService.getMoviesByGenre(genre.getName());

        // Lista<Movie> -> List<MovieDTO>
        List<MovieResponse> responseList = movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());

        // Zwrocenie kodu 200 OK
        return ResponseEntity.ok(responseList);

    }

}
