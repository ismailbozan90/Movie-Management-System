package org.scamlet.movie_management.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 2, max = 100)
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "movie_actors", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

}
