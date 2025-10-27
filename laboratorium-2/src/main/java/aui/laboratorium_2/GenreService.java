package aui.laboratorium_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
Implementation of service for each business class as Spring @Service. Each
service should utilize repository instance provided by the container. At this moment
each service should delegate repository methods. At this point this can look as
services does not introduce any added value but this decomposition can be crucial
in developing much more complex applications.

(2 points)
*/

@Service
public class GenreService {

    @Autowired
    private GenreRepo repository;

    public List<Genre> findByActionIndex(int actionIndex) {
        return repository.findByActionIndex(actionIndex);
    }

    public void save(Genre genre) {
        repository.save(genre);
    }

    public List<Genre> showAllGenres() {
        return repository.findAll();
    }

}
