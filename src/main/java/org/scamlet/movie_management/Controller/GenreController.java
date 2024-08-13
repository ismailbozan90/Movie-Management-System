package org.scamlet.movie_management.Controller;

import org.scamlet.movie_management.DTO.GenreDTO;
import org.scamlet.movie_management.Service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;

    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<GenreDTO> genreDTOList = genreService.getAllGenres();
        if (genreDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(genreDTOList, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<GenreDTO>> getGenresByName(@PathVariable String name) {
        List<GenreDTO> genreDTOList = genreService.getGenresByName(name);
        if (genreDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(genreDTOList, HttpStatus.OK);
    }
}
