package aui.laboratorium_5.REST.Controllers;

import aui.laboratorium_5.Entities.SimplifiedGenre;
import aui.laboratorium_5.Repositories.SimplifiedGenreRepo;
import aui.laboratorium_5.Repositories.MovieRepo;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal/genres")
public class GenreSyncController {

    @Autowired
    private SimplifiedGenreRepo genreRepository;

    @Autowired
    private MovieRepo movieRepository;

    @PostMapping
    public ResponseEntity<Void> createLocalGenreCopy(@RequestBody SimplifiedGenre newGenre) {

        // Zapisujemy kopie w lokalnej bazie H2
        genreRepository.save(newGenre);

        // Zwrocenie 200 OK
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteGenreAndAssociatedMovies(@PathVariable UUID id) {

        // Sprawdzamy czy gatunek istnieje w lokalnej bazie H2
        if (!genreRepository.existsById(id)) {

            // Jeśli nie to po prostu zwracamy 200 OK
            return ResponseEntity.ok().build();

        }

        // Usuwamy wszystkie filmy powiazane z tym gatunkiem
        movieRepository.deleteAllByGenreId(id);

        // Usuń kopie gatunku z lokalnej bazy H2
        genreRepository.deleteById(id);

        // Zwrocenie 200 OK
        return ResponseEntity.ok().build();

    }

}