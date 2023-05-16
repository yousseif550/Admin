package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Materiel;
import com.mycompany.myapp.repository.MaterielRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Materiel}.
 */
@RestController
@RequestMapping("/api")
public class MaterielResource {

    private final Logger log = LoggerFactory.getLogger(MaterielResource.class);

    private static final String ENTITY_NAME = "materiel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterielRepository materielRepository;

    public MaterielResource(MaterielRepository materielRepository) {
        this.materielRepository = materielRepository;
    }

    /**
     * {@code POST  /materiels} : Create a new materiel.
     *
     * @param materiel the materiel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materiel, or with status {@code 400 (Bad Request)} if the materiel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/materiels")
    public Mono<ResponseEntity<Materiel>> createMateriel(@RequestBody Materiel materiel) throws URISyntaxException {
        log.debug("REST request to save Materiel : {}", materiel);
        if (materiel.getId() != null) {
            throw new BadRequestAlertException("A new materiel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return materielRepository
            .save(materiel)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/materiels/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /materiels/:id} : Updates an existing materiel.
     *
     * @param id the id of the materiel to save.
     * @param materiel the materiel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiel,
     * or with status {@code 400 (Bad Request)} if the materiel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materiel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/materiels/{id}")
    public Mono<ResponseEntity<Materiel>> updateMateriel(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Materiel materiel
    ) throws URISyntaxException {
        log.debug("REST request to update Materiel : {}, {}", id, materiel);
        if (materiel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return materielRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return materielRepository
                    .save(materiel)
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
     * {@code PATCH  /materiels/:id} : Partial updates given fields of an existing materiel, field will ignore if it is null
     *
     * @param id the id of the materiel to save.
     * @param materiel the materiel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiel,
     * or with status {@code 400 (Bad Request)} if the materiel is not valid,
     * or with status {@code 404 (Not Found)} if the materiel is not found,
     * or with status {@code 500 (Internal Server Error)} if the materiel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/materiels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Materiel>> partialUpdateMateriel(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Materiel materiel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Materiel partially : {}, {}", id, materiel);
        if (materiel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return materielRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Materiel> result = materielRepository
                    .findById(materiel.getId())
                    .map(existingMateriel -> {
                        if (materiel.getUtilisation() != null) {
                            existingMateriel.setUtilisation(materiel.getUtilisation());
                        }
                        if (materiel.getModele() != null) {
                            existingMateriel.setModele(materiel.getModele());
                        }
                        if (materiel.getAsset() != null) {
                            existingMateriel.setAsset(materiel.getAsset());
                        }
                        if (materiel.getDateAttribution() != null) {
                            existingMateriel.setDateAttribution(materiel.getDateAttribution());
                        }
                        if (materiel.getDateRendu() != null) {
                            existingMateriel.setDateRendu(materiel.getDateRendu());
                        }
                        if (materiel.getActif() != null) {
                            existingMateriel.setActif(materiel.getActif());
                        }
                        if (materiel.getIsHs() != null) {
                            existingMateriel.setIsHs(materiel.getIsHs());
                        }
                        if (materiel.getCleAntiVol() != null) {
                            existingMateriel.setCleAntiVol(materiel.getCleAntiVol());
                        }
                        if (materiel.getAdressMAC() != null) {
                            existingMateriel.setAdressMAC(materiel.getAdressMAC());
                        }
                        if (materiel.getStationDgfip() != null) {
                            existingMateriel.setStationDgfip(materiel.getStationDgfip());
                        }
                        if (materiel.getIpdfip() != null) {
                            existingMateriel.setIpdfip(materiel.getIpdfip());
                        }
                        if (materiel.getiPTeletravail() != null) {
                            existingMateriel.setiPTeletravail(materiel.getiPTeletravail());
                        }
                        if (materiel.getBios() != null) {
                            existingMateriel.setBios(materiel.getBios());
                        }
                        if (materiel.getMajBios() != null) {
                            existingMateriel.setMajBios(materiel.getMajBios());
                        }
                        if (materiel.getCommentaire() != null) {
                            existingMateriel.setCommentaire(materiel.getCommentaire());
                        }

                        return existingMateriel;
                    })
                    .flatMap(materielRepository::save);

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
     * {@code GET  /materiels} : get all the materiels.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materiels in body.
     */
    @GetMapping("/materiels")
    public Mono<List<Materiel>> getAllMateriels(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Materiels");
        if (eagerload) {
            return materielRepository.findAllWithEagerRelationships().collectList();
        } else {
            return materielRepository.findAll().collectList();
        }
    }

    /**
     * {@code GET  /materiels} : get all the materiels as a stream.
     * @return the {@link Flux} of materiels.
     */
    @GetMapping(value = "/materiels", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Materiel> getAllMaterielsAsStream() {
        log.debug("REST request to get all Materiels as a stream");
        return materielRepository.findAll();
    }

    /**
     * {@code GET  /materiels/:id} : get the "id" materiel.
     *
     * @param id the id of the materiel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materiel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/materiels/{id}")
    public Mono<ResponseEntity<Materiel>> getMateriel(@PathVariable String id) {
        log.debug("REST request to get Materiel : {}", id);
        Mono<Materiel> materiel = materielRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(materiel);
    }

    /**
     * {@code DELETE  /materiels/:id} : delete the "id" materiel.
     *
     * @param id the id of the materiel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/materiels/{id}")
    public Mono<ResponseEntity<Void>> deleteMateriel(@PathVariable String id) {
        log.debug("REST request to delete Materiel : {}", id);
        return materielRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
