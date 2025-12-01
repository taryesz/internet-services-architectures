package aui.laboratorium_5.Repositories;

import aui.laboratorium_5.Entities.SimplifiedGenre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SimplifiedGenreRepo extends JpaRepository<SimplifiedGenre, UUID> {

    Optional<SimplifiedGenre> findByName(String name);

}
