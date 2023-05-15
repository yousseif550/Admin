package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.NumeroInventaire;
import com.mycompany.myapp.repository.NumeroInventaireRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Integration tests for the {@link NumeroInventaireResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class NumeroInventaireResourceIT {

    private static final Boolean DEFAULT_DISPONIBLE = false;
    private static final Boolean UPDATED_DISPONIBLE = true;

    private static final LocalDate DEFAULT_DATE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/numero-inventaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private NumeroInventaireRepository numeroInventaireRepository;

    @Mock
    private NumeroInventaireRepository numeroInventaireRepositoryMock;

    @Autowired
    private WebTestClient webTestClient;

    private NumeroInventaire numeroInventaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NumeroInventaire createEntity() {
        NumeroInventaire numeroInventaire = new NumeroInventaire()
            .disponible(DEFAULT_DISPONIBLE)
            .dateModification(DEFAULT_DATE_MODIFICATION)
            .commentaire(DEFAULT_COMMENTAIRE);
        return numeroInventaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NumeroInventaire createUpdatedEntity() {
        NumeroInventaire numeroInventaire = new NumeroInventaire()
            .disponible(UPDATED_DISPONIBLE)
            .dateModification(UPDATED_DATE_MODIFICATION)
            .commentaire(UPDATED_COMMENTAIRE);
        return numeroInventaire;
    }

    @BeforeEach
    public void initTest() {
        numeroInventaireRepository.deleteAll().block();
        numeroInventaire = createEntity();
    }

    @Test
    void createNumeroInventaire() throws Exception {
        int databaseSizeBeforeCreate = numeroInventaireRepository.findAll().collectList().block().size();
        // Create the NumeroInventaire
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(numeroInventaire))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the NumeroInventaire in the database
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeCreate + 1);
        NumeroInventaire testNumeroInventaire = numeroInventaireList.get(numeroInventaireList.size() - 1);
        assertThat(testNumeroInventaire.getDisponible()).isEqualTo(DEFAULT_DISPONIBLE);
        assertThat(testNumeroInventaire.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
        assertThat(testNumeroInventaire.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    void createNumeroInventaireWithExistingId() throws Exception {
        // Create the NumeroInventaire with an existing ID
        numeroInventaire.setId("existing_id");

        int databaseSizeBeforeCreate = numeroInventaireRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(numeroInventaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NumeroInventaire in the database
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllNumeroInventairesAsStream() {
        // Initialize the database
        numeroInventaireRepository.save(numeroInventaire).block();

        List<NumeroInventaire> numeroInventaireList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(NumeroInventaire.class)
            .getResponseBody()
            .filter(numeroInventaire::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(numeroInventaireList).isNotNull();
        assertThat(numeroInventaireList).hasSize(1);
        NumeroInventaire testNumeroInventaire = numeroInventaireList.get(0);
        assertThat(testNumeroInventaire.getDisponible()).isEqualTo(DEFAULT_DISPONIBLE);
        assertThat(testNumeroInventaire.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
        assertThat(testNumeroInventaire.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    void getAllNumeroInventaires() {
        // Initialize the database
        numeroInventaireRepository.save(numeroInventaire).block();

        // Get all the numeroInventaireList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(numeroInventaire.getId()))
            .jsonPath("$.[*].disponible")
            .value(hasItem(DEFAULT_DISPONIBLE.booleanValue()))
            .jsonPath("$.[*].dateModification")
            .value(hasItem(DEFAULT_DATE_MODIFICATION.toString()))
            .jsonPath("$.[*].commentaire")
            .value(hasItem(DEFAULT_COMMENTAIRE));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNumeroInventairesWithEagerRelationshipsIsEnabled() {
        when(numeroInventaireRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(numeroInventaireRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNumeroInventairesWithEagerRelationshipsIsNotEnabled() {
        when(numeroInventaireRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(numeroInventaireRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getNumeroInventaire() {
        // Initialize the database
        numeroInventaireRepository.save(numeroInventaire).block();

        // Get the numeroInventaire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, numeroInventaire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(numeroInventaire.getId()))
            .jsonPath("$.disponible")
            .value(is(DEFAULT_DISPONIBLE.booleanValue()))
            .jsonPath("$.dateModification")
            .value(is(DEFAULT_DATE_MODIFICATION.toString()))
            .jsonPath("$.commentaire")
            .value(is(DEFAULT_COMMENTAIRE));
    }

    @Test
    void getNonExistingNumeroInventaire() {
        // Get the numeroInventaire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingNumeroInventaire() throws Exception {
        // Initialize the database
        numeroInventaireRepository.save(numeroInventaire).block();

        int databaseSizeBeforeUpdate = numeroInventaireRepository.findAll().collectList().block().size();

        // Update the numeroInventaire
        NumeroInventaire updatedNumeroInventaire = numeroInventaireRepository.findById(numeroInventaire.getId()).block();
        updatedNumeroInventaire.disponible(UPDATED_DISPONIBLE).dateModification(UPDATED_DATE_MODIFICATION).commentaire(UPDATED_COMMENTAIRE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedNumeroInventaire.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedNumeroInventaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NumeroInventaire in the database
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeUpdate);
        NumeroInventaire testNumeroInventaire = numeroInventaireList.get(numeroInventaireList.size() - 1);
        assertThat(testNumeroInventaire.getDisponible()).isEqualTo(UPDATED_DISPONIBLE);
        assertThat(testNumeroInventaire.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
        assertThat(testNumeroInventaire.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    void putNonExistingNumeroInventaire() throws Exception {
        int databaseSizeBeforeUpdate = numeroInventaireRepository.findAll().collectList().block().size();
        numeroInventaire.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, numeroInventaire.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(numeroInventaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NumeroInventaire in the database
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchNumeroInventaire() throws Exception {
        int databaseSizeBeforeUpdate = numeroInventaireRepository.findAll().collectList().block().size();
        numeroInventaire.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(numeroInventaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NumeroInventaire in the database
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamNumeroInventaire() throws Exception {
        int databaseSizeBeforeUpdate = numeroInventaireRepository.findAll().collectList().block().size();
        numeroInventaire.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(numeroInventaire))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the NumeroInventaire in the database
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateNumeroInventaireWithPatch() throws Exception {
        // Initialize the database
        numeroInventaireRepository.save(numeroInventaire).block();

        int databaseSizeBeforeUpdate = numeroInventaireRepository.findAll().collectList().block().size();

        // Update the numeroInventaire using partial update
        NumeroInventaire partialUpdatedNumeroInventaire = new NumeroInventaire();
        partialUpdatedNumeroInventaire.setId(numeroInventaire.getId());

        partialUpdatedNumeroInventaire.dateModification(UPDATED_DATE_MODIFICATION).commentaire(UPDATED_COMMENTAIRE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNumeroInventaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNumeroInventaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NumeroInventaire in the database
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeUpdate);
        NumeroInventaire testNumeroInventaire = numeroInventaireList.get(numeroInventaireList.size() - 1);
        assertThat(testNumeroInventaire.getDisponible()).isEqualTo(DEFAULT_DISPONIBLE);
        assertThat(testNumeroInventaire.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
        assertThat(testNumeroInventaire.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    void fullUpdateNumeroInventaireWithPatch() throws Exception {
        // Initialize the database
        numeroInventaireRepository.save(numeroInventaire).block();

        int databaseSizeBeforeUpdate = numeroInventaireRepository.findAll().collectList().block().size();

        // Update the numeroInventaire using partial update
        NumeroInventaire partialUpdatedNumeroInventaire = new NumeroInventaire();
        partialUpdatedNumeroInventaire.setId(numeroInventaire.getId());

        partialUpdatedNumeroInventaire
            .disponible(UPDATED_DISPONIBLE)
            .dateModification(UPDATED_DATE_MODIFICATION)
            .commentaire(UPDATED_COMMENTAIRE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNumeroInventaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNumeroInventaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NumeroInventaire in the database
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeUpdate);
        NumeroInventaire testNumeroInventaire = numeroInventaireList.get(numeroInventaireList.size() - 1);
        assertThat(testNumeroInventaire.getDisponible()).isEqualTo(UPDATED_DISPONIBLE);
        assertThat(testNumeroInventaire.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
        assertThat(testNumeroInventaire.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    void patchNonExistingNumeroInventaire() throws Exception {
        int databaseSizeBeforeUpdate = numeroInventaireRepository.findAll().collectList().block().size();
        numeroInventaire.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, numeroInventaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(numeroInventaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NumeroInventaire in the database
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchNumeroInventaire() throws Exception {
        int databaseSizeBeforeUpdate = numeroInventaireRepository.findAll().collectList().block().size();
        numeroInventaire.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(numeroInventaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NumeroInventaire in the database
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamNumeroInventaire() throws Exception {
        int databaseSizeBeforeUpdate = numeroInventaireRepository.findAll().collectList().block().size();
        numeroInventaire.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(numeroInventaire))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the NumeroInventaire in the database
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteNumeroInventaire() {
        // Initialize the database
        numeroInventaireRepository.save(numeroInventaire).block();

        int databaseSizeBeforeDelete = numeroInventaireRepository.findAll().collectList().block().size();

        // Delete the numeroInventaire
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, numeroInventaire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<NumeroInventaire> numeroInventaireList = numeroInventaireRepository.findAll().collectList().block();
        assertThat(numeroInventaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
