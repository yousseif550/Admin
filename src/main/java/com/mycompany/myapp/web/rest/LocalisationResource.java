package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Localisation;
import com.mycompany.myapp.repository.LocalisationRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Localisation}.
 */
@RestController
@RequestMapping("/api")
public class LocalisationResource {

    private final Logger log = LoggerFactory.getLogger(LocalisationResource.class);

    private static final String ENTITY_NAME = "localisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalisationRepository localisationRepository;

    public LocalisationResource(LocalisationRepository localisationRepository) {
        this.localisationRepository = localisationRepository;
    }

    /**
     * {@code POST  /localisations} : Create a new localisation.
     *
     * @param localisation the localisation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localisation, or with status {@code 400 (Bad Request)} if the localisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/localisations")
    public Mono<ResponseEntity<Localisation>> createLocalisation(@RequestBody Localisation localisation) throws URISyntaxException {
        log.debug("REST request to save Localisation : {}", localisation);
        if (localisation.getId() != null) {
            throw new BadRequestAlertException("A new localisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return localisationRepository
            .save(localisation)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/localisations/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /localisations/:id} : Updates an existing localisation.
     *
     * @param id the id of the localisation to save.
     * @param localisation the localisation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localisation,
     * or with status {@code 400 (Bad Request)} if the localisation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localisation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/localisations/{id}")
    public Mono<ResponseEntity<Localisation>> updateLocalisation(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Localisation localisation
    ) throws URISyntaxException {
        log.debug("REST request to update Localisation : {}, {}", id, localisation);
        if (localisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, localisation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return localisationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return localisationRepository
                    .save(localisation)
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
     * {@code PATCH  /localisations/:id} : Partial updates given fields of an existing localisation, field will ignore if it is null
     *
     * @param id the id of the localisation to save.
     * @param localisation the localisation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localisation,
     * or with status {@code 400 (Bad Request)} if the localisation is not valid,
     * or with status {@code 404 (Not Found)} if the localisation is not found,
     * or with status {@code 500 (Internal Server Error)} if the localisation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/localisations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Localisation>> partialUpdateLocalisation(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Localisation localisation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Localisation partially : {}, {}", id, localisation);
        if (localisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, localisation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return localisationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Localisation> result = localisationRepository
                    .findById(localisation.getId())
                    .map(existingLocalisation -> {
                        if (localisation.getBatiment() != null) {
                            existingLocalisation.setBatiment(localisation.getBatiment());
                        }
                        if (localisation.getBureau() != null) {
                            existingLocalisation.setBureau(localisation.getBureau());
                        }
                        if (localisation.getSite() != null) {
                            existingLocalisation.setSite(localisation.getSite());
                        }
                        if (localisation.getVille() != null) {
                            existingLocalisation.setVille(localisation.getVille());
                        }

                        return existingLocalisation;
                    })
                    .flatMap(localisationRepository::save);

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
     * {@code GET  /localisations} : get all the localisations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localisations in body.
     */
    @GetMapping("/localisations")
    public Mono<List<Localisation>> getAllLocalisations() {
        log.debug("REST request to get all Localisations");
        return localisationRepository.findAll().collectList();
    }

    /**
     * {@code GET  /localisations} : get all the localisations as a stream.
     * @return the {@link Flux} of localisations.
     */
    @GetMapping(value = "/localisations", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Localisation> getAllLocalisationsAsStream() {
        log.debug("REST request to get all Localisations as a stream");
        return localisationRepository.findAll();
    }

    /**
     * {@code GET  /localisations/:id} : get the "id" localisation.
     *
     * @param id the id of the localisation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localisation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/localisations/{id}")
    public Mono<ResponseEntity<Localisation>> getLocalisation(@PathVariable String id) {
        log.debug("REST request to get Localisation : {}", id);
        Mono<Localisation> localisation = localisationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(localisation);
    }

    /**
     * {@code DELETE  /localisations/:id} : delete the "id" localisation.
     *
     * @param id the id of the localisation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/localisations/{id}")
    public Mono<ResponseEntity<Void>> deleteLocalisation(@PathVariable String id) {
        log.debug("REST request to delete Localisation : {}", id);
        return localisationRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
