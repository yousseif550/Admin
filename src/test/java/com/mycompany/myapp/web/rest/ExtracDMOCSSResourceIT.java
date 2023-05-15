package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ExtracDMOCSS;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.repository.ExtracDMOCSSRepository;
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
 * Integration tests for the {@link ExtracDMOCSSResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ExtracDMOCSSResourceIT {

    private static final String DEFAULT_ADRESSE_PHYSIQUE_DG_FI_P = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_PHYSIQUE_DG_FI_P = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IP_PC_DGFIP = "AAAAAAAAAA";
    private static final String UPDATED_IP_PC_DGFIP = "BBBBBBBBBB";

    private static final String DEFAULT_IP_VPN_IPSEC = "AAAAAAAAAA";
    private static final String UPDATED_IP_VPN_IPSEC = "BBBBBBBBBB";

    private static final String DEFAULT_IO_TELETRAVAIL = "AAAAAAAAAA";
    private static final String UPDATED_IO_TELETRAVAIL = "BBBBBBBBBB";

    private static final Etat DEFAULT_STATUT = Etat.OK;
    private static final Etat UPDATED_STATUT = Etat.NonRealiser;

    private static final String DEFAULT_NUM_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_NUM_VERSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/extrac-dmocsses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ExtracDMOCSSRepository extracDMOCSSRepository;

    @Mock
    private ExtracDMOCSSRepository extracDMOCSSRepositoryMock;

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
            .date(DEFAULT_DATE)
            .ipPcDgfip(DEFAULT_IP_PC_DGFIP)
            .ipVpnIPSEC(DEFAULT_IP_VPN_IPSEC)
            .ioTeletravail(DEFAULT_IO_TELETRAVAIL)
            .statut(DEFAULT_STATUT)
            .numVersion(DEFAULT_NUM_VERSION);
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
            .date(UPDATED_DATE)
            .ipPcDgfip(UPDATED_IP_PC_DGFIP)
            .ipVpnIPSEC(UPDATED_IP_VPN_IPSEC)
            .ioTeletravail(UPDATED_IO_TELETRAVAIL)
            .statut(UPDATED_STATUT)
            .numVersion(UPDATED_NUM_VERSION);
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
        assertThat(testExtracDMOCSS.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testExtracDMOCSS.getIpPcDgfip()).isEqualTo(DEFAULT_IP_PC_DGFIP);
        assertThat(testExtracDMOCSS.getIpVpnIPSEC()).isEqualTo(DEFAULT_IP_VPN_IPSEC);
        assertThat(testExtracDMOCSS.getIoTeletravail()).isEqualTo(DEFAULT_IO_TELETRAVAIL);
        assertThat(testExtracDMOCSS.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testExtracDMOCSS.getNumVersion()).isEqualTo(DEFAULT_NUM_VERSION);
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
        assertThat(testExtracDMOCSS.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testExtracDMOCSS.getIpPcDgfip()).isEqualTo(DEFAULT_IP_PC_DGFIP);
        assertThat(testExtracDMOCSS.getIpVpnIPSEC()).isEqualTo(DEFAULT_IP_VPN_IPSEC);
        assertThat(testExtracDMOCSS.getIoTeletravail()).isEqualTo(DEFAULT_IO_TELETRAVAIL);
        assertThat(testExtracDMOCSS.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testExtracDMOCSS.getNumVersion()).isEqualTo(DEFAULT_NUM_VERSION);
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
            .jsonPath("$.[*].date")
            .value(hasItem(DEFAULT_DATE.toString()))
            .jsonPath("$.[*].ipPcDgfip")
            .value(hasItem(DEFAULT_IP_PC_DGFIP))
            .jsonPath("$.[*].ipVpnIPSEC")
            .value(hasItem(DEFAULT_IP_VPN_IPSEC))
            .jsonPath("$.[*].ioTeletravail")
            .value(hasItem(DEFAULT_IO_TELETRAVAIL))
            .jsonPath("$.[*].statut")
            .value(hasItem(DEFAULT_STATUT.toString()))
            .jsonPath("$.[*].numVersion")
            .value(hasItem(DEFAULT_NUM_VERSION));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllExtracDMOCSSESWithEagerRelationshipsIsEnabled() {
        when(extracDMOCSSRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(extracDMOCSSRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllExtracDMOCSSESWithEagerRelationshipsIsNotEnabled() {
        when(extracDMOCSSRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(extracDMOCSSRepositoryMock, times(1)).findAllWithEagerRelationships(any());
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
            .jsonPath("$.date")
            .value(is(DEFAULT_DATE.toString()))
            .jsonPath("$.ipPcDgfip")
            .value(is(DEFAULT_IP_PC_DGFIP))
            .jsonPath("$.ipVpnIPSEC")
            .value(is(DEFAULT_IP_VPN_IPSEC))
            .jsonPath("$.ioTeletravail")
            .value(is(DEFAULT_IO_TELETRAVAIL))
            .jsonPath("$.statut")
            .value(is(DEFAULT_STATUT.toString()))
            .jsonPath("$.numVersion")
            .value(is(DEFAULT_NUM_VERSION));
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
            .date(UPDATED_DATE)
            .ipPcDgfip(UPDATED_IP_PC_DGFIP)
            .ipVpnIPSEC(UPDATED_IP_VPN_IPSEC)
            .ioTeletravail(UPDATED_IO_TELETRAVAIL)
            .statut(UPDATED_STATUT)
            .numVersion(UPDATED_NUM_VERSION);

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
        assertThat(testExtracDMOCSS.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testExtracDMOCSS.getIpPcDgfip()).isEqualTo(UPDATED_IP_PC_DGFIP);
        assertThat(testExtracDMOCSS.getIpVpnIPSEC()).isEqualTo(UPDATED_IP_VPN_IPSEC);
        assertThat(testExtracDMOCSS.getIoTeletravail()).isEqualTo(UPDATED_IO_TELETRAVAIL);
        assertThat(testExtracDMOCSS.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testExtracDMOCSS.getNumVersion()).isEqualTo(UPDATED_NUM_VERSION);
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
            .date(UPDATED_DATE)
            .ipVpnIPSEC(UPDATED_IP_VPN_IPSEC)
            .numVersion(UPDATED_NUM_VERSION);

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
        assertThat(testExtracDMOCSS.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testExtracDMOCSS.getIpPcDgfip()).isEqualTo(DEFAULT_IP_PC_DGFIP);
        assertThat(testExtracDMOCSS.getIpVpnIPSEC()).isEqualTo(UPDATED_IP_VPN_IPSEC);
        assertThat(testExtracDMOCSS.getIoTeletravail()).isEqualTo(DEFAULT_IO_TELETRAVAIL);
        assertThat(testExtracDMOCSS.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testExtracDMOCSS.getNumVersion()).isEqualTo(UPDATED_NUM_VERSION);
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
            .date(UPDATED_DATE)
            .ipPcDgfip(UPDATED_IP_PC_DGFIP)
            .ipVpnIPSEC(UPDATED_IP_VPN_IPSEC)
            .ioTeletravail(UPDATED_IO_TELETRAVAIL)
            .statut(UPDATED_STATUT)
            .numVersion(UPDATED_NUM_VERSION);

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
        assertThat(testExtracDMOCSS.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testExtracDMOCSS.getIpPcDgfip()).isEqualTo(UPDATED_IP_PC_DGFIP);
        assertThat(testExtracDMOCSS.getIpVpnIPSEC()).isEqualTo(UPDATED_IP_VPN_IPSEC);
        assertThat(testExtracDMOCSS.getIoTeletravail()).isEqualTo(UPDATED_IO_TELETRAVAIL);
        assertThat(testExtracDMOCSS.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testExtracDMOCSS.getNumVersion()).isEqualTo(UPDATED_NUM_VERSION);
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
