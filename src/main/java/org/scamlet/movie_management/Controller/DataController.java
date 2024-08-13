package org.scamlet.movie_management.Controller;

import org.scamlet.movie_management.Service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/setup")
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public ResponseEntity<Boolean> setup() {
        Boolean result = dataService.prepareData();
        if (!result) {
            return new ResponseEntity<>(false, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
