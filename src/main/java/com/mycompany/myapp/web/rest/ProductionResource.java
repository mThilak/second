package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Production;
import com.mycompany.myapp.repository.ProductionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Production.
 */
@RestController
@RequestMapping("/app")
public class ProductionResource {

    private final Logger log = LoggerFactory.getLogger(ProductionResource.class);

    @Inject
    private ProductionRepository productionRepository;

    /**
     * POST  /rest/productions -> Create a new production.
     */
    @RequestMapping(value = "/rest/productions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Production production) {
        log.debug("REST request to save Production : {}", production);
        productionRepository.save(production);
    }

    /**
     * GET  /rest/productions -> get all the productions.
     */
    @RequestMapping(value = "/rest/productions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Production> getAll() {
        log.debug("REST request to get all Productions");
        return productionRepository.findAll();
    }

    /**
     * GET  /rest/productions/:id -> get the "id" production.
     */
    @RequestMapping(value = "/rest/productions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Production> get(@PathVariable Long id) {
        log.debug("REST request to get Production : {}", id);
        return Optional.ofNullable(productionRepository.findOne(id))
            .map(production -> new ResponseEntity<>(
                production,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/productions/:id -> delete the "id" production.
     */
    @RequestMapping(value = "/rest/productions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Production : {}", id);
        productionRepository.delete(id);
    }
}
