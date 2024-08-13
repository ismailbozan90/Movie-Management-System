package org.scamlet.movie_management.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    @NotNull
    @Size(min = 2, max = 100)
    private String title;

    private Integer year;
    private Double rating;
    private String director;
    private Set<String> actors = new HashSet<>();
    private Set<String> genres = new HashSet<>();

}
