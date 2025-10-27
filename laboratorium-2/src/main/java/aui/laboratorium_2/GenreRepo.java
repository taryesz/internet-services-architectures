package aui.laboratorium_2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/*
Implementation of JPA repositories for each business class as Spring
@Repository. Repository for elements should allow for querying them by
category.

(2 points)
*/

@Repository
public interface GenreRepo extends JpaRepository<Genre, UUID> {

    @Query("SELECT genre FROM Genre genre WHERE genre.actionIndex = :actionIndex")
    List<Genre> findByActionIndex(@Param("actionIndex") int actionIndex);

}
