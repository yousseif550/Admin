package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Collaborateurs;
import com.mycompany.myapp.repository.CollaborateursRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Collaborateurs}.
 */
@RestController
@RequestMapping("/api")
public class CollaborateursResource {

    private final Logger log = LoggerFactory.getLogger(CollaborateursResource.class);

    private static final String ENTITY_NAME = "collaborateurs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollaborateursRepository collaborateursRepository;

    public CollaborateursResource(CollaborateursRepository collaborateursRepository) {
        this.collaborateursRepository = collaborateursRepository;
    }

    /**
     * {@code POST  /collaborateurs} : Create a new collaborateurs.
     *
     * @param collaborateurs the collaborateurs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collaborateurs, or with status {@code 400 (Bad Request)} if the collaborateurs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collaborateurs")
    public Mono<ResponseEntity<Collaborateurs>> createCollaborateurs(@RequestBody Collaborateurs collaborateurs) throws URISyntaxException {
        log.debug("REST request to save Collaborateurs : {}", collaborateurs);
        if (collaborateurs.getId() != null) {
            throw new BadRequestAlertException("A new collaborateurs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return collaborateursRepository
            .save(collaborateurs)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/collaborateurs/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /collaborateurs/:id} : Updates an existing collaborateurs.
     *
     * @param id the id of the collaborateurs to save.
     * @param collaborateurs the collaborateurs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collaborateurs,
     * or with status {@code 400 (Bad Request)} if the collaborateurs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collaborateurs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collaborateurs/{id}")
    public Mono<ResponseEntity<Collaborateurs>> updateCollaborateurs(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Collaborateurs collaborateurs
    ) throws URISyntaxException {
        log.debug("REST request to update Collaborateurs : {}, {}", id, collaborateurs);
        if (collaborateurs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collaborateurs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return collaborateursRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return collaborateursRepository
                    .save(collaborateurs)
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
     * {@code PATCH  /collaborateurs/:id} : Partial updates given fields of an existing collaborateurs, field will ignore if it is null
     *
     * @param id the id of the collaborateurs to save.
     * @param collaborateurs the collaborateurs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collaborateurs,
     * or with status {@code 400 (Bad Request)} if the collaborateurs is not valid,
     * or with status {@code 404 (Not Found)} if the collaborateurs is not found,
     * or with status {@code 500 (Internal Server Error)} if the collaborateurs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/collaborateurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Collaborateurs>> partialUpdateCollaborateurs(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Collaborateurs collaborateurs
    ) throws URISyntaxException {
        log.debug("REST request to partial update Collaborateurs partially : {}, {}", id, collaborateurs);
        if (collaborateurs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collaborateurs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return collaborateursRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Collaborateurs> result = collaborateursRepository
                    .findById(collaborateurs.getId())
                    .map(existingCollaborateurs -> {
                        if (collaborateurs.getNom() != null) {
                            existingCollaborateurs.setNom(collaborateurs.getNom());
                        }
                        if (collaborateurs.getSociete() != null) {
                            existingCollaborateurs.setSociete(collaborateurs.getSociete());
                        }
                        if (collaborateurs.getIdentifiant() != null) {
                            existingCollaborateurs.setIdentifiant(collaborateurs.getIdentifiant());
                        }
                        if (collaborateurs.getTel() != null) {
                            existingCollaborateurs.setTel(collaborateurs.getTel());
                        }
                        if (collaborateurs.getPrestataire() != null) {
                            existingCollaborateurs.setPrestataire(collaborateurs.getPrestataire());
                        }
                        if (collaborateurs.getIsActif() != null) {
                            existingCollaborateurs.setIsActif(collaborateurs.getIsActif());
                        }
                        if (collaborateurs.getDateEntree() != null) {
                            existingCollaborateurs.setDateEntree(collaborateurs.getDateEntree());
                        }
                        if (collaborateurs.getDateSortie() != null) {
                            existingCollaborateurs.setDateSortie(collaborateurs.getDateSortie());
                        }

                        return existingCollaborateurs;
                    })
                    .flatMap(collaborateursRepository::save);

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
     * {@code GET  /collaborateurs} : get all the collaborateurs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collaborateurs in body.
     */
    @GetMapping("/collaborateurs")
    public Mono<List<Collaborateurs>> getAllCollaborateurs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Collaborateurs");
        if (eagerload) {
            return collaborateursRepository.findAllWithEagerRelationships().collectList();
        } else {
            return collaborateursRepository.findAll().collectList();
        }
    }

    /**
     * {@code GET  /collaborateurs} : get all the collaborateurs as a stream.
     * @return the {@link Flux} of collaborateurs.
     */
    @GetMapping(value = "/collaborateurs", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Collaborateurs> getAllCollaborateursAsStream() {
        log.debug("REST request to get all Collaborateurs as a stream");
        return collaborateursRepository.findAll();
    }

    /**
     * {@code GET  /collaborateurs/:id} : get the "id" collaborateurs.
     *
     * @param id the id of the collaborateurs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collaborateurs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collaborateurs/{id}")
    public Mono<ResponseEntity<Collaborateurs>> getCollaborateurs(@PathVariable String id) {
        log.debug("REST request to get Collaborateurs : {}", id);
        Mono<Collaborateurs> collaborateurs = collaborateursRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(collaborateurs);
    }

    /**
     * {@code DELETE  /collaborateurs/:id} : delete the "id" collaborateurs.
     *
     * @param id the id of the collaborateurs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collaborateurs/{id}")
    public Mono<ResponseEntity<Void>> deleteCollaborateurs(@PathVariable String id) {
        log.debug("REST request to delete Collaborateurs : {}", id);
        return collaborateursRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
