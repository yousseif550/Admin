package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Projet;
import com.mycompany.myapp.repository.ProjetRepository;
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
 * Integration tests for the {@link ProjetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ProjetResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_STUCTURE = "AAAAAAAAAA";
    private static final String UPDATED_STUCTURE = "BBBBBBBBBB";

    private static final String DEFAULT_INFORMATIONS = "AAAAAAAAAA";
    private static final String UPDATED_INFORMATIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Projet projet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projet createEntity() {
        Projet projet = new Projet().nom(DEFAULT_NOM).stucture(DEFAULT_STUCTURE).informations(DEFAULT_INFORMATIONS);
        return projet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projet createUpdatedEntity() {
        Projet projet = new Projet().nom(UPDATED_NOM).stucture(UPDATED_STUCTURE).informations(UPDATED_INFORMATIONS);
        return projet;
    }

    @BeforeEach
    public void initTest() {
        projetRepository.deleteAll().block();
        projet = createEntity();
    }

    @Test
    void createProjet() throws Exception {
        int databaseSizeBeforeCreate = projetRepository.findAll().collectList().block().size();
        // Create the Projet
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projet))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate + 1);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProjet.getStucture()).isEqualTo(DEFAULT_STUCTURE);
        assertThat(testProjet.getInformations()).isEqualTo(DEFAULT_INFORMATIONS);
    }

    @Test
    void createProjetWithExistingId() throws Exception {
        // Create the Projet with an existing ID
        projet.setId("existing_id");

        int databaseSizeBeforeCreate = projetRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllProjetsAsStream() {
        // Initialize the database
        projetRepository.save(projet).block();

        List<Projet> projetList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Projet.class)
            .getResponseBody()
            .filter(projet::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(projetList).isNotNull();
        assertThat(projetList).hasSize(1);
        Projet testProjet = projetList.get(0);
        assertThat(testProjet.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProjet.getStucture()).isEqualTo(DEFAULT_STUCTURE);
        assertThat(testProjet.getInformations()).isEqualTo(DEFAULT_INFORMATIONS);
    }

    @Test
    void getAllProjets() {
        // Initialize the database
        projetRepository.save(projet).block();

        // Get all the projetList
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
            .value(hasItem(projet.getId()))
            .jsonPath("$.[*].nom")
            .value(hasItem(DEFAULT_NOM))
            .jsonPath("$.[*].stucture")
            .value(hasItem(DEFAULT_STUCTURE))
            .jsonPath("$.[*].informations")
            .value(hasItem(DEFAULT_INFORMATIONS));
    }

    @Test
    void getProjet() {
        // Initialize the database
        projetRepository.save(projet).block();

        // Get the projet
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, projet.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(projet.getId()))
            .jsonPath("$.nom")
            .value(is(DEFAULT_NOM))
            .jsonPath("$.stucture")
            .value(is(DEFAULT_STUCTURE))
            .jsonPath("$.informations")
            .value(is(DEFAULT_INFORMATIONS));
    }

    @Test
    void getNonExistingProjet() {
        // Get the projet
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingProjet() throws Exception {
        // Initialize the database
        projetRepository.save(projet).block();

        int databaseSizeBeforeUpdate = projetRepository.findAll().collectList().block().size();

        // Update the projet
        Projet updatedProjet = projetRepository.findById(projet.getId()).block();
        updatedProjet.nom(UPDATED_NOM).stucture(UPDATED_STUCTURE).informations(UPDATED_INFORMATIONS);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedProjet.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedProjet))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProjet.getStucture()).isEqualTo(UPDATED_STUCTURE);
        assertThat(testProjet.getInformations()).isEqualTo(UPDATED_INFORMATIONS);
    }

    @Test
    void putNonExistingProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().collectList().block().size();
        projet.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, projet.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().collectList().block().size();
        projet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().collectList().block().size();
        projet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projet))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProjetWithPatch() throws Exception {
        // Initialize the database
        projetRepository.save(projet).block();

        int databaseSizeBeforeUpdate = projetRepository.findAll().collectList().block().size();

        // Update the projet using partial update
        Projet partialUpdatedProjet = new Projet();
        partialUpdatedProjet.setId(projet.getId());

        partialUpdatedProjet.informations(UPDATED_INFORMATIONS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProjet.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProjet))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProjet.getStucture()).isEqualTo(DEFAULT_STUCTURE);
        assertThat(testProjet.getInformations()).isEqualTo(UPDATED_INFORMATIONS);
    }

    @Test
    void fullUpdateProjetWithPatch() throws Exception {
        // Initialize the database
        projetRepository.save(projet).block();

        int databaseSizeBeforeUpdate = projetRepository.findAll().collectList().block().size();

        // Update the projet using partial update
        Projet partialUpdatedProjet = new Projet();
        partialUpdatedProjet.setId(projet.getId());

        partialUpdatedProjet.nom(UPDATED_NOM).stucture(UPDATED_STUCTURE).informations(UPDATED_INFORMATIONS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProjet.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProjet))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProjet.getStucture()).isEqualTo(UPDATED_STUCTURE);
        assertThat(testProjet.getInformations()).isEqualTo(UPDATED_INFORMATIONS);
    }

    @Test
    void patchNonExistingProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().collectList().block().size();
        projet.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, projet.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(projet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().collectList().block().size();
        projet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(projet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().collectList().block().size();
        projet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(projet))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProjet() {
        // Initialize the database
        projetRepository.save(projet).block();

        int databaseSizeBeforeDelete = projetRepository.findAll().collectList().block().size();

        // Delete the projet
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, projet.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Projet> projetList = projetRepository.findAll().collectList().block();
        assertThat(projetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
