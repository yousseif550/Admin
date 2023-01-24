package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Materiel;
import com.mycompany.myapp.repository.MaterielRepository;
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
 * Integration tests for the {@link MaterielResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class MaterielResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MODELE = "AAAAAAAAAA";
    private static final String UPDATED_MODELE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET = "AAAAAAAAAA";
    private static final String UPDATED_ASSET = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    private static final LocalDate DEFAULT_DATE_ATTRIBUTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ATTRIBUTION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RENDU = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RENDU = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_HS = false;
    private static final Boolean UPDATED_IS_HS = true;

    private static final String ENTITY_API_URL = "/api/materiels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MaterielRepository materielRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Materiel materiel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materiel createEntity() {
        Materiel materiel = new Materiel()
            .type(DEFAULT_TYPE)
            .modele(DEFAULT_MODELE)
            .asset(DEFAULT_ASSET)
            .actif(DEFAULT_ACTIF)
            .dateAttribution(DEFAULT_DATE_ATTRIBUTION)
            .dateRendu(DEFAULT_DATE_RENDU)
            .commentaire(DEFAULT_COMMENTAIRE)
            .isHs(DEFAULT_IS_HS);
        return materiel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materiel createUpdatedEntity() {
        Materiel materiel = new Materiel()
            .type(UPDATED_TYPE)
            .modele(UPDATED_MODELE)
            .asset(UPDATED_ASSET)
            .actif(UPDATED_ACTIF)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .dateRendu(UPDATED_DATE_RENDU)
            .commentaire(UPDATED_COMMENTAIRE)
            .isHs(UPDATED_IS_HS);
        return materiel;
    }

    @BeforeEach
    public void initTest() {
        materielRepository.deleteAll().block();
        materiel = createEntity();
    }

    @Test
    void createMateriel() throws Exception {
        int databaseSizeBeforeCreate = materielRepository.findAll().collectList().block().size();
        // Create the Materiel
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(materiel))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeCreate + 1);
        Materiel testMateriel = materielList.get(materielList.size() - 1);
        assertThat(testMateriel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMateriel.getModele()).isEqualTo(DEFAULT_MODELE);
        assertThat(testMateriel.getAsset()).isEqualTo(DEFAULT_ASSET);
        assertThat(testMateriel.getActif()).isEqualTo(DEFAULT_ACTIF);
        assertThat(testMateriel.getDateAttribution()).isEqualTo(DEFAULT_DATE_ATTRIBUTION);
        assertThat(testMateriel.getDateRendu()).isEqualTo(DEFAULT_DATE_RENDU);
        assertThat(testMateriel.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testMateriel.getIsHs()).isEqualTo(DEFAULT_IS_HS);
    }

    @Test
    void createMaterielWithExistingId() throws Exception {
        // Create the Materiel with an existing ID
        materiel.setId("existing_id");

        int databaseSizeBeforeCreate = materielRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(materiel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllMaterielsAsStream() {
        // Initialize the database
        materielRepository.save(materiel).block();

        List<Materiel> materielList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Materiel.class)
            .getResponseBody()
            .filter(materiel::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(materielList).isNotNull();
        assertThat(materielList).hasSize(1);
        Materiel testMateriel = materielList.get(0);
        assertThat(testMateriel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMateriel.getModele()).isEqualTo(DEFAULT_MODELE);
        assertThat(testMateriel.getAsset()).isEqualTo(DEFAULT_ASSET);
        assertThat(testMateriel.getActif()).isEqualTo(DEFAULT_ACTIF);
        assertThat(testMateriel.getDateAttribution()).isEqualTo(DEFAULT_DATE_ATTRIBUTION);
        assertThat(testMateriel.getDateRendu()).isEqualTo(DEFAULT_DATE_RENDU);
        assertThat(testMateriel.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testMateriel.getIsHs()).isEqualTo(DEFAULT_IS_HS);
    }

    @Test
    void getAllMateriels() {
        // Initialize the database
        materielRepository.save(materiel).block();

        // Get all the materielList
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
            .value(hasItem(materiel.getId()))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE))
            .jsonPath("$.[*].modele")
            .value(hasItem(DEFAULT_MODELE))
            .jsonPath("$.[*].asset")
            .value(hasItem(DEFAULT_ASSET))
            .jsonPath("$.[*].actif")
            .value(hasItem(DEFAULT_ACTIF.booleanValue()))
            .jsonPath("$.[*].dateAttribution")
            .value(hasItem(DEFAULT_DATE_ATTRIBUTION.toString()))
            .jsonPath("$.[*].dateRendu")
            .value(hasItem(DEFAULT_DATE_RENDU.toString()))
            .jsonPath("$.[*].commentaire")
            .value(hasItem(DEFAULT_COMMENTAIRE))
            .jsonPath("$.[*].isHs")
            .value(hasItem(DEFAULT_IS_HS.booleanValue()));
    }

    @Test
    void getMateriel() {
        // Initialize the database
        materielRepository.save(materiel).block();

        // Get the materiel
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, materiel.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(materiel.getId()))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE))
            .jsonPath("$.modele")
            .value(is(DEFAULT_MODELE))
            .jsonPath("$.asset")
            .value(is(DEFAULT_ASSET))
            .jsonPath("$.actif")
            .value(is(DEFAULT_ACTIF.booleanValue()))
            .jsonPath("$.dateAttribution")
            .value(is(DEFAULT_DATE_ATTRIBUTION.toString()))
            .jsonPath("$.dateRendu")
            .value(is(DEFAULT_DATE_RENDU.toString()))
            .jsonPath("$.commentaire")
            .value(is(DEFAULT_COMMENTAIRE))
            .jsonPath("$.isHs")
            .value(is(DEFAULT_IS_HS.booleanValue()));
    }

    @Test
    void getNonExistingMateriel() {
        // Get the materiel
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingMateriel() throws Exception {
        // Initialize the database
        materielRepository.save(materiel).block();

        int databaseSizeBeforeUpdate = materielRepository.findAll().collectList().block().size();

        // Update the materiel
        Materiel updatedMateriel = materielRepository.findById(materiel.getId()).block();
        updatedMateriel
            .type(UPDATED_TYPE)
            .modele(UPDATED_MODELE)
            .asset(UPDATED_ASSET)
            .actif(UPDATED_ACTIF)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .dateRendu(UPDATED_DATE_RENDU)
            .commentaire(UPDATED_COMMENTAIRE)
            .isHs(UPDATED_IS_HS);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedMateriel.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedMateriel))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
        Materiel testMateriel = materielList.get(materielList.size() - 1);
        assertThat(testMateriel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMateriel.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testMateriel.getAsset()).isEqualTo(UPDATED_ASSET);
        assertThat(testMateriel.getActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testMateriel.getDateAttribution()).isEqualTo(UPDATED_DATE_ATTRIBUTION);
        assertThat(testMateriel.getDateRendu()).isEqualTo(UPDATED_DATE_RENDU);
        assertThat(testMateriel.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testMateriel.getIsHs()).isEqualTo(UPDATED_IS_HS);
    }

    @Test
    void putNonExistingMateriel() throws Exception {
        int databaseSizeBeforeUpdate = materielRepository.findAll().collectList().block().size();
        materiel.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, materiel.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(materiel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMateriel() throws Exception {
        int databaseSizeBeforeUpdate = materielRepository.findAll().collectList().block().size();
        materiel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(materiel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMateriel() throws Exception {
        int databaseSizeBeforeUpdate = materielRepository.findAll().collectList().block().size();
        materiel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(materiel))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMaterielWithPatch() throws Exception {
        // Initialize the database
        materielRepository.save(materiel).block();

        int databaseSizeBeforeUpdate = materielRepository.findAll().collectList().block().size();

        // Update the materiel using partial update
        Materiel partialUpdatedMateriel = new Materiel();
        partialUpdatedMateriel.setId(materiel.getId());

        partialUpdatedMateriel
            .type(UPDATED_TYPE)
            .modele(UPDATED_MODELE)
            .actif(UPDATED_ACTIF)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .isHs(UPDATED_IS_HS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMateriel.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMateriel))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
        Materiel testMateriel = materielList.get(materielList.size() - 1);
        assertThat(testMateriel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMateriel.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testMateriel.getAsset()).isEqualTo(DEFAULT_ASSET);
        assertThat(testMateriel.getActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testMateriel.getDateAttribution()).isEqualTo(UPDATED_DATE_ATTRIBUTION);
        assertThat(testMateriel.getDateRendu()).isEqualTo(DEFAULT_DATE_RENDU);
        assertThat(testMateriel.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testMateriel.getIsHs()).isEqualTo(UPDATED_IS_HS);
    }

    @Test
    void fullUpdateMaterielWithPatch() throws Exception {
        // Initialize the database
        materielRepository.save(materiel).block();

        int databaseSizeBeforeUpdate = materielRepository.findAll().collectList().block().size();

        // Update the materiel using partial update
        Materiel partialUpdatedMateriel = new Materiel();
        partialUpdatedMateriel.setId(materiel.getId());

        partialUpdatedMateriel
            .type(UPDATED_TYPE)
            .modele(UPDATED_MODELE)
            .asset(UPDATED_ASSET)
            .actif(UPDATED_ACTIF)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .dateRendu(UPDATED_DATE_RENDU)
            .commentaire(UPDATED_COMMENTAIRE)
            .isHs(UPDATED_IS_HS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMateriel.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMateriel))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
        Materiel testMateriel = materielList.get(materielList.size() - 1);
        assertThat(testMateriel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMateriel.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testMateriel.getAsset()).isEqualTo(UPDATED_ASSET);
        assertThat(testMateriel.getActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testMateriel.getDateAttribution()).isEqualTo(UPDATED_DATE_ATTRIBUTION);
        assertThat(testMateriel.getDateRendu()).isEqualTo(UPDATED_DATE_RENDU);
        assertThat(testMateriel.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testMateriel.getIsHs()).isEqualTo(UPDATED_IS_HS);
    }

    @Test
    void patchNonExistingMateriel() throws Exception {
        int databaseSizeBeforeUpdate = materielRepository.findAll().collectList().block().size();
        materiel.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, materiel.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(materiel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMateriel() throws Exception {
        int databaseSizeBeforeUpdate = materielRepository.findAll().collectList().block().size();
        materiel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(materiel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMateriel() throws Exception {
        int databaseSizeBeforeUpdate = materielRepository.findAll().collectList().block().size();
        materiel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(materiel))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMateriel() {
        // Initialize the database
        materielRepository.save(materiel).block();

        int databaseSizeBeforeDelete = materielRepository.findAll().collectList().block().size();

        // Delete the materiel
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, materiel.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Materiel> materielList = materielRepository.findAll().collectList().block();
        assertThat(materielList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
