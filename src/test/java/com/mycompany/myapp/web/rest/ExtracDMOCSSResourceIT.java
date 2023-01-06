package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ExtracDMOCSS;
import com.mycompany.myapp.repository.ExtracDMOCSSRepository;
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
 * Integration tests for the {@link ExtracDMOCSSResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ExtracDMOCSSResourceIT {

    private static final String DEFAULT_ADRESSE_PHYSIQUE_DG_FI_P = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_PHYSIQUE_DG_FI_P = "BBBBBBBBBB";

    private static final String DEFAULT_BUREAU_ACTUEL = "AAAAAAAAAA";
    private static final String UPDATED_BUREAU_ACTUEL = "BBBBBBBBBB";

    private static final String DEFAULT_BUREAU_DEPLACEMENT = "AAAAAAAAAA";
    private static final String UPDATED_BUREAU_DEPLACEMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IP_PC_DG_FI_P = "AAAAAAAAAA";
    private static final String UPDATED_IP_PC_DG_FI_P = "BBBBBBBBBB";

    private static final String DEFAULT_IP_VPN_IPSEC = "AAAAAAAAAA";
    private static final String UPDATED_IP_VPN_IPSEC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/extrac-dmocsses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ExtracDMOCSSRepository extracDMOCSSRepository;

    @Autowired
    private WebTestClient webTestClient;

    private ExtracDMOCSS extracDMOCSS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtracDMOCSS createEntity() {
        ExtracDMOCSS extracDMOCSS = new ExtracDMOCSS()
            .adressePhysiqueDGFiP(DEFAULT_ADRESSE_PHYSIQUE_DG_FI_P)
            .bureauActuel(DEFAULT_BUREAU_ACTUEL)
            .bureauDeplacement(DEFAULT_BUREAU_DEPLACEMENT)
            .date(DEFAULT_DATE)
            .ipPcDGFiP(DEFAULT_IP_PC_DG_FI_P)
            .ipVpnIPSEC(DEFAULT_IP_VPN_IPSEC);
        return extracDMOCSS;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtracDMOCSS createUpdatedEntity() {
        ExtracDMOCSS extracDMOCSS = new ExtracDMOCSS()
            .adressePhysiqueDGFiP(UPDATED_ADRESSE_PHYSIQUE_DG_FI_P)
            .bureauActuel(UPDATED_BUREAU_ACTUEL)
            .bureauDeplacement(UPDATED_BUREAU_DEPLACEMENT)
            .date(UPDATED_DATE)
            .ipPcDGFiP(UPDATED_IP_PC_DG_FI_P)
            .ipVpnIPSEC(UPDATED_IP_VPN_IPSEC);
        return extracDMOCSS;
    }

    @BeforeEach
    public void initTest() {
        extracDMOCSSRepository.deleteAll().block();
        extracDMOCSS = createEntity();
    }

    @Test
    void createExtracDMOCSS() throws Exception {
        int databaseSizeBeforeCreate = extracDMOCSSRepository.findAll().collectList().block().size();
        // Create the ExtracDMOCSS
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(extracDMOCSS))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ExtracDMOCSS in the database
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeCreate + 1);
        ExtracDMOCSS testExtracDMOCSS = extracDMOCSSList.get(extracDMOCSSList.size() - 1);
        assertThat(testExtracDMOCSS.getAdressePhysiqueDGFiP()).isEqualTo(DEFAULT_ADRESSE_PHYSIQUE_DG_FI_P);
        assertThat(testExtracDMOCSS.getBureauActuel()).isEqualTo(DEFAULT_BUREAU_ACTUEL);
        assertThat(testExtracDMOCSS.getBureauDeplacement()).isEqualTo(DEFAULT_BUREAU_DEPLACEMENT);
        assertThat(testExtracDMOCSS.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testExtracDMOCSS.getIpPcDGFiP()).isEqualTo(DEFAULT_IP_PC_DG_FI_P);
        assertThat(testExtracDMOCSS.getIpVpnIPSEC()).isEqualTo(DEFAULT_IP_VPN_IPSEC);
    }

    @Test
    void createExtracDMOCSSWithExistingId() throws Exception {
        // Create the ExtracDMOCSS with an existing ID
        extracDMOCSS.setId("existing_id");

        int databaseSizeBeforeCreate = extracDMOCSSRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(extracDMOCSS))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ExtracDMOCSS in the database
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllExtracDMOCSSESAsStream() {
        // Initialize the database
        extracDMOCSSRepository.save(extracDMOCSS).block();

        List<ExtracDMOCSS> extracDMOCSSList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(ExtracDMOCSS.class)
            .getResponseBody()
            .filter(extracDMOCSS::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(extracDMOCSSList).isNotNull();
        assertThat(extracDMOCSSList).hasSize(1);
        ExtracDMOCSS testExtracDMOCSS = extracDMOCSSList.get(0);
        assertThat(testExtracDMOCSS.getAdressePhysiqueDGFiP()).isEqualTo(DEFAULT_ADRESSE_PHYSIQUE_DG_FI_P);
        assertThat(testExtracDMOCSS.getBureauActuel()).isEqualTo(DEFAULT_BUREAU_ACTUEL);
        assertThat(testExtracDMOCSS.getBureauDeplacement()).isEqualTo(DEFAULT_BUREAU_DEPLACEMENT);
        assertThat(testExtracDMOCSS.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testExtracDMOCSS.getIpPcDGFiP()).isEqualTo(DEFAULT_IP_PC_DG_FI_P);
        assertThat(testExtracDMOCSS.getIpVpnIPSEC()).isEqualTo(DEFAULT_IP_VPN_IPSEC);
    }

    @Test
    void getAllExtracDMOCSSES() {
        // Initialize the database
        extracDMOCSSRepository.save(extracDMOCSS).block();

        // Get all the extracDMOCSSList
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
            .value(hasItem(extracDMOCSS.getId()))
            .jsonPath("$.[*].adressePhysiqueDGFiP")
            .value(hasItem(DEFAULT_ADRESSE_PHYSIQUE_DG_FI_P))
            .jsonPath("$.[*].bureauActuel")
            .value(hasItem(DEFAULT_BUREAU_ACTUEL))
            .jsonPath("$.[*].bureauDeplacement")
            .value(hasItem(DEFAULT_BUREAU_DEPLACEMENT))
            .jsonPath("$.[*].date")
            .value(hasItem(DEFAULT_DATE.toString()))
            .jsonPath("$.[*].ipPcDGFiP")
            .value(hasItem(DEFAULT_IP_PC_DG_FI_P))
            .jsonPath("$.[*].ipVpnIPSEC")
            .value(hasItem(DEFAULT_IP_VPN_IPSEC));
    }

    @Test
    void getExtracDMOCSS() {
        // Initialize the database
        extracDMOCSSRepository.save(extracDMOCSS).block();

        // Get the extracDMOCSS
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, extracDMOCSS.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(extracDMOCSS.getId()))
            .jsonPath("$.adressePhysiqueDGFiP")
            .value(is(DEFAULT_ADRESSE_PHYSIQUE_DG_FI_P))
            .jsonPath("$.bureauActuel")
            .value(is(DEFAULT_BUREAU_ACTUEL))
            .jsonPath("$.bureauDeplacement")
            .value(is(DEFAULT_BUREAU_DEPLACEMENT))
            .jsonPath("$.date")
            .value(is(DEFAULT_DATE.toString()))
            .jsonPath("$.ipPcDGFiP")
            .value(is(DEFAULT_IP_PC_DG_FI_P))
            .jsonPath("$.ipVpnIPSEC")
            .value(is(DEFAULT_IP_VPN_IPSEC));
    }

    @Test
    void getNonExistingExtracDMOCSS() {
        // Get the extracDMOCSS
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingExtracDMOCSS() throws Exception {
        // Initialize the database
        extracDMOCSSRepository.save(extracDMOCSS).block();

        int databaseSizeBeforeUpdate = extracDMOCSSRepository.findAll().collectList().block().size();

        // Update the extracDMOCSS
        ExtracDMOCSS updatedExtracDMOCSS = extracDMOCSSRepository.findById(extracDMOCSS.getId()).block();
        updatedExtracDMOCSS
            .adressePhysiqueDGFiP(UPDATED_ADRESSE_PHYSIQUE_DG_FI_P)
            .bureauActuel(UPDATED_BUREAU_ACTUEL)
            .bureauDeplacement(UPDATED_BUREAU_DEPLACEMENT)
            .date(UPDATED_DATE)
            .ipPcDGFiP(UPDATED_IP_PC_DG_FI_P)
            .ipVpnIPSEC(UPDATED_IP_VPN_IPSEC);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedExtracDMOCSS.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedExtracDMOCSS))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ExtracDMOCSS in the database
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeUpdate);
        ExtracDMOCSS testExtracDMOCSS = extracDMOCSSList.get(extracDMOCSSList.size() - 1);
        assertThat(testExtracDMOCSS.getAdressePhysiqueDGFiP()).isEqualTo(UPDATED_ADRESSE_PHYSIQUE_DG_FI_P);
        assertThat(testExtracDMOCSS.getBureauActuel()).isEqualTo(UPDATED_BUREAU_ACTUEL);
        assertThat(testExtracDMOCSS.getBureauDeplacement()).isEqualTo(UPDATED_BUREAU_DEPLACEMENT);
        assertThat(testExtracDMOCSS.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testExtracDMOCSS.getIpPcDGFiP()).isEqualTo(UPDATED_IP_PC_DG_FI_P);
        assertThat(testExtracDMOCSS.getIpVpnIPSEC()).isEqualTo(UPDATED_IP_VPN_IPSEC);
    }

    @Test
    void putNonExistingExtracDMOCSS() throws Exception {
        int databaseSizeBeforeUpdate = extracDMOCSSRepository.findAll().collectList().block().size();
        extracDMOCSS.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, extracDMOCSS.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(extracDMOCSS))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ExtracDMOCSS in the database
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchExtracDMOCSS() throws Exception {
        int databaseSizeBeforeUpdate = extracDMOCSSRepository.findAll().collectList().block().size();
        extracDMOCSS.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(extracDMOCSS))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ExtracDMOCSS in the database
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamExtracDMOCSS() throws Exception {
        int databaseSizeBeforeUpdate = extracDMOCSSRepository.findAll().collectList().block().size();
        extracDMOCSS.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(extracDMOCSS))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ExtracDMOCSS in the database
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateExtracDMOCSSWithPatch() throws Exception {
        // Initialize the database
        extracDMOCSSRepository.save(extracDMOCSS).block();

        int databaseSizeBeforeUpdate = extracDMOCSSRepository.findAll().collectList().block().size();

        // Update the extracDMOCSS using partial update
        ExtracDMOCSS partialUpdatedExtracDMOCSS = new ExtracDMOCSS();
        partialUpdatedExtracDMOCSS.setId(extracDMOCSS.getId());

        partialUpdatedExtracDMOCSS
            .adressePhysiqueDGFiP(UPDATED_ADRESSE_PHYSIQUE_DG_FI_P)
            .bureauActuel(UPDATED_BUREAU_ACTUEL)
            .date(UPDATED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedExtracDMOCSS.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedExtracDMOCSS))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ExtracDMOCSS in the database
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeUpdate);
        ExtracDMOCSS testExtracDMOCSS = extracDMOCSSList.get(extracDMOCSSList.size() - 1);
        assertThat(testExtracDMOCSS.getAdressePhysiqueDGFiP()).isEqualTo(UPDATED_ADRESSE_PHYSIQUE_DG_FI_P);
        assertThat(testExtracDMOCSS.getBureauActuel()).isEqualTo(UPDATED_BUREAU_ACTUEL);
        assertThat(testExtracDMOCSS.getBureauDeplacement()).isEqualTo(DEFAULT_BUREAU_DEPLACEMENT);
        assertThat(testExtracDMOCSS.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testExtracDMOCSS.getIpPcDGFiP()).isEqualTo(DEFAULT_IP_PC_DG_FI_P);
        assertThat(testExtracDMOCSS.getIpVpnIPSEC()).isEqualTo(DEFAULT_IP_VPN_IPSEC);
    }

    @Test
    void fullUpdateExtracDMOCSSWithPatch() throws Exception {
        // Initialize the database
        extracDMOCSSRepository.save(extracDMOCSS).block();

        int databaseSizeBeforeUpdate = extracDMOCSSRepository.findAll().collectList().block().size();

        // Update the extracDMOCSS using partial update
        ExtracDMOCSS partialUpdatedExtracDMOCSS = new ExtracDMOCSS();
        partialUpdatedExtracDMOCSS.setId(extracDMOCSS.getId());

        partialUpdatedExtracDMOCSS
            .adressePhysiqueDGFiP(UPDATED_ADRESSE_PHYSIQUE_DG_FI_P)
            .bureauActuel(UPDATED_BUREAU_ACTUEL)
            .bureauDeplacement(UPDATED_BUREAU_DEPLACEMENT)
            .date(UPDATED_DATE)
            .ipPcDGFiP(UPDATED_IP_PC_DG_FI_P)
            .ipVpnIPSEC(UPDATED_IP_VPN_IPSEC);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedExtracDMOCSS.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedExtracDMOCSS))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ExtracDMOCSS in the database
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeUpdate);
        ExtracDMOCSS testExtracDMOCSS = extracDMOCSSList.get(extracDMOCSSList.size() - 1);
        assertThat(testExtracDMOCSS.getAdressePhysiqueDGFiP()).isEqualTo(UPDATED_ADRESSE_PHYSIQUE_DG_FI_P);
        assertThat(testExtracDMOCSS.getBureauActuel()).isEqualTo(UPDATED_BUREAU_ACTUEL);
        assertThat(testExtracDMOCSS.getBureauDeplacement()).isEqualTo(UPDATED_BUREAU_DEPLACEMENT);
        assertThat(testExtracDMOCSS.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testExtracDMOCSS.getIpPcDGFiP()).isEqualTo(UPDATED_IP_PC_DG_FI_P);
        assertThat(testExtracDMOCSS.getIpVpnIPSEC()).isEqualTo(UPDATED_IP_VPN_IPSEC);
    }

    @Test
    void patchNonExistingExtracDMOCSS() throws Exception {
        int databaseSizeBeforeUpdate = extracDMOCSSRepository.findAll().collectList().block().size();
        extracDMOCSS.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, extracDMOCSS.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(extracDMOCSS))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ExtracDMOCSS in the database
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchExtracDMOCSS() throws Exception {
        int databaseSizeBeforeUpdate = extracDMOCSSRepository.findAll().collectList().block().size();
        extracDMOCSS.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(extracDMOCSS))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ExtracDMOCSS in the database
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamExtracDMOCSS() throws Exception {
        int databaseSizeBeforeUpdate = extracDMOCSSRepository.findAll().collectList().block().size();
        extracDMOCSS.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(extracDMOCSS))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ExtracDMOCSS in the database
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteExtracDMOCSS() {
        // Initialize the database
        extracDMOCSSRepository.save(extracDMOCSS).block();

        int databaseSizeBeforeDelete = extracDMOCSSRepository.findAll().collectList().block().size();

        // Delete the extracDMOCSS
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, extracDMOCSS.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ExtracDMOCSS> extracDMOCSSList = extracDMOCSSRepository.findAll().collectList().block();
        assertThat(extracDMOCSSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
