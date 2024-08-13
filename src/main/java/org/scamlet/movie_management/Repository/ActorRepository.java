package org.scamlet.movie_management.Repository;

import org.scamlet.movie_management.Entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Actor findByName(String name);

    List<Actor> findByNameContaining(String name);

}
