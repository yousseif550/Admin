package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Mouvement;
import com.mycompany.myapp.repository.MouvementRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Mouvement}.
 */
@RestController
@RequestMapping("/api")
public class MouvementResource {

    private final Logger log = LoggerFactory.getLogger(MouvementResource.class);

    private static final String ENTITY_NAME = "mouvement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MouvementRepository mouvementRepository;

    public MouvementResource(MouvementRepository mouvementRepository) {
        this.mouvementRepository = mouvementRepository;
    }

    /**
     * {@code POST  /mouvements} : Create a new mouvement.
     *
     * @param mouvement the mouvement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mouvement, or with status {@code 400 (Bad Request)} if the mouvement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mouvements")
    public Mono<ResponseEntity<Mouvement>> createMouvement(@RequestBody Mouvement mouvement) throws URISyntaxException {
        log.debug("REST request to save Mouvement : {}", mouvement);
        if (mouvement.getId() != null) {
            throw new BadRequestAlertException("A new mouvement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return mouvementRepository
            .save(mouvement)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/mouvements/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /mouvements/:id} : Updates an existing mouvement.
     *
     * @param id the id of the mouvement to save.
     * @param mouvement the mouvement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mouvement,
     * or with status {@code 400 (Bad Request)} if the mouvement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mouvement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mouvements/{id}")
    public Mono<ResponseEntity<Mouvement>> updateMouvement(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Mouvement mouvement
    ) throws URISyntaxException {
        log.debug("REST request to update Mouvement : {}, {}", id, mouvement);
        if (mouvement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mouvement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return mouvementRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return mouvementRepository
                    .save(mouvement)
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
     * {@code PATCH  /mouvements/:id} : Partial updates given fields of an existing mouvement, field will ignore if it is null
     *
     * @param id the id of the mouvement to save.
     * @param mouvement the mouvement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mouvement,
     * or with status {@code 400 (Bad Request)} if the mouvement is not valid,
     * or with status {@code 404 (Not Found)} if the mouvement is not found,
     * or with status {@code 500 (Internal Server Error)} if the mouvement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mouvements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Mouvement>> partialUpdateMouvement(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Mouvement mouvement
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mouvement partially : {}, {}", id, mouvement);
        if (mouvement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mouvement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return mouvementRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Mouvement> result = mouvementRepository
                    .findById(mouvement.getId())
                    .map(existingMouvement -> {
                        if (mouvement.getDate() != null) {
                            existingMouvement.setDate(mouvement.getDate());
                        }
                        if (mouvement.getType() != null) {
                            existingMouvement.setType(mouvement.getType());
                        }
                        if (mouvement.getSource() != null) {
                            existingMouvement.setSource(mouvement.getSource());
                        }
                        if (mouvement.getDestination() != null) {
                            existingMouvement.setDestination(mouvement.getDestination());
                        }
                        if (mouvement.getUser() != null) {
                            existingMouvement.setUser(mouvement.getUser());
                        }

                        return existingMouvement;
                    })
                    .flatMap(mouvementRepository::save);

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
     * {@code GET  /mouvements} : get all the mouvements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mouvements in body.
     */
    @GetMapping("/mouvements")
    public Mono<List<Mouvement>> getAllMouvements() {
        log.debug("REST request to get all Mouvements");
        return mouvementRepository.findAll().collectList();
    }

    /**
     * {@code GET  /mouvements} : get all the mouvements as a stream.
     * @return the {@link Flux} of mouvements.
     */
    @GetMapping(value = "/mouvements", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Mouvement> getAllMouvementsAsStream() {
        log.debug("REST request to get all Mouvements as a stream");
        return mouvementRepository.findAll();
    }

    /**
     * {@code GET  /mouvements/:id} : get the "id" mouvement.
     *
     * @param id the id of the mouvement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mouvement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mouvements/{id}")
    public Mono<ResponseEntity<Mouvement>> getMouvement(@PathVariable String id) {
        log.debug("REST request to get Mouvement : {}", id);
        Mono<Mouvement> mouvement = mouvementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mouvement);
    }

    /**
     * {@code DELETE  /mouvements/:id} : delete the "id" mouvement.
     *
     * @param id the id of the mouvement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mouvements/{id}")
    public Mono<ResponseEntity<Void>> deleteMouvement(@PathVariable String id) {
        log.debug("REST request to delete Mouvement : {}", id);
        return mouvementRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
