package aui.laboratorium_3.Services;

import aui.laboratorium_3.Entities.Genre;
import aui.laboratorium_3.Repositories.GenreRepo;
import aui.laboratorium_3.REST.Responses.NotFound;
import aui.laboratorium_3.REST.Requests.GenreRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GenreService {

    @Autowired
    private GenreRepo repository;

    @Transactional(readOnly = true)
    public List<Genre> findByActionIndex(int actionIndex) {
        return repository.findByActionIndex(actionIndex);
    }

    @Transactional
    public void save(Genre genre) {
        repository.save(genre);
    }

    @Transactional(readOnly = true)
    public List<Genre> showAllGenres() {
        return repository.findAll();
    }

    // REST:

    @Transactional
    public Genre createGenre(Genre genre) {
        return repository.save(genre);
    }

    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Genre getGenreById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFound("Nie znaleziono gatunku o ID=" + id));
    }

    @Transactional
    public void patchGenre(UUID id, GenreRequest patchRequest) {

        Genre existingGenre = getGenreById(id);

        if (patchRequest.getName() != null) {
            existingGenre.setName(patchRequest.getName());
        }

        if (patchRequest.getActionIndex() != null) {
            existingGenre.setActionIndex(patchRequest.getActionIndex());
        }

        repository.save(existingGenre);
    }

    @Transactional
    public void deleteGenre(UUID id) {
        repository.delete(getGenreById(id));
    }

    @Transactional(readOnly = true)
    public Genre findGenreByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NotFound("Nie znaleziono gatunku o nazwie: " + name));
    }

}
