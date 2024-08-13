package org.scamlet.movie_management.Service;

import org.scamlet.movie_management.DTO.GenreDTO;
import org.scamlet.movie_management.Entity.Genre;
import org.scamlet.movie_management.Entity.Movie;
import org.scamlet.movie_management.Exception.GenreException;
import org.scamlet.movie_management.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDTO> getAllGenres() {
        try {
            List<Genre> genres = genreRepository.findAll();
            return genres.stream().map(this::convertGenreToGenreDTO).toList();
        } catch (Exception e) {
            throw new GenreException("Error getting genres : " + e.getMessage());
        }
    }

    public List<GenreDTO> getGenresByName(String name) {
        try {
            List<Genre> genres = genreRepository.findByNameContaining(name);
            return genres.stream().map(this::convertGenreToGenreDTO).toList();
        } catch (Exception e) {
            throw new GenreException("Error getting genres by name with " + name + " : " + e.getMessage());
        }
    }

    public GenreDTO convertGenreToGenreDTO(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setName(genre.getName());
        genreDTO.setMovies(genre.getMovies().stream().map(Movie::getTitle).collect(Collectors.toSet()));
        return genreDTO;
    }
}
