package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.InformationsTech;
import com.mycompany.myapp.repository.InformationsTechRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.InformationsTech}.
 */
@RestController
@RequestMapping("/api")
public class InformationsTechResource {

    private final Logger log = LoggerFactory.getLogger(InformationsTechResource.class);

    private static final String ENTITY_NAME = "informationsTech";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InformationsTechRepository informationsTechRepository;

    public InformationsTechResource(InformationsTechRepository informationsTechRepository) {
        this.informationsTechRepository = informationsTechRepository;
    }

    /**
     * {@code POST  /informations-teches} : Create a new informationsTech.
     *
     * @param informationsTech the informationsTech to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new informationsTech, or with status {@code 400 (Bad Request)} if the informationsTech has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/informations-teches")
    public Mono<ResponseEntity<InformationsTech>> createInformationsTech(@RequestBody InformationsTech informationsTech)
        throws URISyntaxException {
        log.debug("REST request to save InformationsTech : {}", informationsTech);
        if (informationsTech.getId() != null) {
            throw new BadRequestAlertException("A new informationsTech cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return informationsTechRepository
            .save(informationsTech)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/informations-teches/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /informations-teches/:id} : Updates an existing informationsTech.
     *
     * @param id the id of the informationsTech to save.
     * @param informationsTech the informationsTech to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informationsTech,
     * or with status {@code 400 (Bad Request)} if the informationsTech is not valid,
     * or with status {@code 500 (Internal Server Error)} if the informationsTech couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/informations-teches/{id}")
    public Mono<ResponseEntity<InformationsTech>> updateInformationsTech(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody InformationsTech informationsTech
    ) throws URISyntaxException {
        log.debug("REST request to update InformationsTech : {}, {}", id, informationsTech);
        if (informationsTech.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informationsTech.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return informationsTechRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return informationsTechRepository
                    .save(informationsTech)
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
     * {@code PATCH  /informations-teches/:id} : Partial updates given fields of an existing informationsTech, field will ignore if it is null
     *
     * @param id the id of the informationsTech to save.
     * @param informationsTech the informationsTech to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informationsTech,
     * or with status {@code 400 (Bad Request)} if the informationsTech is not valid,
     * or with status {@code 404 (Not Found)} if the informationsTech is not found,
     * or with status {@code 500 (Internal Server Error)} if the informationsTech couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/informations-teches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<InformationsTech>> partialUpdateInformationsTech(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody InformationsTech informationsTech
    ) throws URISyntaxException {
        log.debug("REST request to partial update InformationsTech partially : {}, {}", id, informationsTech);
        if (informationsTech.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informationsTech.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return informationsTechRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<InformationsTech> result = informationsTechRepository
                    .findById(informationsTech.getId())
                    .map(existingInformationsTech -> {
                        if (informationsTech.getIpdfipConnexion() != null) {
                            existingInformationsTech.setIpdfipConnexion(informationsTech.getIpdfipConnexion());
                        }
                        if (informationsTech.getIpfixDMOCSS() != null) {
                            existingInformationsTech.setIpfixDMOCSS(informationsTech.getIpfixDMOCSS());
                        }
                        if (informationsTech.getAdressMAC() != null) {
                            existingInformationsTech.setAdressMAC(informationsTech.getAdressMAC());
                        }
                        if (informationsTech.getiPTeletravail() != null) {
                            existingInformationsTech.setiPTeletravail(informationsTech.getiPTeletravail());
                        }
                        if (informationsTech.getAdresseDGFiP() != null) {
                            existingInformationsTech.setAdresseDGFiP(informationsTech.getAdresseDGFiP());
                        }

                        return existingInformationsTech;
                    })
                    .flatMap(informationsTechRepository::save);

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
     * {@code GET  /informations-teches} : get all the informationsTeches.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of informationsTeches in body.
     */
    @GetMapping("/informations-teches")
    public Mono<List<InformationsTech>> getAllInformationsTeches(
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get all InformationsTeches");
        if (eagerload) {
            return informationsTechRepository.findAllWithEagerRelationships().collectList();
        } else {
            return informationsTechRepository.findAll().collectList();
        }
    }

    /**
     * {@code GET  /informations-teches} : get all the informationsTeches as a stream.
     * @return the {@link Flux} of informationsTeches.
     */
    @GetMapping(value = "/informations-teches", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<InformationsTech> getAllInformationsTechesAsStream() {
        log.debug("REST request to get all InformationsTeches as a stream");
        return informationsTechRepository.findAll();
    }

    /**
     * {@code GET  /informations-teches/:id} : get the "id" informationsTech.
     *
     * @param id the id of the informationsTech to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the informationsTech, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/informations-teches/{id}")
    public Mono<ResponseEntity<InformationsTech>> getInformationsTech(@PathVariable String id) {
        log.debug("REST request to get InformationsTech : {}", id);
        Mono<InformationsTech> informationsTech = informationsTechRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(informationsTech);
    }

    /**
     * {@code DELETE  /informations-teches/:id} : delete the "id" informationsTech.
     *
     * @param id the id of the informationsTech to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/informations-teches/{id}")
    public Mono<ResponseEntity<Void>> deleteInformationsTech(@PathVariable String id) {
        log.debug("REST request to delete InformationsTech : {}", id);
        return informationsTechRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
