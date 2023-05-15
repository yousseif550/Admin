package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Suivi;
import com.mycompany.myapp.repository.SuiviRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Suivi}.
 */
@RestController
@RequestMapping("/api")
public class SuiviResource {

    private final Logger log = LoggerFactory.getLogger(SuiviResource.class);

    private static final String ENTITY_NAME = "suivi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SuiviRepository suiviRepository;

    public SuiviResource(SuiviRepository suiviRepository) {
        this.suiviRepository = suiviRepository;
    }

    /**
     * {@code POST  /suivis} : Create a new suivi.
     *
     * @param suivi the suivi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new suivi, or with status {@code 400 (Bad Request)} if the suivi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/suivis")
    public Mono<ResponseEntity<Suivi>> createSuivi(@RequestBody Suivi suivi) throws URISyntaxException {
        log.debug("REST request to save Suivi : {}", suivi);
        if (suivi.getId() != null) {
            throw new BadRequestAlertException("A new suivi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return suiviRepository
            .save(suivi)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/suivis/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /suivis/:id} : Updates an existing suivi.
     *
     * @param id the id of the suivi to save.
     * @param suivi the suivi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suivi,
     * or with status {@code 400 (Bad Request)} if the suivi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the suivi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/suivis/{id}")
    public Mono<ResponseEntity<Suivi>> updateSuivi(@PathVariable(value = "id", required = false) final String id, @RequestBody Suivi suivi)
        throws URISyntaxException {
        log.debug("REST request to update Suivi : {}, {}", id, suivi);
        if (suivi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, suivi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return suiviRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return suiviRepository
                    .save(suivi)
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
     * {@code PATCH  /suivis/:id} : Partial updates given fields of an existing suivi, field will ignore if it is null
     *
     * @param id the id of the suivi to save.
     * @param suivi the suivi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suivi,
     * or with status {@code 400 (Bad Request)} if the suivi is not valid,
     * or with status {@code 404 (Not Found)} if the suivi is not found,
     * or with status {@code 500 (Internal Server Error)} if the suivi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/suivis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Suivi>> partialUpdateSuivi(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Suivi suivi
    ) throws URISyntaxException {
        log.debug("REST request to partial update Suivi partially : {}, {}", id, suivi);
        if (suivi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, suivi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return suiviRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Suivi> result = suiviRepository
                    .findById(suivi.getId())
                    .map(existingSuivi -> {
                        if (suivi.getEnvoiKitAccueil() != null) {
                            existingSuivi.setEnvoiKitAccueil(suivi.getEnvoiKitAccueil());
                        }
                        if (suivi.getDocumentSigner() != null) {
                            existingSuivi.setDocumentSigner(suivi.getDocumentSigner());
                        }
                        if (suivi.getCommandePCDom() != null) {
                            existingSuivi.setCommandePCDom(suivi.getCommandePCDom());
                        }
                        if (suivi.getCompteSSG() != null) {
                            existingSuivi.setCompteSSG(suivi.getCompteSSG());
                        }
                        if (suivi.getListeNTIC() != null) {
                            existingSuivi.setListeNTIC(suivi.getListeNTIC());
                        }
                        if (suivi.getAccesTeams() != null) {
                            existingSuivi.setAccesTeams(suivi.getAccesTeams());
                        }
                        if (suivi.getAccesPulseDGFiP() != null) {
                            existingSuivi.setAccesPulseDGFiP(suivi.getAccesPulseDGFiP());
                        }
                        if (suivi.getProfilPCDom() != null) {
                            existingSuivi.setProfilPCDom(suivi.getProfilPCDom());
                        }
                        if (suivi.getCommanderPCDGFiP() != null) {
                            existingSuivi.setCommanderPCDGFiP(suivi.getCommanderPCDGFiP());
                        }
                        if (suivi.getCreationBALPDGFiP() != null) {
                            existingSuivi.setCreationBALPDGFiP(suivi.getCreationBALPDGFiP());
                        }
                        if (suivi.getCreationCompteAD() != null) {
                            existingSuivi.setCreationCompteAD(suivi.getCreationCompteAD());
                        }
                        if (suivi.getSoclagePC() != null) {
                            existingSuivi.setSoclagePC(suivi.getSoclagePC());
                        }
                        if (suivi.getDmocssIpTT() != null) {
                            existingSuivi.setDmocssIpTT(suivi.getDmocssIpTT());
                        }
                        if (suivi.getInstallationLogiciel() != null) {
                            existingSuivi.setInstallationLogiciel(suivi.getInstallationLogiciel());
                        }
                        if (suivi.getCommentaires() != null) {
                            existingSuivi.setCommentaires(suivi.getCommentaires());
                        }

                        return existingSuivi;
                    })
                    .flatMap(suiviRepository::save);

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
     * {@code GET  /suivis} : get all the suivis.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of suivis in body.
     */
    @GetMapping("/suivis")
    public Mono<List<Suivi>> getAllSuivis(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Suivis");
        if (eagerload) {
            return suiviRepository.findAllWithEagerRelationships().collectList();
        } else {
            return suiviRepository.findAll().collectList();
        }
    }

    /**
     * {@code GET  /suivis} : get all the suivis as a stream.
     * @return the {@link Flux} of suivis.
     */
    @GetMapping(value = "/suivis", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Suivi> getAllSuivisAsStream() {
        log.debug("REST request to get all Suivis as a stream");
        return suiviRepository.findAll();
    }

    /**
     * {@code GET  /suivis/:id} : get the "id" suivi.
     *
     * @param id the id of the suivi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the suivi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/suivis/{id}")
    public Mono<ResponseEntity<Suivi>> getSuivi(@PathVariable String id) {
        log.debug("REST request to get Suivi : {}", id);
        Mono<Suivi> suivi = suiviRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(suivi);
    }

    /**
     * {@code DELETE  /suivis/:id} : delete the "id" suivi.
     *
     * @param id the id of the suivi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/suivis/{id}")
    public Mono<ResponseEntity<Void>> deleteSuivi(@PathVariable String id) {
        log.debug("REST request to delete Suivi : {}", id);
        return suiviRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
