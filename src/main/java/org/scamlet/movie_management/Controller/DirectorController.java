package org.scamlet.movie_management.Controller;

import org.scamlet.movie_management.DTO.DirectorDTO;
import org.scamlet.movie_management.Service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }


    @GetMapping
    public ResponseEntity<List<DirectorDTO>> getAllDirectors() {
        List<DirectorDTO> directorDTOList = directorService.getAllDirectors();
        if (directorDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(directorDTOList, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<DirectorDTO>> getDirectorsByName(@PathVariable String name) {
        List<DirectorDTO> directorDTOList = directorService.getDirectorsByName(name);
        if (directorDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(directorDTOList, HttpStatus.OK);
    }

}
