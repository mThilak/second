package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Movie;
import com.mycompany.myapp.repository.MovieRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Movie.
 */
@RestController
@RequestMapping("/app")
public class MovieResource {

    private final Logger log = LoggerFactory.getLogger(MovieResource.class);

    @Inject
    private MovieRepository movieRepository;

    /**
     * POST  /rest/movies -> Create a new movie.
     */
    @RequestMapping(value = "/rest/movies",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public void create(@RequestBody Movie movie) {
        log.debug("REST request to save Movie : {}", movie);
        movieRepository.save(movie);
    }

    /**
     * GET  /rest/movies -> get all the movies.
     */
    @RequestMapping(value = "/rest/movies",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Movie> getAll() {
        log.debug("REST request to get all Movies");
        return movieRepository.findAll();
    }

    /**
     * GET  /rest/movies/:id -> get the "id" movie.
     */
    @RequestMapping(value = "/rest/movies/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Movie> get(@PathVariable Long id) {
        log.debug("REST request to get Movie : {}", id);
        return Optional.ofNullable(movieRepository.findOne(id))
            .map(movie -> new ResponseEntity<>(
                movie,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/movies/:id -> delete the "id" movie.
     */
    @RequestMapping(value = "/rest/movies/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Movie : {}", id);
        movieRepository.delete(id);
    }
}
