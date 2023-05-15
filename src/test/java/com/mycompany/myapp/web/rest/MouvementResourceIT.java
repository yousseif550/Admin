package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Mouvement;
import com.mycompany.myapp.repository.MouvementRepository;
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
 * Integration tests for the {@link MouvementResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class MouvementResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mouvements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MouvementRepository mouvementRepository;

    @Mock
    private MouvementRepository mouvementRepositoryMock;

    @Autowired
    private WebTestClient webTestClient;

    private Mouvement mouvement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mouvement createEntity() {
        Mouvement mouvement = new Mouvement()
            .date(DEFAULT_DATE)
            .type(DEFAULT_TYPE)
            .source(DEFAULT_SOURCE)
            .destination(DEFAULT_DESTINATION)
            .user(DEFAULT_USER)
            .commentaire(DEFAULT_COMMENTAIRE);
        return mouvement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mouvement createUpdatedEntity() {
        Mouvement mouvement = new Mouvement()
            .date(UPDATED_DATE)
            .type(UPDATED_TYPE)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .user(UPDATED_USER)
            .commentaire(UPDATED_COMMENTAIRE);
        return mouvement;
    }

    @BeforeEach
    public void initTest() {
        mouvementRepository.deleteAll().block();
        mouvement = createEntity();
    }

    @Test
    void createMouvement() throws Exception {
        int databaseSizeBeforeCreate = mouvementRepository.findAll().collectList().block().size();
        // Create the Mouvement
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(mouvement))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeCreate + 1);
        Mouvement testMouvement = mouvementList.get(mouvementList.size() - 1);
        assertThat(testMouvement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMouvement.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMouvement.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testMouvement.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testMouvement.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testMouvement.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    void createMouvementWithExistingId() throws Exception {
        // Create the Mouvement with an existing ID
        mouvement.setId("existing_id");

        int databaseSizeBeforeCreate = mouvementRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(mouvement))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllMouvementsAsStream() {
        // Initialize the database
        mouvementRepository.save(mouvement).block();

        List<Mouvement> mouvementList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Mouvement.class)
            .getResponseBody()
            .filter(mouvement::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(mouvementList).isNotNull();
        assertThat(mouvementList).hasSize(1);
        Mouvement testMouvement = mouvementList.get(0);
        assertThat(testMouvement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMouvement.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMouvement.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testMouvement.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testMouvement.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testMouvement.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    void getAllMouvements() {
        // Initialize the database
        mouvementRepository.save(mouvement).block();

        // Get all the mouvementList
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
            .value(hasItem(mouvement.getId()))
            .jsonPath("$.[*].date")
            .value(hasItem(DEFAULT_DATE.toString()))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE))
            .jsonPath("$.[*].source")
            .value(hasItem(DEFAULT_SOURCE))
            .jsonPath("$.[*].destination")
            .value(hasItem(DEFAULT_DESTINATION))
            .jsonPath("$.[*].user")
            .value(hasItem(DEFAULT_USER))
            .jsonPath("$.[*].commentaire")
            .value(hasItem(DEFAULT_COMMENTAIRE));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMouvementsWithEagerRelationshipsIsEnabled() {
        when(mouvementRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(mouvementRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMouvementsWithEagerRelationshipsIsNotEnabled() {
        when(mouvementRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(mouvementRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getMouvement() {
        // Initialize the database
        mouvementRepository.save(mouvement).block();

        // Get the mouvement
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, mouvement.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(mouvement.getId()))
            .jsonPath("$.date")
            .value(is(DEFAULT_DATE.toString()))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE))
            .jsonPath("$.source")
            .value(is(DEFAULT_SOURCE))
            .jsonPath("$.destination")
            .value(is(DEFAULT_DESTINATION))
            .jsonPath("$.user")
            .value(is(DEFAULT_USER))
            .jsonPath("$.commentaire")
            .value(is(DEFAULT_COMMENTAIRE));
    }

    @Test
    void getNonExistingMouvement() {
        // Get the mouvement
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingMouvement() throws Exception {
        // Initialize the database
        mouvementRepository.save(mouvement).block();

        int databaseSizeBeforeUpdate = mouvementRepository.findAll().collectList().block().size();

        // Update the mouvement
        Mouvement updatedMouvement = mouvementRepository.findById(mouvement.getId()).block();
        updatedMouvement
            .date(UPDATED_DATE)
            .type(UPDATED_TYPE)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .user(UPDATED_USER)
            .commentaire(UPDATED_COMMENTAIRE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedMouvement.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedMouvement))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
        Mouvement testMouvement = mouvementList.get(mouvementList.size() - 1);
        assertThat(testMouvement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMouvement.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMouvement.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testMouvement.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testMouvement.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testMouvement.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    void putNonExistingMouvement() throws Exception {
        int databaseSizeBeforeUpdate = mouvementRepository.findAll().collectList().block().size();
        mouvement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, mouvement.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(mouvement))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMouvement() throws Exception {
        int databaseSizeBeforeUpdate = mouvementRepository.findAll().collectList().block().size();
        mouvement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(mouvement))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMouvement() throws Exception {
        int databaseSizeBeforeUpdate = mouvementRepository.findAll().collectList().block().size();
        mouvement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(mouvement))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMouvementWithPatch() throws Exception {
        // Initialize the database
        mouvementRepository.save(mouvement).block();

        int databaseSizeBeforeUpdate = mouvementRepository.findAll().collectList().block().size();

        // Update the mouvement using partial update
        Mouvement partialUpdatedMouvement = new Mouvement();
        partialUpdatedMouvement.setId(mouvement.getId());

        partialUpdatedMouvement.type(UPDATED_TYPE).source(UPDATED_SOURCE).destination(UPDATED_DESTINATION).commentaire(UPDATED_COMMENTAIRE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMouvement.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMouvement))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
        Mouvement testMouvement = mouvementList.get(mouvementList.size() - 1);
        assertThat(testMouvement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMouvement.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMouvement.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testMouvement.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testMouvement.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testMouvement.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    void fullUpdateMouvementWithPatch() throws Exception {
        // Initialize the database
        mouvementRepository.save(mouvement).block();

        int databaseSizeBeforeUpdate = mouvementRepository.findAll().collectList().block().size();

        // Update the mouvement using partial update
        Mouvement partialUpdatedMouvement = new Mouvement();
        partialUpdatedMouvement.setId(mouvement.getId());

        partialUpdatedMouvement
            .date(UPDATED_DATE)
            .type(UPDATED_TYPE)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .user(UPDATED_USER)
            .commentaire(UPDATED_COMMENTAIRE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMouvement.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMouvement))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
        Mouvement testMouvement = mouvementList.get(mouvementList.size() - 1);
        assertThat(testMouvement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMouvement.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMouvement.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testMouvement.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testMouvement.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testMouvement.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    void patchNonExistingMouvement() throws Exception {
        int databaseSizeBeforeUpdate = mouvementRepository.findAll().collectList().block().size();
        mouvement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, mouvement.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(mouvement))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMouvement() throws Exception {
        int databaseSizeBeforeUpdate = mouvementRepository.findAll().collectList().block().size();
        mouvement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(mouvement))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMouvement() throws Exception {
        int databaseSizeBeforeUpdate = mouvementRepository.findAll().collectList().block().size();
        mouvement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(mouvement))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMouvement() {
        // Initialize the database
        mouvementRepository.save(mouvement).block();

        int databaseSizeBeforeDelete = mouvementRepository.findAll().collectList().block().size();

        // Delete the mouvement
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, mouvement.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Mouvement> mouvementList = mouvementRepository.findAll().collectList().block();
        assertThat(mouvementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
