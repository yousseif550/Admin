package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Typemateriel;
import com.mycompany.myapp.repository.TypematerielRepository;
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
 * Integration tests for the {@link TypematerielResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TypematerielResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/typemateriels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TypematerielRepository typematerielRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Typemateriel typemateriel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Typemateriel createEntity() {
        Typemateriel typemateriel = new Typemateriel().type(DEFAULT_TYPE);
        return typemateriel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Typemateriel createUpdatedEntity() {
        Typemateriel typemateriel = new Typemateriel().type(UPDATED_TYPE);
        return typemateriel;
    }

    @BeforeEach
    public void initTest() {
        typematerielRepository.deleteAll().block();
        typemateriel = createEntity();
    }

    @Test
    void createTypemateriel() throws Exception {
        int databaseSizeBeforeCreate = typematerielRepository.findAll().collectList().block().size();
        // Create the Typemateriel
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(typemateriel))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Typemateriel in the database
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeCreate + 1);
        Typemateriel testTypemateriel = typematerielList.get(typematerielList.size() - 1);
        assertThat(testTypemateriel.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createTypematerielWithExistingId() throws Exception {
        // Create the Typemateriel with an existing ID
        typemateriel.setId("existing_id");

        int databaseSizeBeforeCreate = typematerielRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(typemateriel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Typemateriel in the database
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTypematerielsAsStream() {
        // Initialize the database
        typematerielRepository.save(typemateriel).block();

        List<Typemateriel> typematerielList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Typemateriel.class)
            .getResponseBody()
            .filter(typemateriel::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(typematerielList).isNotNull();
        assertThat(typematerielList).hasSize(1);
        Typemateriel testTypemateriel = typematerielList.get(0);
        assertThat(testTypemateriel.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void getAllTypemateriels() {
        // Initialize the database
        typematerielRepository.save(typemateriel).block();

        // Get all the typematerielList
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
            .value(hasItem(typemateriel.getId()))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE));
    }

    @Test
    void getTypemateriel() {
        // Initialize the database
        typematerielRepository.save(typemateriel).block();

        // Get the typemateriel
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, typemateriel.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(typemateriel.getId()))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingTypemateriel() {
        // Get the typemateriel
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTypemateriel() throws Exception {
        // Initialize the database
        typematerielRepository.save(typemateriel).block();

        int databaseSizeBeforeUpdate = typematerielRepository.findAll().collectList().block().size();

        // Update the typemateriel
        Typemateriel updatedTypemateriel = typematerielRepository.findById(typemateriel.getId()).block();
        updatedTypemateriel.type(UPDATED_TYPE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedTypemateriel.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedTypemateriel))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Typemateriel in the database
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeUpdate);
        Typemateriel testTypemateriel = typematerielList.get(typematerielList.size() - 1);
        assertThat(testTypemateriel.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingTypemateriel() throws Exception {
        int databaseSizeBeforeUpdate = typematerielRepository.findAll().collectList().block().size();
        typemateriel.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, typemateriel.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(typemateriel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Typemateriel in the database
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTypemateriel() throws Exception {
        int databaseSizeBeforeUpdate = typematerielRepository.findAll().collectList().block().size();
        typemateriel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(typemateriel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Typemateriel in the database
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTypemateriel() throws Exception {
        int databaseSizeBeforeUpdate = typematerielRepository.findAll().collectList().block().size();
        typemateriel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(typemateriel))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Typemateriel in the database
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTypematerielWithPatch() throws Exception {
        // Initialize the database
        typematerielRepository.save(typemateriel).block();

        int databaseSizeBeforeUpdate = typematerielRepository.findAll().collectList().block().size();

        // Update the typemateriel using partial update
        Typemateriel partialUpdatedTypemateriel = new Typemateriel();
        partialUpdatedTypemateriel.setId(typemateriel.getId());

        partialUpdatedTypemateriel.type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTypemateriel.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTypemateriel))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Typemateriel in the database
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeUpdate);
        Typemateriel testTypemateriel = typematerielList.get(typematerielList.size() - 1);
        assertThat(testTypemateriel.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void fullUpdateTypematerielWithPatch() throws Exception {
        // Initialize the database
        typematerielRepository.save(typemateriel).block();

        int databaseSizeBeforeUpdate = typematerielRepository.findAll().collectList().block().size();

        // Update the typemateriel using partial update
        Typemateriel partialUpdatedTypemateriel = new Typemateriel();
        partialUpdatedTypemateriel.setId(typemateriel.getId());

        partialUpdatedTypemateriel.type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTypemateriel.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTypemateriel))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Typemateriel in the database
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeUpdate);
        Typemateriel testTypemateriel = typematerielList.get(typematerielList.size() - 1);
        assertThat(testTypemateriel.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingTypemateriel() throws Exception {
        int databaseSizeBeforeUpdate = typematerielRepository.findAll().collectList().block().size();
        typemateriel.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, typemateriel.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(typemateriel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Typemateriel in the database
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTypemateriel() throws Exception {
        int databaseSizeBeforeUpdate = typematerielRepository.findAll().collectList().block().size();
        typemateriel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(typemateriel))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Typemateriel in the database
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTypemateriel() throws Exception {
        int databaseSizeBeforeUpdate = typematerielRepository.findAll().collectList().block().size();
        typemateriel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(typemateriel))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Typemateriel in the database
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTypemateriel() {
        // Initialize the database
        typematerielRepository.save(typemateriel).block();

        int databaseSizeBeforeDelete = typematerielRepository.findAll().collectList().block().size();

        // Delete the typemateriel
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, typemateriel.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Typemateriel> typematerielList = typematerielRepository.findAll().collectList().block();
        assertThat(typematerielList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
