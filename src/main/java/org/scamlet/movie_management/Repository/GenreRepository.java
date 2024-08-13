package org.scamlet.movie_management.Repository;

import org.scamlet.movie_management.Entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);

    List<Genre> findByNameContaining(String name);
}
