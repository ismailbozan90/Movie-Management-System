package org.scamlet.movie_management.Controller;

import org.scamlet.movie_management.DTO.ActorDTO;
import org.scamlet.movie_management.Service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<List<ActorDTO>> getActors() {
        List<ActorDTO> actorList = actorService.getAllActors();
        if (actorList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actorList, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<ActorDTO>> getActorsByName(@PathVariable String name) {
        List<ActorDTO> actorList = actorService.getActorsByName(name);
        if (actorList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actorList, HttpStatus.OK);
    }

}
