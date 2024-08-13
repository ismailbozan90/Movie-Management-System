package org.scamlet.movie_management.Repository;

import org.scamlet.movie_management.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContaining(String name);
}
