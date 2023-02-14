package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Projet;
import com.mycompany.myapp.repository.ProjetRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Projet}.
 */
@RestController
@RequestMapping("/api")
public class ProjetResource {

    private final Logger log = LoggerFactory.getLogger(ProjetResource.class);

    private static final String ENTITY_NAME = "projet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjetRepository projetRepository;

    public ProjetResource(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    /**
     * {@code POST  /projets} : Create a new projet.
     *
     * @param projet the projet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projet, or with status {@code 400 (Bad Request)} if the projet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projets")
    public Mono<ResponseEntity<Projet>> createProjet(@RequestBody Projet projet) throws URISyntaxException {
        log.debug("REST request to save Projet : {}", projet);
        if (projet.getId() != null) {
            throw new BadRequestAlertException("A new projet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return projetRepository
            .save(projet)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/projets/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /projets/:id} : Updates an existing projet.
     *
     * @param id the id of the projet to save.
     * @param projet the projet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projet,
     * or with status {@code 400 (Bad Request)} if the projet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projets/{id}")
    public Mono<ResponseEntity<Projet>> updateProjet(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Projet projet
    ) throws URISyntaxException {
        log.debug("REST request to update Projet : {}, {}", id, projet);
        if (projet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return projetRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return projetRepository
                    .save(projet)
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
     * {@code PATCH  /projets/:id} : Partial updates given fields of an existing projet, field will ignore if it is null
     *
     * @param id the id of the projet to save.
     * @param projet the projet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projet,
     * or with status {@code 400 (Bad Request)} if the projet is not valid,
     * or with status {@code 404 (Not Found)} if the projet is not found,
     * or with status {@code 500 (Internal Server Error)} if the projet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/projets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Projet>> partialUpdateProjet(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Projet projet
    ) throws URISyntaxException {
        log.debug("REST request to partial update Projet partially : {}, {}", id, projet);
        if (projet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return projetRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Projet> result = projetRepository
                    .findById(projet.getId())
                    .map(existingProjet -> {
                        if (projet.getNom() != null) {
                            existingProjet.setNom(projet.getNom());
                        }
                        if (projet.getStucture() != null) {
                            existingProjet.setStucture(projet.getStucture());
                        }
                        if (projet.getInformations() != null) {
                            existingProjet.setInformations(projet.getInformations());
                        }

                        return existingProjet;
                    })
                    .flatMap(projetRepository::save);

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
     * {@code GET  /projets} : get all the projets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projets in body.
     */
    @GetMapping("/projets")
    public Mono<List<Projet>> getAllProjets() {
        log.debug("REST request to get all Projets");
        return projetRepository.findAll().collectList();
    }

    /**
     * {@code GET  /projets} : get all the projets as a stream.
     * @return the {@link Flux} of projets.
     */
    @GetMapping(value = "/projets", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Projet> getAllProjetsAsStream() {
        log.debug("REST request to get all Projets as a stream");
        return projetRepository.findAll();
    }

    /**
     * {@code GET  /projets/:id} : get the "id" projet.
     *
     * @param id the id of the projet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projets/{id}")
    public Mono<ResponseEntity<Projet>> getProjet(@PathVariable String id) {
        log.debug("REST request to get Projet : {}", id);
        Mono<Projet> projet = projetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projet);
    }

    /**
     * {@code DELETE  /projets/:id} : delete the "id" projet.
     *
     * @param id the id of the projet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projets/{id}")
    public Mono<ResponseEntity<Void>> deleteProjet(@PathVariable String id) {
        log.debug("REST request to delete Projet : {}", id);
        return projetRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
