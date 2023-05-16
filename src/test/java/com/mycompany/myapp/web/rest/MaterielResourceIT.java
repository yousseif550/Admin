package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

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
 * Integration tests for the {@link MaterielResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class MaterielResourceIT {

    private static final String DEFAULT_UTILISATION = "AAAAAAAAAA";
    private static final String UPDATED_UTILISATION = "BBBBBBBBBB";

    private static final String DEFAULT_MODELE = "AAAAAAAAAA";
    private static final String UPDATED_MODELE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET = "AAAAAAAAAA";
    private static final String UPDATED_ASSET = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ATTRIBUTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ATTRIBUTION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RENDU = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RENDU = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    private static final Boolean DEFAULT_IS_HS = false;
    private static final Boolean UPDATED_IS_HS = true;

    private static final String DEFAULT_CLE_ANTI_VOL = "AAAAAAAAAA";
    private static final String UPDATED_CLE_ANTI_VOL = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESS_MAC = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS_MAC = "BBBBBBBBBB";

    private static final String DEFAULT_STATION_DGFIP = "AAAAAAAAAA";
    private static final String UPDATED_STATION_DGFIP = "BBBBBBBBBB";

    private static final String DEFAULT_IPDFIP = "AAAAAAAAAA";
    private static final String UPDATED_IPDFIP = "BBBBBBBBBB";

    private static final String DEFAULT_I_P_TELETRAVAIL = "AAAAAAAAAA";
    private static final String UPDATED_I_P_TELETRAVAIL = "BBBBBBBBBB";

    private static final String DEFAULT_BIOS = "AAAAAAAAAA";
    private static final String UPDATED_BIOS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MAJ_BIOS = false;
    private static final Boolean UPDATED_MAJ_BIOS = true;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/materiels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MaterielRepository materielRepository;

    @Mock
    private MaterielRepository materielRepositoryMock;

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
            .utilisation(DEFAULT_UTILISATION)
            .modele(DEFAULT_MODELE)
            .asset(DEFAULT_ASSET)
            .dateAttribution(DEFAULT_DATE_ATTRIBUTION)
            .dateRendu(DEFAULT_DATE_RENDU)
            .actif(DEFAULT_ACTIF)
            .isHs(DEFAULT_IS_HS)
            .cleAntiVol(DEFAULT_CLE_ANTI_VOL)
            .adressMAC(DEFAULT_ADRESS_MAC)
            .stationDgfip(DEFAULT_STATION_DGFIP)
            .ipdfip(DEFAULT_IPDFIP)
            .iPTeletravail(DEFAULT_I_P_TELETRAVAIL)
            .bios(DEFAULT_BIOS)
            .majBios(DEFAULT_MAJ_BIOS)
            .commentaire(DEFAULT_COMMENTAIRE);
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
            .utilisation(UPDATED_UTILISATION)
            .modele(UPDATED_MODELE)
            .asset(UPDATED_ASSET)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .dateRendu(UPDATED_DATE_RENDU)
            .actif(UPDATED_ACTIF)
            .isHs(UPDATED_IS_HS)
            .cleAntiVol(UPDATED_CLE_ANTI_VOL)
            .adressMAC(UPDATED_ADRESS_MAC)
            .stationDgfip(UPDATED_STATION_DGFIP)
            .ipdfip(UPDATED_IPDFIP)
            .iPTeletravail(UPDATED_I_P_TELETRAVAIL)
            .bios(UPDATED_BIOS)
            .majBios(UPDATED_MAJ_BIOS)
            .commentaire(UPDATED_COMMENTAIRE);
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
        assertThat(testMateriel.getUtilisation()).isEqualTo(DEFAULT_UTILISATION);
        assertThat(testMateriel.getModele()).isEqualTo(DEFAULT_MODELE);
        assertThat(testMateriel.getAsset()).isEqualTo(DEFAULT_ASSET);
        assertThat(testMateriel.getDateAttribution()).isEqualTo(DEFAULT_DATE_ATTRIBUTION);
        assertThat(testMateriel.getDateRendu()).isEqualTo(DEFAULT_DATE_RENDU);
        assertThat(testMateriel.getActif()).isEqualTo(DEFAULT_ACTIF);
        assertThat(testMateriel.getIsHs()).isEqualTo(DEFAULT_IS_HS);
        assertThat(testMateriel.getCleAntiVol()).isEqualTo(DEFAULT_CLE_ANTI_VOL);
        assertThat(testMateriel.getAdressMAC()).isEqualTo(DEFAULT_ADRESS_MAC);
        assertThat(testMateriel.getStationDgfip()).isEqualTo(DEFAULT_STATION_DGFIP);
        assertThat(testMateriel.getIpdfip()).isEqualTo(DEFAULT_IPDFIP);
        assertThat(testMateriel.getiPTeletravail()).isEqualTo(DEFAULT_I_P_TELETRAVAIL);
        assertThat(testMateriel.getBios()).isEqualTo(DEFAULT_BIOS);
        assertThat(testMateriel.getMajBios()).isEqualTo(DEFAULT_MAJ_BIOS);
        assertThat(testMateriel.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
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
        assertThat(testMateriel.getUtilisation()).isEqualTo(DEFAULT_UTILISATION);
        assertThat(testMateriel.getModele()).isEqualTo(DEFAULT_MODELE);
        assertThat(testMateriel.getAsset()).isEqualTo(DEFAULT_ASSET);
        assertThat(testMateriel.getDateAttribution()).isEqualTo(DEFAULT_DATE_ATTRIBUTION);
        assertThat(testMateriel.getDateRendu()).isEqualTo(DEFAULT_DATE_RENDU);
        assertThat(testMateriel.getActif()).isEqualTo(DEFAULT_ACTIF);
        assertThat(testMateriel.getIsHs()).isEqualTo(DEFAULT_IS_HS);
        assertThat(testMateriel.getCleAntiVol()).isEqualTo(DEFAULT_CLE_ANTI_VOL);
        assertThat(testMateriel.getAdressMAC()).isEqualTo(DEFAULT_ADRESS_MAC);
        assertThat(testMateriel.getStationDgfip()).isEqualTo(DEFAULT_STATION_DGFIP);
        assertThat(testMateriel.getIpdfip()).isEqualTo(DEFAULT_IPDFIP);
        assertThat(testMateriel.getiPTeletravail()).isEqualTo(DEFAULT_I_P_TELETRAVAIL);
        assertThat(testMateriel.getBios()).isEqualTo(DEFAULT_BIOS);
        assertThat(testMateriel.getMajBios()).isEqualTo(DEFAULT_MAJ_BIOS);
        assertThat(testMateriel.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
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
            .jsonPath("$.[*].utilisation")
            .value(hasItem(DEFAULT_UTILISATION))
            .jsonPath("$.[*].modele")
            .value(hasItem(DEFAULT_MODELE))
            .jsonPath("$.[*].asset")
            .value(hasItem(DEFAULT_ASSET))
            .jsonPath("$.[*].dateAttribution")
            .value(hasItem(DEFAULT_DATE_ATTRIBUTION.toString()))
            .jsonPath("$.[*].dateRendu")
            .value(hasItem(DEFAULT_DATE_RENDU.toString()))
            .jsonPath("$.[*].actif")
            .value(hasItem(DEFAULT_ACTIF.booleanValue()))
            .jsonPath("$.[*].isHs")
            .value(hasItem(DEFAULT_IS_HS.booleanValue()))
            .jsonPath("$.[*].cleAntiVol")
            .value(hasItem(DEFAULT_CLE_ANTI_VOL))
            .jsonPath("$.[*].adressMAC")
            .value(hasItem(DEFAULT_ADRESS_MAC))
            .jsonPath("$.[*].stationDgfip")
            .value(hasItem(DEFAULT_STATION_DGFIP))
            .jsonPath("$.[*].ipdfip")
            .value(hasItem(DEFAULT_IPDFIP))
            .jsonPath("$.[*].iPTeletravail")
            .value(hasItem(DEFAULT_I_P_TELETRAVAIL))
            .jsonPath("$.[*].bios")
            .value(hasItem(DEFAULT_BIOS))
            .jsonPath("$.[*].majBios")
            .value(hasItem(DEFAULT_MAJ_BIOS.booleanValue()))
            .jsonPath("$.[*].commentaire")
            .value(hasItem(DEFAULT_COMMENTAIRE));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMaterielsWithEagerRelationshipsIsEnabled() {
        when(materielRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(materielRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMaterielsWithEagerRelationshipsIsNotEnabled() {
        when(materielRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(materielRepositoryMock, times(1)).findAllWithEagerRelationships(any());
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
            .jsonPath("$.utilisation")
            .value(is(DEFAULT_UTILISATION))
            .jsonPath("$.modele")
            .value(is(DEFAULT_MODELE))
            .jsonPath("$.asset")
            .value(is(DEFAULT_ASSET))
            .jsonPath("$.dateAttribution")
            .value(is(DEFAULT_DATE_ATTRIBUTION.toString()))
            .jsonPath("$.dateRendu")
            .value(is(DEFAULT_DATE_RENDU.toString()))
            .jsonPath("$.actif")
            .value(is(DEFAULT_ACTIF.booleanValue()))
            .jsonPath("$.isHs")
            .value(is(DEFAULT_IS_HS.booleanValue()))
            .jsonPath("$.cleAntiVol")
            .value(is(DEFAULT_CLE_ANTI_VOL))
            .jsonPath("$.adressMAC")
            .value(is(DEFAULT_ADRESS_MAC))
            .jsonPath("$.stationDgfip")
            .value(is(DEFAULT_STATION_DGFIP))
            .jsonPath("$.ipdfip")
            .value(is(DEFAULT_IPDFIP))
            .jsonPath("$.iPTeletravail")
            .value(is(DEFAULT_I_P_TELETRAVAIL))
            .jsonPath("$.bios")
            .value(is(DEFAULT_BIOS))
            .jsonPath("$.majBios")
            .value(is(DEFAULT_MAJ_BIOS.booleanValue()))
            .jsonPath("$.commentaire")
            .value(is(DEFAULT_COMMENTAIRE));
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
            .utilisation(UPDATED_UTILISATION)
            .modele(UPDATED_MODELE)
            .asset(UPDATED_ASSET)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .dateRendu(UPDATED_DATE_RENDU)
            .actif(UPDATED_ACTIF)
            .isHs(UPDATED_IS_HS)
            .cleAntiVol(UPDATED_CLE_ANTI_VOL)
            .adressMAC(UPDATED_ADRESS_MAC)
            .stationDgfip(UPDATED_STATION_DGFIP)
            .ipdfip(UPDATED_IPDFIP)
            .iPTeletravail(UPDATED_I_P_TELETRAVAIL)
            .bios(UPDATED_BIOS)
            .majBios(UPDATED_MAJ_BIOS)
            .commentaire(UPDATED_COMMENTAIRE);

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
        assertThat(testMateriel.getUtilisation()).isEqualTo(UPDATED_UTILISATION);
        assertThat(testMateriel.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testMateriel.getAsset()).isEqualTo(UPDATED_ASSET);
        assertThat(testMateriel.getDateAttribution()).isEqualTo(UPDATED_DATE_ATTRIBUTION);
        assertThat(testMateriel.getDateRendu()).isEqualTo(UPDATED_DATE_RENDU);
        assertThat(testMateriel.getActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testMateriel.getIsHs()).isEqualTo(UPDATED_IS_HS);
        assertThat(testMateriel.getCleAntiVol()).isEqualTo(UPDATED_CLE_ANTI_VOL);
        assertThat(testMateriel.getAdressMAC()).isEqualTo(UPDATED_ADRESS_MAC);
        assertThat(testMateriel.getStationDgfip()).isEqualTo(UPDATED_STATION_DGFIP);
        assertThat(testMateriel.getIpdfip()).isEqualTo(UPDATED_IPDFIP);
        assertThat(testMateriel.getiPTeletravail()).isEqualTo(UPDATED_I_P_TELETRAVAIL);
        assertThat(testMateriel.getBios()).isEqualTo(UPDATED_BIOS);
        assertThat(testMateriel.getMajBios()).isEqualTo(UPDATED_MAJ_BIOS);
        assertThat(testMateriel.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
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
            .utilisation(UPDATED_UTILISATION)
            .modele(UPDATED_MODELE)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .dateRendu(UPDATED_DATE_RENDU)
            .cleAntiVol(UPDATED_CLE_ANTI_VOL)
            .adressMAC(UPDATED_ADRESS_MAC)
            .stationDgfip(UPDATED_STATION_DGFIP)
            .ipdfip(UPDATED_IPDFIP)
            .commentaire(UPDATED_COMMENTAIRE);

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
        assertThat(testMateriel.getUtilisation()).isEqualTo(UPDATED_UTILISATION);
        assertThat(testMateriel.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testMateriel.getAsset()).isEqualTo(DEFAULT_ASSET);
        assertThat(testMateriel.getDateAttribution()).isEqualTo(UPDATED_DATE_ATTRIBUTION);
        assertThat(testMateriel.getDateRendu()).isEqualTo(UPDATED_DATE_RENDU);
        assertThat(testMateriel.getActif()).isEqualTo(DEFAULT_ACTIF);
        assertThat(testMateriel.getIsHs()).isEqualTo(DEFAULT_IS_HS);
        assertThat(testMateriel.getCleAntiVol()).isEqualTo(UPDATED_CLE_ANTI_VOL);
        assertThat(testMateriel.getAdressMAC()).isEqualTo(UPDATED_ADRESS_MAC);
        assertThat(testMateriel.getStationDgfip()).isEqualTo(UPDATED_STATION_DGFIP);
        assertThat(testMateriel.getIpdfip()).isEqualTo(UPDATED_IPDFIP);
        assertThat(testMateriel.getiPTeletravail()).isEqualTo(DEFAULT_I_P_TELETRAVAIL);
        assertThat(testMateriel.getBios()).isEqualTo(DEFAULT_BIOS);
        assertThat(testMateriel.getMajBios()).isEqualTo(DEFAULT_MAJ_BIOS);
        assertThat(testMateriel.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
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
            .utilisation(UPDATED_UTILISATION)
            .modele(UPDATED_MODELE)
            .asset(UPDATED_ASSET)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .dateRendu(UPDATED_DATE_RENDU)
            .actif(UPDATED_ACTIF)
            .isHs(UPDATED_IS_HS)
            .cleAntiVol(UPDATED_CLE_ANTI_VOL)
            .adressMAC(UPDATED_ADRESS_MAC)
            .stationDgfip(UPDATED_STATION_DGFIP)
            .ipdfip(UPDATED_IPDFIP)
            .iPTeletravail(UPDATED_I_P_TELETRAVAIL)
            .bios(UPDATED_BIOS)
            .majBios(UPDATED_MAJ_BIOS)
            .commentaire(UPDATED_COMMENTAIRE);

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
        assertThat(testMateriel.getUtilisation()).isEqualTo(UPDATED_UTILISATION);
        assertThat(testMateriel.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testMateriel.getAsset()).isEqualTo(UPDATED_ASSET);
        assertThat(testMateriel.getDateAttribution()).isEqualTo(UPDATED_DATE_ATTRIBUTION);
        assertThat(testMateriel.getDateRendu()).isEqualTo(UPDATED_DATE_RENDU);
        assertThat(testMateriel.getActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testMateriel.getIsHs()).isEqualTo(UPDATED_IS_HS);
        assertThat(testMateriel.getCleAntiVol()).isEqualTo(UPDATED_CLE_ANTI_VOL);
        assertThat(testMateriel.getAdressMAC()).isEqualTo(UPDATED_ADRESS_MAC);
        assertThat(testMateriel.getStationDgfip()).isEqualTo(UPDATED_STATION_DGFIP);
        assertThat(testMateriel.getIpdfip()).isEqualTo(UPDATED_IPDFIP);
        assertThat(testMateriel.getiPTeletravail()).isEqualTo(UPDATED_I_P_TELETRAVAIL);
        assertThat(testMateriel.getBios()).isEqualTo(UPDATED_BIOS);
        assertThat(testMateriel.getMajBios()).isEqualTo(UPDATED_MAJ_BIOS);
        assertThat(testMateriel.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
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
