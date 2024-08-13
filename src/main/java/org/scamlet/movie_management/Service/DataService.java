package org.scamlet.movie_management.Service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.scamlet.movie_management.Entity.Actor;
import org.scamlet.movie_management.Entity.Director;
import org.scamlet.movie_management.Entity.Genre;
import org.scamlet.movie_management.Entity.Movie;
import org.scamlet.movie_management.Exception.DataException;
import org.scamlet.movie_management.Repository.ActorRepository;
import org.scamlet.movie_management.Repository.DirectorRepository;
import org.scamlet.movie_management.Repository.GenreRepository;
import org.scamlet.movie_management.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.util.*;

@Service
public class DataService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final DirectorRepository directorRepository;

    @Autowired
    public DataService(ActorRepository actorRepository, MovieRepository movieRepository, GenreRepository genreRepository, DirectorRepository directorRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.directorRepository = directorRepository;
    }

    @Transactional
    public Boolean prepareData() {

        // Clear old data
        actorRepository.deleteAll();
        movieRepository.deleteAll();
        genreRepository.deleteAll();
        directorRepository.deleteAll();
        //

        String excelFilePath = "src/main/resources/imdb_top_1000.xls";

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new HSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                String title = null;
                String directorName = null;
                int year = 0;
                double rating = 0;
                List<String> actors = new ArrayList<>();
                List<String> genres = new ArrayList<>();

                for (Cell cell : row) {
                    int index = cell.getColumnIndex();
                    switch (index) {
                        case 0:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                title = String.valueOf(cell.getNumericCellValue());
                            } else {
                                title = cell.getStringCellValue();
                            }
                            break;
                        case 1:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                year = (int) cell.getNumericCellValue();
                            }
                            break;
                        case 2:
                            String data = cell.getStringCellValue();
                            String[] genresArray = data.split(",");
                            List<String> trimmedGenres = Arrays.stream(genresArray).map(String::trim).toList();
                            genres.addAll(trimmedGenres);
                            break;
                        case 3:
                            rating = cell.getNumericCellValue();
                            break;
                        case 4:
                            directorName = cell.getStringCellValue();
                            break;
                        default:
                            if (!actors.contains(cell.getStringCellValue())) {
                                actors.add(cell.getStringCellValue());
                            }
                            break;
                    }
                }

                if (title != null && directorName != null && !actors.isEmpty() && !genres.isEmpty() && year != 0 && rating != 0) {
                    setupData(title, directorName, year, rating, genres, actors);
                }
            }
        } catch (Exception e) {
            throw new DataException("Couldn't save data from excel file. \n" + e.getMessage());
        }
        return true;
    }


    public void setupData(String title, String directorName, int year, double rating, List<String> genres, List<String> actors) {
        Director director = directorRepository.findByName(directorName);
        if (director == null) {
            director = new Director();
            director.setName(directorName);
        }

        Set<Actor> actorSet = new HashSet<>();
        for (String actorName : actors) {
            Actor actor = actorRepository.findByName(actorName);
            if (actor == null) {
                actor = new Actor();
                actor.setName(actorName);
            }
            actorSet.add(actor);
        }

        Set<Genre> genreSet = new HashSet<>();
        for (String genreName : genres) {
            Genre genre = genreRepository.findByName(genreName);
            if (genre == null) {
                genre = new Genre();
                genre.setName(genreName);
            }
            genreSet.add(genre);
        }

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setYear(year);
        movie.setRating(rating);
        movie.setDirector(director);
        movie.setActors(actorSet);
        movie.setGenres(genreSet);

        movieRepository.save(movie);
        actorRepository.saveAll(actorSet);
        genreRepository.saveAll(genreSet);
    }
}