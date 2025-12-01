package aui.laboratorium_5.REST.Controllers;

import aui.laboratorium_5.Entities.Genre;
import aui.laboratorium_5.REST.Mappers.GenreMapper;
import aui.laboratorium_5.REST.Requests.GenreRequest;
import aui.laboratorium_5.REST.Responses.GenreResponse;
import aui.laboratorium_5.Services.GenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreMapper genreMapper;

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

}
