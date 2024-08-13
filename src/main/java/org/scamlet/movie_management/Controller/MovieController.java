package org.scamlet.movie_management.Controller;

import org.scamlet.movie_management.DTO.MovieDTO;
import org.scamlet.movie_management.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movieDTOList = movieService.getAllMovies();
        if (movieDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(movieDTOList, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<MovieDTO>> getMoviesByName(@PathVariable("name") String name) {
        List<MovieDTO> movieDTOList = movieService.getMoviesByName(name);
        if (movieDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(movieDTOList, HttpStatus.OK);
    }

}
