package org.scamlet.movie_management.Service;

import org.scamlet.movie_management.DTO.MovieDTO;
import org.scamlet.movie_management.Entity.Actor;
import org.scamlet.movie_management.Entity.Genre;
import org.scamlet.movie_management.Entity.Movie;
import org.scamlet.movie_management.Exception.MovieException;
import org.scamlet.movie_management.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDTO> getAllMovies() {
        try {
            List<Movie> list = movieRepository.findAll();
            return list.stream().map(this::convertToDTO).toList();
        } catch (Exception e) {
            throw new MovieException("Error getting movies : " + e.getMessage());
        }
    }

    public List<MovieDTO> getMoviesByName(String name) {
        try {
            List<Movie> movieList = movieRepository.findByTitleContaining(name);
            return movieList.stream().map(this::convertToDTO).toList();
        } catch (Exception e) {
            throw new MovieException("Error getting movies by name with " + name + " : " + e.getMessage());
        }

    }

    public MovieDTO convertToDTO(Movie movie) {
        MovieDTO newMovie = new MovieDTO();
        newMovie.setTitle(movie.getTitle());
        newMovie.setYear(movie.getYear());
        newMovie.setRating(movie.getRating());
        newMovie.setDirector(movie.getDirector().getName());
        newMovie.setActors(movie.getActors().stream().map(Actor::getName).collect(Collectors.toSet()));
        newMovie.setGenres(movie.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()));
        return newMovie;
    }

}
