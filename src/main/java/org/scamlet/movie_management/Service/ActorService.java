package org.scamlet.movie_management.Service;

import org.scamlet.movie_management.DTO.ActorDTO;
import org.scamlet.movie_management.Entity.Actor;
import org.scamlet.movie_management.Entity.Movie;
import org.scamlet.movie_management.Exception.ActorException;
import org.scamlet.movie_management.Repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorService {
    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<ActorDTO> getAllActors() {
        try {
            List<Actor> actorList = actorRepository.findAll();
            return actorList.stream().map(this::convertToDTO).toList();
        } catch (Exception e) {
            throw new ActorException("Error while getting actor list\n" + e.getMessage());
        }
    }

    public List<ActorDTO> getActorsByName(String name) {
        try {
            List<Actor> actorList = actorRepository.findByNameContaining(name);
            return actorList.stream().map(this::convertToDTO).toList();
        } catch (Exception e) {
            throw new ActorException("Error getting actors by name with " + name + " : " + e.getMessage());
        }
    }

    public ActorDTO convertToDTO(Actor actor) {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setName(actor.getName());
        actorDTO.setMovies(actor.getMovies().stream().map(Movie::getTitle).collect(Collectors.toSet()));
        return actorDTO;
    }
}
