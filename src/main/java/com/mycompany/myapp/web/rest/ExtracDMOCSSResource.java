package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ExtracDMOCSS;
import com.mycompany.myapp.repository.ExtracDMOCSSRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ExtracDMOCSS}.
 */
@RestController
@RequestMapping("/api")
public class ExtracDMOCSSResource {

    private final Logger log = LoggerFactory.getLogger(ExtracDMOCSSResource.class);

    private static final String ENTITY_NAME = "extracDMOCSS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExtracDMOCSSRepository extracDMOCSSRepository;

    public ExtracDMOCSSResource(ExtracDMOCSSRepository extracDMOCSSRepository) {
        this.extracDMOCSSRepository = extracDMOCSSRepository;
    }

    /**
     * {@code POST  /extrac-dmocsses} : Create a new extracDMOCSS.
     *
     * @param extracDMOCSS the extracDMOCSS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new extracDMOCSS, or with status {@code 400 (Bad Request)} if the extracDMOCSS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/extrac-dmocsses")
    public Mono<ResponseEntity<ExtracDMOCSS>> createExtracDMOCSS(@RequestBody ExtracDMOCSS extracDMOCSS) throws URISyntaxException {
        log.debug("REST request to save ExtracDMOCSS : {}", extracDMOCSS);
        if (extracDMOCSS.getId() != null) {
            throw new BadRequestAlertException("A new extracDMOCSS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return extracDMOCSSRepository
            .save(extracDMOCSS)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/extrac-dmocsses/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /extrac-dmocsses/:id} : Updates an existing extracDMOCSS.
     *
     * @param id the id of the extracDMOCSS to save.
     * @param extracDMOCSS the extracDMOCSS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extracDMOCSS,
     * or with status {@code 400 (Bad Request)} if the extracDMOCSS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the extracDMOCSS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/extrac-dmocsses/{id}")
    public Mono<ResponseEntity<ExtracDMOCSS>> updateExtracDMOCSS(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ExtracDMOCSS extracDMOCSS
    ) throws URISyntaxException {
        log.debug("REST request to update ExtracDMOCSS : {}, {}", id, extracDMOCSS);
        if (extracDMOCSS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, extracDMOCSS.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return extracDMOCSSRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return extracDMOCSSRepository
                    .save(extracDMOCSS)
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
     * {@code PATCH  /extrac-dmocsses/:id} : Partial updates given fields of an existing extracDMOCSS, field will ignore if it is null
     *
     * @param id the id of the extracDMOCSS to save.
     * @param extracDMOCSS the extracDMOCSS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extracDMOCSS,
     * or with status {@code 400 (Bad Request)} if the extracDMOCSS is not valid,
     * or with status {@code 404 (Not Found)} if the extracDMOCSS is not found,
     * or with status {@code 500 (Internal Server Error)} if the extracDMOCSS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/extrac-dmocsses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ExtracDMOCSS>> partialUpdateExtracDMOCSS(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ExtracDMOCSS extracDMOCSS
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExtracDMOCSS partially : {}, {}", id, extracDMOCSS);
        if (extracDMOCSS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, extracDMOCSS.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return extracDMOCSSRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ExtracDMOCSS> result = extracDMOCSSRepository
                    .findById(extracDMOCSS.getId())
                    .map(existingExtracDMOCSS -> {
                        if (extracDMOCSS.getAdressePhysiqueDGFiP() != null) {
                            existingExtracDMOCSS.setAdressePhysiqueDGFiP(extracDMOCSS.getAdressePhysiqueDGFiP());
                        }
                        if (extracDMOCSS.getDate() != null) {
                            existingExtracDMOCSS.setDate(extracDMOCSS.getDate());
                        }
                        if (extracDMOCSS.getIpPcDgfip() != null) {
                            existingExtracDMOCSS.setIpPcDgfip(extracDMOCSS.getIpPcDgfip());
                        }
                        if (extracDMOCSS.getIpVpnIPSEC() != null) {
                            existingExtracDMOCSS.setIpVpnIPSEC(extracDMOCSS.getIpVpnIPSEC());
                        }
                        if (extracDMOCSS.getIoTeletravail() != null) {
                            existingExtracDMOCSS.setIoTeletravail(extracDMOCSS.getIoTeletravail());
                        }
                        if (extracDMOCSS.getStatut() != null) {
                            existingExtracDMOCSS.setStatut(extracDMOCSS.getStatut());
                        }
                        if (extracDMOCSS.getNumVersion() != null) {
                            existingExtracDMOCSS.setNumVersion(extracDMOCSS.getNumVersion());
                        }

                        return existingExtracDMOCSS;
                    })
                    .flatMap(extracDMOCSSRepository::save);

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
     * {@code GET  /extrac-dmocsses} : get all the extracDMOCSSES.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of extracDMOCSSES in body.
     */
    @GetMapping("/extrac-dmocsses")
    public Mono<List<ExtracDMOCSS>> getAllExtracDMOCSSES(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ExtracDMOCSSES");
        if (eagerload) {
            return extracDMOCSSRepository.findAllWithEagerRelationships().collectList();
        } else {
            return extracDMOCSSRepository.findAll().collectList();
        }
    }

    /**
     * {@code GET  /extrac-dmocsses} : get all the extracDMOCSSES as a stream.
     * @return the {@link Flux} of extracDMOCSSES.
     */
    @GetMapping(value = "/extrac-dmocsses", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ExtracDMOCSS> getAllExtracDMOCSSESAsStream() {
        log.debug("REST request to get all ExtracDMOCSSES as a stream");
        return extracDMOCSSRepository.findAll();
    }

    /**
     * {@code GET  /extrac-dmocsses/:id} : get the "id" extracDMOCSS.
     *
     * @param id the id of the extracDMOCSS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the extracDMOCSS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/extrac-dmocsses/{id}")
    public Mono<ResponseEntity<ExtracDMOCSS>> getExtracDMOCSS(@PathVariable String id) {
        log.debug("REST request to get ExtracDMOCSS : {}", id);
        Mono<ExtracDMOCSS> extracDMOCSS = extracDMOCSSRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(extracDMOCSS);
    }

    /**
     * {@code DELETE  /extrac-dmocsses/:id} : delete the "id" extracDMOCSS.
     *
     * @param id the id of the extracDMOCSS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/extrac-dmocsses/{id}")
    public Mono<ResponseEntity<Void>> deleteExtracDMOCSS(@PathVariable String id) {
        log.debug("REST request to delete ExtracDMOCSS : {}", id);
        return extracDMOCSSRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
