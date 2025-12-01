package aui.laboratorium_5.Services;

import aui.laboratorium_5.Entities.Genre;
import aui.laboratorium_5.REST.Requests.GenreRequest;
import aui.laboratorium_5.REST.Responses.NotFound;
import aui.laboratorium_5.Repositories.GenreRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

/*
Implementation of inter-applications event-based communication. When removing
existing or adding new category the elements management application should be
notified in order to remove elements or create new simplified category record in the
database. As event communication REST services can be used.

(3 points)
*/

@Service
public class GenreService {

    @Autowired
    private GenreRepo repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String movieServiceUrl = "http://localhost:8082/internal/genres";

    @Transactional
    public Genre createGenre(Genre genre) {

        // Zapisujemy gatunek w lokalnej bazie H2
        Genre savedGenre = repository.save(genre);

        try {
            // Wysylamy powiadomienie do movie-microservice o dodaniu nowego gatunku
            // Movie-microservice posiada prywatny endpoint ktory sluzy do zapisania na nim
            // nowych gatunkow
            restTemplate.postForEntity(movieServiceUrl, savedGenre, Void.class);
        } catch (Exception e) {
            System.err.println("Nie udalo sie powiadomic movie-microservice o nowym gatunku.");
        }

        // Zwrocenie zapisanego obiektu
        return savedGenre;

    }

    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {

        // Zwrocenie wszystkiego co sie znajduje w lokalnej bazie H2
        return repository.findAll();

    }

    @Transactional(readOnly = true)
    public Genre getGenreById(UUID id) {

        // Zwrocenie konkretnego gatunku z lokalnej bazy H2
        return repository.findById(id).orElseThrow(() -> new NotFound("Nie znaleziono gatunku o ID = " + id + "."));

    }

    @Transactional
    public void patchGenre(UUID id, GenreRequest patchRequest) {

        // Znajdujemy gatunek w lokalnej bazie H2
        Genre existingGenre = getGenreById(id);

        // Jesli chcemy zmienic nazwe...
        if (patchRequest.getName() != null) {
            existingGenre.setName(patchRequest.getName());
        }

        // Jesli chcemy zmienic indeks akcji...
        if (patchRequest.getActionIndex() != null) {
            existingGenre.setActionIndex(patchRequest.getActionIndex());
        }

        // Zapisujemy zmiany
        repository.save(existingGenre);

    }

    @Transactional
    public void deleteGenre(UUID id) {

        // Usuwamy gatunek z lokalnej bazy H2
        repository.deleteById(id);

        try {
            // Wysylamy powiadomienie do movie-microservice o usunieciu gatunku
            // Movie-microservice posiada prywatny endpoint ktory sluzy do zapisania na nim
            // nowych gatunkow - mozemy je rowniez stamtad usunac
            restTemplate.delete(movieServiceUrl + "/" + id);
        } catch (Exception e) {
            System.err.println("Nie udalo sie powiadomic movie-microservice o usunieciu gatunku.");
        }
    }

}
