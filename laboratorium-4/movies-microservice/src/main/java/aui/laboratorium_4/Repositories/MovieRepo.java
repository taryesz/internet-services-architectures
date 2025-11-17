package aui.laboratorium_4.Repositories;

import aui.laboratorium_4.Entities.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRepo extends JpaRepository<Movie, UUID> {

    List<Movie> findByTitle(String title);

    List<Movie> findByRating(int rating);

    List<Movie> findByPegi(boolean pegi);

    @Query("SELECT movie FROM Movie movie WHERE movie.genre.name = :genre")
    List<Movie> findByGenre(@Param("genre") String genre);

    List<Movie> findByGenreId(UUID genreId);

    void deleteAllByGenreId(UUID genreId);

}
