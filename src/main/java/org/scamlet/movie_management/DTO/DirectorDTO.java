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
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDTO {

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    private Set<String> movies = new HashSet<>();

}
