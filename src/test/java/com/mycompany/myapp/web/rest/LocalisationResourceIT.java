package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Localisation;
import com.mycompany.myapp.repository.LocalisationRepository;
import java.time.Duration;
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
 * Integration tests for the {@link LocalisationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class LocalisationResourceIT {

    private static final String DEFAULT_BATIMENT = "AAAAAAAAAA";
    private static final String UPDATED_BATIMENT = "BBBBBBBBBB";

    private static final String DEFAULT_BUREAU = "AAAAAAAAAA";
    private static final String UPDATED_BUREAU = "BBBBBBBBBB";

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/localisations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LocalisationRepository localisationRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Localisation localisation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createEntity() {
        Localisation localisation = new Localisation()
            .batiment(DEFAULT_BATIMENT)
            .bureau(DEFAULT_BUREAU)
            .site(DEFAULT_SITE)
            .ville(DEFAULT_VILLE);
        return localisation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createUpdatedEntity() {
        Localisation localisation = new Localisation()
            .batiment(UPDATED_BATIMENT)
            .bureau(UPDATED_BUREAU)
            .site(UPDATED_SITE)
            .ville(UPDATED_VILLE);
        return localisation;
    }

    @BeforeEach
    public void initTest() {
        localisationRepository.deleteAll().block();
        localisation = createEntity();
    }

    @Test
    void createLocalisation() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().collectList().block().size();
        // Create the Localisation
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(localisation))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate + 1);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getBatiment()).isEqualTo(DEFAULT_BATIMENT);
        assertThat(testLocalisation.getBureau()).isEqualTo(DEFAULT_BUREAU);
        assertThat(testLocalisation.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testLocalisation.getVille()).isEqualTo(DEFAULT_VILLE);
    }

    @Test
    void createLocalisationWithExistingId() throws Exception {
        // Create the Localisation with an existing ID
        localisation.setId("existing_id");

        int databaseSizeBeforeCreate = localisationRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(localisation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllLocalisationsAsStream() {
        // Initialize the database
        localisationRepository.save(localisation).block();

        List<Localisation> localisationList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Localisation.class)
            .getResponseBody()
            .filter(localisation::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(localisationList).isNotNull();
        assertThat(localisationList).hasSize(1);
        Localisation testLocalisation = localisationList.get(0);
        assertThat(testLocalisation.getBatiment()).isEqualTo(DEFAULT_BATIMENT);
        assertThat(testLocalisation.getBureau()).isEqualTo(DEFAULT_BUREAU);
        assertThat(testLocalisation.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testLocalisation.getVille()).isEqualTo(DEFAULT_VILLE);
    }

    @Test
    void getAllLocalisations() {
        // Initialize the database
        localisationRepository.save(localisation).block();

        // Get all the localisationList
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
            .value(hasItem(localisation.getId()))
            .jsonPath("$.[*].batiment")
            .value(hasItem(DEFAULT_BATIMENT))
            .jsonPath("$.[*].bureau")
            .value(hasItem(DEFAULT_BUREAU))
            .jsonPath("$.[*].site")
            .value(hasItem(DEFAULT_SITE))
            .jsonPath("$.[*].ville")
            .value(hasItem(DEFAULT_VILLE));
    }

    @Test
    void getLocalisation() {
        // Initialize the database
        localisationRepository.save(localisation).block();

        // Get the localisation
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, localisation.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(localisation.getId()))
            .jsonPath("$.batiment")
            .value(is(DEFAULT_BATIMENT))
            .jsonPath("$.bureau")
            .value(is(DEFAULT_BUREAU))
            .jsonPath("$.site")
            .value(is(DEFAULT_SITE))
            .jsonPath("$.ville")
            .value(is(DEFAULT_VILLE));
    }

    @Test
    void getNonExistingLocalisation() {
        // Get the localisation
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation).block();

        int databaseSizeBeforeUpdate = localisationRepository.findAll().collectList().block().size();

        // Update the localisation
        Localisation updatedLocalisation = localisationRepository.findById(localisation.getId()).block();
        updatedLocalisation.batiment(UPDATED_BATIMENT).bureau(UPDATED_BUREAU).site(UPDATED_SITE).ville(UPDATED_VILLE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedLocalisation.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedLocalisation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getBatiment()).isEqualTo(UPDATED_BATIMENT);
        assertThat(testLocalisation.getBureau()).isEqualTo(UPDATED_BUREAU);
        assertThat(testLocalisation.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testLocalisation.getVille()).isEqualTo(UPDATED_VILLE);
    }

    @Test
    void putNonExistingLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().collectList().block().size();
        localisation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, localisation.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(localisation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().collectList().block().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(localisation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().collectList().block().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(localisation))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLocalisationWithPatch() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation).block();

        int databaseSizeBeforeUpdate = localisationRepository.findAll().collectList().block().size();

        // Update the localisation using partial update
        Localisation partialUpdatedLocalisation = new Localisation();
        partialUpdatedLocalisation.setId(localisation.getId());

        partialUpdatedLocalisation.bureau(UPDATED_BUREAU).ville(UPDATED_VILLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedLocalisation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedLocalisation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getBatiment()).isEqualTo(DEFAULT_BATIMENT);
        assertThat(testLocalisation.getBureau()).isEqualTo(UPDATED_BUREAU);
        assertThat(testLocalisation.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testLocalisation.getVille()).isEqualTo(UPDATED_VILLE);
    }

    @Test
    void fullUpdateLocalisationWithPatch() throws Exception {
        // Initialize the database
        localisationRepository.save(localisation).block();

        int databaseSizeBeforeUpdate = localisationRepository.findAll().collectList().block().size();

        // Update the localisation using partial update
        Localisation partialUpdatedLocalisation = new Localisation();
        partialUpdatedLocalisation.setId(localisation.getId());

        partialUpdatedLocalisation.batiment(UPDATED_BATIMENT).bureau(UPDATED_BUREAU).site(UPDATED_SITE).ville(UPDATED_VILLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedLocalisation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedLocalisation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getBatiment()).isEqualTo(UPDATED_BATIMENT);
        assertThat(testLocalisation.getBureau()).isEqualTo(UPDATED_BUREAU);
        assertThat(testLocalisation.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testLocalisation.getVille()).isEqualTo(UPDATED_VILLE);
    }

    @Test
    void patchNonExistingLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().collectList().block().size();
        localisation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, localisation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(localisation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().collectList().block().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(localisation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().collectList().block().size();
        localisation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(localisation))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLocalisation() {
        // Initialize the database
        localisationRepository.save(localisation).block();

        int databaseSizeBeforeDelete = localisationRepository.findAll().collectList().block().size();

        // Delete the localisation
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, localisation.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Localisation> localisationList = localisationRepository.findAll().collectList().block();
        assertThat(localisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
