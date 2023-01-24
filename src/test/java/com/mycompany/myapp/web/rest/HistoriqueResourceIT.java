package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Historique;
import com.mycompany.myapp.repository.HistoriqueRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link HistoriqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class HistoriqueResourceIT {

    private static final String DEFAULT_PC = "AAAAAAAAAA";
    private static final String UPDATED_PC = "BBBBBBBBBB";

    private static final String DEFAULT_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_ZONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_MOUVEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MOUVEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/historiques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private HistoriqueRepository historiqueRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Historique historique;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historique createEntity() {
        Historique historique = new Historique().pc(DEFAULT_PC).zone(DEFAULT_ZONE).dateMouvement(DEFAULT_DATE_MOUVEMENT);
        return historique;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historique createUpdatedEntity() {
        Historique historique = new Historique().pc(UPDATED_PC).zone(UPDATED_ZONE).dateMouvement(UPDATED_DATE_MOUVEMENT);
        return historique;
    }

    @BeforeEach
    public void initTest() {
        historiqueRepository.deleteAll().block();
        historique = createEntity();
    }

    @Test
    void createHistorique() throws Exception {
        int databaseSizeBeforeCreate = historiqueRepository.findAll().collectList().block().size();
        // Create the Historique
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(historique))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeCreate + 1);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getPc()).isEqualTo(DEFAULT_PC);
        assertThat(testHistorique.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testHistorique.getDateMouvement()).isEqualTo(DEFAULT_DATE_MOUVEMENT);
    }

    @Test
    void createHistoriqueWithExistingId() throws Exception {
        // Create the Historique with an existing ID
        historique.setId("existing_id");

        int databaseSizeBeforeCreate = historiqueRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(historique))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllHistoriquesAsStream() {
        // Initialize the database
        historiqueRepository.save(historique).block();

        List<Historique> historiqueList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Historique.class)
            .getResponseBody()
            .filter(historique::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(historiqueList).isNotNull();
        assertThat(historiqueList).hasSize(1);
        Historique testHistorique = historiqueList.get(0);
        assertThat(testHistorique.getPc()).isEqualTo(DEFAULT_PC);
        assertThat(testHistorique.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testHistorique.getDateMouvement()).isEqualTo(DEFAULT_DATE_MOUVEMENT);
    }

    @Test
    void getAllHistoriques() {
        // Initialize the database
        historiqueRepository.save(historique).block();

        // Get all the historiqueList
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
            .value(hasItem(historique.getId()))
            .jsonPath("$.[*].pc")
            .value(hasItem(DEFAULT_PC))
            .jsonPath("$.[*].zone")
            .value(hasItem(DEFAULT_ZONE))
            .jsonPath("$.[*].dateMouvement")
            .value(hasItem(DEFAULT_DATE_MOUVEMENT.toString()));
    }

    @Test
    void getHistorique() {
        // Initialize the database
        historiqueRepository.save(historique).block();

        // Get the historique
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, historique.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(historique.getId()))
            .jsonPath("$.pc")
            .value(is(DEFAULT_PC))
            .jsonPath("$.zone")
            .value(is(DEFAULT_ZONE))
            .jsonPath("$.dateMouvement")
            .value(is(DEFAULT_DATE_MOUVEMENT.toString()));
    }

    @Test
    void getNonExistingHistorique() {
        // Get the historique
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingHistorique() throws Exception {
        // Initialize the database
        historiqueRepository.save(historique).block();

        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();

        // Update the historique
        Historique updatedHistorique = historiqueRepository.findById(historique.getId()).block();
        updatedHistorique.pc(UPDATED_PC).zone(UPDATED_ZONE).dateMouvement(UPDATED_DATE_MOUVEMENT);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedHistorique.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedHistorique))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getPc()).isEqualTo(UPDATED_PC);
        assertThat(testHistorique.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testHistorique.getDateMouvement()).isEqualTo(UPDATED_DATE_MOUVEMENT);
    }

    @Test
    void putNonExistingHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, historique.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(historique))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(historique))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(historique))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateHistoriqueWithPatch() throws Exception {
        // Initialize the database
        historiqueRepository.save(historique).block();

        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();

        // Update the historique using partial update
        Historique partialUpdatedHistorique = new Historique();
        partialUpdatedHistorique.setId(historique.getId());

        partialUpdatedHistorique.pc(UPDATED_PC);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedHistorique.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedHistorique))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getPc()).isEqualTo(UPDATED_PC);
        assertThat(testHistorique.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testHistorique.getDateMouvement()).isEqualTo(DEFAULT_DATE_MOUVEMENT);
    }

    @Test
    void fullUpdateHistoriqueWithPatch() throws Exception {
        // Initialize the database
        historiqueRepository.save(historique).block();

        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();

        // Update the historique using partial update
        Historique partialUpdatedHistorique = new Historique();
        partialUpdatedHistorique.setId(historique.getId());

        partialUpdatedHistorique.pc(UPDATED_PC).zone(UPDATED_ZONE).dateMouvement(UPDATED_DATE_MOUVEMENT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedHistorique.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedHistorique))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getPc()).isEqualTo(UPDATED_PC);
        assertThat(testHistorique.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testHistorique.getDateMouvement()).isEqualTo(UPDATED_DATE_MOUVEMENT);
    }

    @Test
    void patchNonExistingHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, historique.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(historique))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(historique))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().collectList().block().size();
        historique.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(historique))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteHistorique() {
        // Initialize the database
        historiqueRepository.save(historique).block();

        int databaseSizeBeforeDelete = historiqueRepository.findAll().collectList().block().size();

        // Delete the historique
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, historique.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Historique> historiqueList = historiqueRepository.findAll().collectList().block();
        assertThat(historiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
