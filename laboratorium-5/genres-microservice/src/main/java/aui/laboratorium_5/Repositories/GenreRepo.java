package aui.laboratorium_5.Repositories;

import aui.laboratorium_5.Entities.Genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepo extends JpaRepository<Genre, UUID> {

    @Query("SELECT genre FROM Genre genre WHERE genre.actionIndex = :actionIndex")
    List<Genre> findByActionIndex(@Param("actionIndex") int actionIndex);

    Optional<Genre> findByName(String name);

}
