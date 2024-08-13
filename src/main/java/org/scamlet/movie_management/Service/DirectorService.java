package org.scamlet.movie_management.Service;

import org.scamlet.movie_management.DTO.DirectorDTO;
import org.scamlet.movie_management.Entity.Director;
import org.scamlet.movie_management.Entity.Movie;
import org.scamlet.movie_management.Exception.DirectorException;
import org.scamlet.movie_management.Repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<DirectorDTO> getAllDirectors() {
        try {
            List<Director> directors = directorRepository.findAll();
            return directors.stream().map(this::convertToDTO).toList();
        } catch (Exception e) {
            throw new DirectorException("Error while getting director list\n" + e.getMessage());
        }

    }

    public List<DirectorDTO> getDirectorsByName(String name) {
        try {
            List<Director> directors = directorRepository.findByNameContaining(name);
            return directors.stream().map(this::convertToDTO).toList();
        } catch (Exception e) {
            throw new DirectorException("Error getting actors by name with " + name + " : " + e.getMessage());
        }
    }

    public DirectorDTO convertToDTO(Director director) {
        DirectorDTO directorDTO = new DirectorDTO();
        directorDTO.setName(director.getName());
        directorDTO.setMovies(director.getMovies().stream().map(Movie::getTitle).collect(Collectors.toSet()));
        return directorDTO;
    }
}
