package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Typemateriel;
import com.mycompany.myapp.repository.TypematerielRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Typemateriel}.
 */
@RestController
@RequestMapping("/api")
public class TypematerielResource {

    private final Logger log = LoggerFactory.getLogger(TypematerielResource.class);

    private static final String ENTITY_NAME = "typemateriel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypematerielRepository typematerielRepository;

    public TypematerielResource(TypematerielRepository typematerielRepository) {
        this.typematerielRepository = typematerielRepository;
    }

    /**
     * {@code POST  /typemateriels} : Create a new typemateriel.
     *
     * @param typemateriel the typemateriel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typemateriel, or with status {@code 400 (Bad Request)} if the typemateriel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/typemateriels")
    public Mono<ResponseEntity<Typemateriel>> createTypemateriel(@RequestBody Typemateriel typemateriel) throws URISyntaxException {
        log.debug("REST request to save Typemateriel : {}", typemateriel);
        if (typemateriel.getId() != null) {
            throw new BadRequestAlertException("A new typemateriel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return typematerielRepository
            .save(typemateriel)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/typemateriels/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /typemateriels/:id} : Updates an existing typemateriel.
     *
     * @param id the id of the typemateriel to save.
     * @param typemateriel the typemateriel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typemateriel,
     * or with status {@code 400 (Bad Request)} if the typemateriel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typemateriel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/typemateriels/{id}")
    public Mono<ResponseEntity<Typemateriel>> updateTypemateriel(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Typemateriel typemateriel
    ) throws URISyntaxException {
        log.debug("REST request to update Typemateriel : {}, {}", id, typemateriel);
        if (typemateriel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typemateriel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return typematerielRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return typematerielRepository
                    .save(typemateriel)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /typemateriels/:id} : Partial updates given fields of an existing typemateriel, field will ignore if it is null
     *
     * @param id the id of the typemateriel to save.
     * @param typemateriel the typemateriel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typemateriel,
     * or with status {@code 400 (Bad Request)} if the typemateriel is not valid,
     * or with status {@code 404 (Not Found)} if the typemateriel is not found,
     * or with status {@code 500 (Internal Server Error)} if the typemateriel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/typemateriels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Typemateriel>> partialUpdateTypemateriel(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Typemateriel typemateriel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Typemateriel partially : {}, {}", id, typemateriel);
        if (typemateriel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typemateriel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return typematerielRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Typemateriel> result = typematerielRepository
                    .findById(typemateriel.getId())
                    .map(existingTypemateriel -> {
                        if (typemateriel.getType() != null) {
                            existingTypemateriel.setType(typemateriel.getType());
                        }

                        return existingTypemateriel;
                    })
                    .flatMap(typematerielRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /typemateriels} : get all the typemateriels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typemateriels in body.
     */
    @GetMapping("/typemateriels")
    public Mono<List<Typemateriel>> getAllTypemateriels() {
        log.debug("REST request to get all Typemateriels");
        return typematerielRepository.findAll().collectList();
    }

    /**
     * {@code GET  /typemateriels} : get all the typemateriels as a stream.
     * @return the {@link Flux} of typemateriels.
     */
    @GetMapping(value = "/typemateriels", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Typemateriel> getAllTypematerielsAsStream() {
        log.debug("REST request to get all Typemateriels as a stream");
        return typematerielRepository.findAll();
    }

    /**
     * {@code GET  /typemateriels/:id} : get the "id" typemateriel.
     *
     * @param id the id of the typemateriel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typemateriel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/typemateriels/{id}")
    public Mono<ResponseEntity<Typemateriel>> getTypemateriel(@PathVariable String id) {
        log.debug("REST request to get Typemateriel : {}", id);
        Mono<Typemateriel> typemateriel = typematerielRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typemateriel);
    }

    /**
     * {@code DELETE  /typemateriels/:id} : delete the "id" typemateriel.
     *
     * @param id the id of the typemateriel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/typemateriels/{id}")
    public Mono<ResponseEntity<Void>> deleteTypemateriel(@PathVariable String id) {
        log.debug("REST request to delete Typemateriel : {}", id);
        return typematerielRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
