package org.scamlet.movie_management.Repository;

import org.scamlet.movie_management.Entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    Director findByName(String name);

    List<Director> findByNameContaining(String name);
}
