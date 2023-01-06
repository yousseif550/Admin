package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.NumeroInventaire;
import com.mycompany.myapp.repository.NumeroInventaireRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.NumeroInventaire}.
 */
@RestController
@RequestMapping("/api")
public class NumeroInventaireResource {

    private final Logger log = LoggerFactory.getLogger(NumeroInventaireResource.class);

    private static final String ENTITY_NAME = "numeroInventaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NumeroInventaireRepository numeroInventaireRepository;

    public NumeroInventaireResource(NumeroInventaireRepository numeroInventaireRepository) {
        this.numeroInventaireRepository = numeroInventaireRepository;
    }

    /**
     * {@code POST  /numero-inventaires} : Create a new numeroInventaire.
     *
     * @param numeroInventaire the numeroInventaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new numeroInventaire, or with status {@code 400 (Bad Request)} if the numeroInventaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/numero-inventaires")
    public Mono<ResponseEntity<NumeroInventaire>> createNumeroInventaire(@RequestBody NumeroInventaire numeroInventaire)
        throws URISyntaxException {
        log.debug("REST request to save NumeroInventaire : {}", numeroInventaire);
        if (numeroInventaire.getId() != null) {
            throw new BadRequestAlertException("A new numeroInventaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return numeroInventaireRepository
            .save(numeroInventaire)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/numero-inventaires/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /numero-inventaires/:id} : Updates an existing numeroInventaire.
     *
     * @param id the id of the numeroInventaire to save.
     * @param numeroInventaire the numeroInventaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated numeroInventaire,
     * or with status {@code 400 (Bad Request)} if the numeroInventaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the numeroInventaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/numero-inventaires/{id}")
    public Mono<ResponseEntity<NumeroInventaire>> updateNumeroInventaire(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody NumeroInventaire numeroInventaire
    ) throws URISyntaxException {
        log.debug("REST request to update NumeroInventaire : {}, {}", id, numeroInventaire);
        if (numeroInventaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, numeroInventaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return numeroInventaireRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return numeroInventaireRepository
                    .save(numeroInventaire)
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
     * {@code PATCH  /numero-inventaires/:id} : Partial updates given fields of an existing numeroInventaire, field will ignore if it is null
     *
     * @param id the id of the numeroInventaire to save.
     * @param numeroInventaire the numeroInventaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated numeroInventaire,
     * or with status {@code 400 (Bad Request)} if the numeroInventaire is not valid,
     * or with status {@code 404 (Not Found)} if the numeroInventaire is not found,
     * or with status {@code 500 (Internal Server Error)} if the numeroInventaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/numero-inventaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<NumeroInventaire>> partialUpdateNumeroInventaire(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody NumeroInventaire numeroInventaire
    ) throws URISyntaxException {
        log.debug("REST request to partial update NumeroInventaire partially : {}, {}", id, numeroInventaire);
        if (numeroInventaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, numeroInventaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return numeroInventaireRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<NumeroInventaire> result = numeroInventaireRepository
                    .findById(numeroInventaire.getId())
                    .map(existingNumeroInventaire -> {
                        if (numeroInventaire.getZone() != null) {
                            existingNumeroInventaire.setZone(numeroInventaire.getZone());
                        }
                        if (numeroInventaire.getValeur() != null) {
                            existingNumeroInventaire.setValeur(numeroInventaire.getValeur());
                        }
                        if (numeroInventaire.getDisponible() != null) {
                            existingNumeroInventaire.setDisponible(numeroInventaire.getDisponible());
                        }
                        if (numeroInventaire.getAncienMateriel() != null) {
                            existingNumeroInventaire.setAncienMateriel(numeroInventaire.getAncienMateriel());
                        }
                        if (numeroInventaire.getDateModification() != null) {
                            existingNumeroInventaire.setDateModification(numeroInventaire.getDateModification());
                        }

                        return existingNumeroInventaire;
                    })
                    .flatMap(numeroInventaireRepository::save);

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
     * {@code GET  /numero-inventaires} : get all the numeroInventaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of numeroInventaires in body.
     */
    @GetMapping("/numero-inventaires")
    public Mono<List<NumeroInventaire>> getAllNumeroInventaires() {
        log.debug("REST request to get all NumeroInventaires");
        return numeroInventaireRepository.findAll().collectList();
    }

    /**
     * {@code GET  /numero-inventaires} : get all the numeroInventaires as a stream.
     * @return the {@link Flux} of numeroInventaires.
     */
    @GetMapping(value = "/numero-inventaires", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<NumeroInventaire> getAllNumeroInventairesAsStream() {
        log.debug("REST request to get all NumeroInventaires as a stream");
        return numeroInventaireRepository.findAll();
    }

    /**
     * {@code GET  /numero-inventaires/:id} : get the "id" numeroInventaire.
     *
     * @param id the id of the numeroInventaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the numeroInventaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/numero-inventaires/{id}")
    public Mono<ResponseEntity<NumeroInventaire>> getNumeroInventaire(@PathVariable String id) {
        log.debug("REST request to get NumeroInventaire : {}", id);
        Mono<NumeroInventaire> numeroInventaire = numeroInventaireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(numeroInventaire);
    }

    /**
     * {@code DELETE  /numero-inventaires/:id} : delete the "id" numeroInventaire.
     *
     * @param id the id of the numeroInventaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/numero-inventaires/{id}")
    public Mono<ResponseEntity<Void>> deleteNumeroInventaire(@PathVariable String id) {
        log.debug("REST request to delete NumeroInventaire : {}", id);
        return numeroInventaireRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
