package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Collaborateurs;
import com.mycompany.myapp.repository.CollaborateursRepository;
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
 * Integration tests for the {@link CollaborateursResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class CollaborateursResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Long DEFAULT_IDENTIFIANT = 1L;
    private static final Long UPDATED_IDENTIFIANT = 2L;

    private static final Long DEFAULT_TEL = 1L;
    private static final Long UPDATED_TEL = 2L;

    private static final Boolean DEFAULT_PRESTATAIRE = false;
    private static final Boolean UPDATED_PRESTATAIRE = true;

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final LocalDate DEFAULT_DATE_ENTREE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENTREE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_SORTIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SORTIE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/collaborateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CollaborateursRepository collaborateursRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Collaborateurs collaborateurs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaborateurs createEntity() {
        Collaborateurs collaborateurs = new Collaborateurs()
            .nom(DEFAULT_NOM)
            .identifiant(DEFAULT_IDENTIFIANT)
            .tel(DEFAULT_TEL)
            .prestataire(DEFAULT_PRESTATAIRE)
            .isActif(DEFAULT_IS_ACTIF)
            .dateEntree(DEFAULT_DATE_ENTREE)
            .dateSortie(DEFAULT_DATE_SORTIE);
        return collaborateurs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaborateurs createUpdatedEntity() {
        Collaborateurs collaborateurs = new Collaborateurs()
            .nom(UPDATED_NOM)
            .identifiant(UPDATED_IDENTIFIANT)
            .tel(UPDATED_TEL)
            .prestataire(UPDATED_PRESTATAIRE)
            .isActif(UPDATED_IS_ACTIF)
            .dateEntree(UPDATED_DATE_ENTREE)
            .dateSortie(UPDATED_DATE_SORTIE);
        return collaborateurs;
    }

    @BeforeEach
    public void initTest() {
        collaborateursRepository.deleteAll().block();
        collaborateurs = createEntity();
    }

    @Test
    void createCollaborateurs() throws Exception {
        int databaseSizeBeforeCreate = collaborateursRepository.findAll().collectList().block().size();
        // Create the Collaborateurs
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(collaborateurs))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Collaborateurs in the database
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeCreate + 1);
        Collaborateurs testCollaborateurs = collaborateursList.get(collaborateursList.size() - 1);
        assertThat(testCollaborateurs.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCollaborateurs.getIdentifiant()).isEqualTo(DEFAULT_IDENTIFIANT);
        assertThat(testCollaborateurs.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testCollaborateurs.getPrestataire()).isEqualTo(DEFAULT_PRESTATAIRE);
        assertThat(testCollaborateurs.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testCollaborateurs.getDateEntree()).isEqualTo(DEFAULT_DATE_ENTREE);
        assertThat(testCollaborateurs.getDateSortie()).isEqualTo(DEFAULT_DATE_SORTIE);
    }

    @Test
    void createCollaborateursWithExistingId() throws Exception {
        // Create the Collaborateurs with an existing ID
        collaborateurs.setId("existing_id");

        int databaseSizeBeforeCreate = collaborateursRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(collaborateurs))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Collaborateurs in the database
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCollaborateursAsStream() {
        // Initialize the database
        collaborateursRepository.save(collaborateurs).block();

        List<Collaborateurs> collaborateursList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Collaborateurs.class)
            .getResponseBody()
            .filter(collaborateurs::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(collaborateursList).isNotNull();
        assertThat(collaborateursList).hasSize(1);
        Collaborateurs testCollaborateurs = collaborateursList.get(0);
        assertThat(testCollaborateurs.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCollaborateurs.getIdentifiant()).isEqualTo(DEFAULT_IDENTIFIANT);
        assertThat(testCollaborateurs.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testCollaborateurs.getPrestataire()).isEqualTo(DEFAULT_PRESTATAIRE);
        assertThat(testCollaborateurs.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testCollaborateurs.getDateEntree()).isEqualTo(DEFAULT_DATE_ENTREE);
        assertThat(testCollaborateurs.getDateSortie()).isEqualTo(DEFAULT_DATE_SORTIE);
    }

    @Test
    void getAllCollaborateurs() {
        // Initialize the database
        collaborateursRepository.save(collaborateurs).block();

        // Get all the collaborateursList
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
            .value(hasItem(collaborateurs.getId()))
            .jsonPath("$.[*].nom")
            .value(hasItem(DEFAULT_NOM))
            .jsonPath("$.[*].identifiant")
            .value(hasItem(DEFAULT_IDENTIFIANT.intValue()))
            .jsonPath("$.[*].tel")
            .value(hasItem(DEFAULT_TEL.intValue()))
            .jsonPath("$.[*].prestataire")
            .value(hasItem(DEFAULT_PRESTATAIRE.booleanValue()))
            .jsonPath("$.[*].isActif")
            .value(hasItem(DEFAULT_IS_ACTIF.booleanValue()))
            .jsonPath("$.[*].dateEntree")
            .value(hasItem(DEFAULT_DATE_ENTREE.toString()))
            .jsonPath("$.[*].dateSortie")
            .value(hasItem(DEFAULT_DATE_SORTIE.toString()));
    }

    @Test
    void getCollaborateurs() {
        // Initialize the database
        collaborateursRepository.save(collaborateurs).block();

        // Get the collaborateurs
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, collaborateurs.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(collaborateurs.getId()))
            .jsonPath("$.nom")
            .value(is(DEFAULT_NOM))
            .jsonPath("$.identifiant")
            .value(is(DEFAULT_IDENTIFIANT.intValue()))
            .jsonPath("$.tel")
            .value(is(DEFAULT_TEL.intValue()))
            .jsonPath("$.prestataire")
            .value(is(DEFAULT_PRESTATAIRE.booleanValue()))
            .jsonPath("$.isActif")
            .value(is(DEFAULT_IS_ACTIF.booleanValue()))
            .jsonPath("$.dateEntree")
            .value(is(DEFAULT_DATE_ENTREE.toString()))
            .jsonPath("$.dateSortie")
            .value(is(DEFAULT_DATE_SORTIE.toString()));
    }

    @Test
    void getNonExistingCollaborateurs() {
        // Get the collaborateurs
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingCollaborateurs() throws Exception {
        // Initialize the database
        collaborateursRepository.save(collaborateurs).block();

        int databaseSizeBeforeUpdate = collaborateursRepository.findAll().collectList().block().size();

        // Update the collaborateurs
        Collaborateurs updatedCollaborateurs = collaborateursRepository.findById(collaborateurs.getId()).block();
        updatedCollaborateurs
            .nom(UPDATED_NOM)
            .identifiant(UPDATED_IDENTIFIANT)
            .tel(UPDATED_TEL)
            .prestataire(UPDATED_PRESTATAIRE)
            .isActif(UPDATED_IS_ACTIF)
            .dateEntree(UPDATED_DATE_ENTREE)
            .dateSortie(UPDATED_DATE_SORTIE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedCollaborateurs.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedCollaborateurs))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Collaborateurs in the database
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeUpdate);
        Collaborateurs testCollaborateurs = collaborateursList.get(collaborateursList.size() - 1);
        assertThat(testCollaborateurs.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCollaborateurs.getIdentifiant()).isEqualTo(UPDATED_IDENTIFIANT);
        assertThat(testCollaborateurs.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testCollaborateurs.getPrestataire()).isEqualTo(UPDATED_PRESTATAIRE);
        assertThat(testCollaborateurs.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testCollaborateurs.getDateEntree()).isEqualTo(UPDATED_DATE_ENTREE);
        assertThat(testCollaborateurs.getDateSortie()).isEqualTo(UPDATED_DATE_SORTIE);
    }

    @Test
    void putNonExistingCollaborateurs() throws Exception {
        int databaseSizeBeforeUpdate = collaborateursRepository.findAll().collectList().block().size();
        collaborateurs.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, collaborateurs.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(collaborateurs))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Collaborateurs in the database
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCollaborateurs() throws Exception {
        int databaseSizeBeforeUpdate = collaborateursRepository.findAll().collectList().block().size();
        collaborateurs.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(collaborateurs))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Collaborateurs in the database
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCollaborateurs() throws Exception {
        int databaseSizeBeforeUpdate = collaborateursRepository.findAll().collectList().block().size();
        collaborateurs.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(collaborateurs))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Collaborateurs in the database
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCollaborateursWithPatch() throws Exception {
        // Initialize the database
        collaborateursRepository.save(collaborateurs).block();

        int databaseSizeBeforeUpdate = collaborateursRepository.findAll().collectList().block().size();

        // Update the collaborateurs using partial update
        Collaborateurs partialUpdatedCollaborateurs = new Collaborateurs();
        partialUpdatedCollaborateurs.setId(collaborateurs.getId());

        partialUpdatedCollaborateurs.tel(UPDATED_TEL).dateEntree(UPDATED_DATE_ENTREE).dateSortie(UPDATED_DATE_SORTIE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCollaborateurs.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCollaborateurs))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Collaborateurs in the database
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeUpdate);
        Collaborateurs testCollaborateurs = collaborateursList.get(collaborateursList.size() - 1);
        assertThat(testCollaborateurs.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCollaborateurs.getIdentifiant()).isEqualTo(DEFAULT_IDENTIFIANT);
        assertThat(testCollaborateurs.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testCollaborateurs.getPrestataire()).isEqualTo(DEFAULT_PRESTATAIRE);
        assertThat(testCollaborateurs.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testCollaborateurs.getDateEntree()).isEqualTo(UPDATED_DATE_ENTREE);
        assertThat(testCollaborateurs.getDateSortie()).isEqualTo(UPDATED_DATE_SORTIE);
    }

    @Test
    void fullUpdateCollaborateursWithPatch() throws Exception {
        // Initialize the database
        collaborateursRepository.save(collaborateurs).block();

        int databaseSizeBeforeUpdate = collaborateursRepository.findAll().collectList().block().size();

        // Update the collaborateurs using partial update
        Collaborateurs partialUpdatedCollaborateurs = new Collaborateurs();
        partialUpdatedCollaborateurs.setId(collaborateurs.getId());

        partialUpdatedCollaborateurs
            .nom(UPDATED_NOM)
            .identifiant(UPDATED_IDENTIFIANT)
            .tel(UPDATED_TEL)
            .prestataire(UPDATED_PRESTATAIRE)
            .isActif(UPDATED_IS_ACTIF)
            .dateEntree(UPDATED_DATE_ENTREE)
            .dateSortie(UPDATED_DATE_SORTIE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCollaborateurs.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCollaborateurs))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Collaborateurs in the database
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeUpdate);
        Collaborateurs testCollaborateurs = collaborateursList.get(collaborateursList.size() - 1);
        assertThat(testCollaborateurs.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCollaborateurs.getIdentifiant()).isEqualTo(UPDATED_IDENTIFIANT);
        assertThat(testCollaborateurs.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testCollaborateurs.getPrestataire()).isEqualTo(UPDATED_PRESTATAIRE);
        assertThat(testCollaborateurs.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testCollaborateurs.getDateEntree()).isEqualTo(UPDATED_DATE_ENTREE);
        assertThat(testCollaborateurs.getDateSortie()).isEqualTo(UPDATED_DATE_SORTIE);
    }

    @Test
    void patchNonExistingCollaborateurs() throws Exception {
        int databaseSizeBeforeUpdate = collaborateursRepository.findAll().collectList().block().size();
        collaborateurs.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, collaborateurs.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(collaborateurs))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Collaborateurs in the database
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCollaborateurs() throws Exception {
        int databaseSizeBeforeUpdate = collaborateursRepository.findAll().collectList().block().size();
        collaborateurs.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(collaborateurs))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Collaborateurs in the database
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCollaborateurs() throws Exception {
        int databaseSizeBeforeUpdate = collaborateursRepository.findAll().collectList().block().size();
        collaborateurs.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(collaborateurs))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Collaborateurs in the database
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCollaborateurs() {
        // Initialize the database
        collaborateursRepository.save(collaborateurs).block();

        int databaseSizeBeforeDelete = collaborateursRepository.findAll().collectList().block().size();

        // Delete the collaborateurs
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, collaborateurs.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Collaborateurs> collaborateursList = collaborateursRepository.findAll().collectList().block();
        assertThat(collaborateursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
