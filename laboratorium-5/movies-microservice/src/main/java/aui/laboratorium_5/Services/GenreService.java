package aui.laboratorium_5.Services;

import aui.laboratorium_5.Entities.SimplifiedGenre;
import aui.laboratorium_5.REST.Responses.NotFound;
import aui.laboratorium_5.Repositories.SimplifiedGenreRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GenreService {

    @Autowired
    private SimplifiedGenreRepo repository;

    @Transactional(readOnly = true)
    public SimplifiedGenre getGenreById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFound("Nie znaleziono gatunku o ID = " + id));
    }

}
