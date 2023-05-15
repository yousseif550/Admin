package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Suivi;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.repository.SuiviRepository;
import java.time.Duration;
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
 * Integration tests for the {@link SuiviResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class SuiviResourceIT {

    private static final Etat DEFAULT_ENVOI_KIT_ACCUEIL = Etat.OK;
    private static final Etat UPDATED_ENVOI_KIT_ACCUEIL = Etat.NonRealiser;

    private static final Etat DEFAULT_DOCUMENT_SIGNER = Etat.OK;
    private static final Etat UPDATED_DOCUMENT_SIGNER = Etat.NonRealiser;

    private static final Etat DEFAULT_COMMANDE_PC_DOM = Etat.OK;
    private static final Etat UPDATED_COMMANDE_PC_DOM = Etat.NonRealiser;

    private static final Etat DEFAULT_COMPTE_SSG = Etat.OK;
    private static final Etat UPDATED_COMPTE_SSG = Etat.NonRealiser;

    private static final Etat DEFAULT_LISTE_NTIC = Etat.OK;
    private static final Etat UPDATED_LISTE_NTIC = Etat.NonRealiser;

    private static final Etat DEFAULT_ACCES_TEAMS = Etat.OK;
    private static final Etat UPDATED_ACCES_TEAMS = Etat.NonRealiser;

    private static final Etat DEFAULT_ACCES_PULSE_DG_FI_P = Etat.OK;
    private static final Etat UPDATED_ACCES_PULSE_DG_FI_P = Etat.NonRealiser;

    private static final Etat DEFAULT_PROFIL_PC_DOM = Etat.OK;
    private static final Etat UPDATED_PROFIL_PC_DOM = Etat.NonRealiser;

    private static final Etat DEFAULT_COMMANDER_PCDG_FI_P = Etat.OK;
    private static final Etat UPDATED_COMMANDER_PCDG_FI_P = Etat.NonRealiser;

    private static final Etat DEFAULT_CREATION_BALPDG_FI_P = Etat.OK;
    private static final Etat UPDATED_CREATION_BALPDG_FI_P = Etat.NonRealiser;

    private static final Etat DEFAULT_CREATION_COMPTE_AD = Etat.OK;
    private static final Etat UPDATED_CREATION_COMPTE_AD = Etat.NonRealiser;

    private static final Etat DEFAULT_SOCLAGE_PC = Etat.OK;
    private static final Etat UPDATED_SOCLAGE_PC = Etat.NonRealiser;

    private static final Etat DEFAULT_DMOCSS_IP_TT = Etat.OK;
    private static final Etat UPDATED_DMOCSS_IP_TT = Etat.NonRealiser;

    private static final Etat DEFAULT_INSTALLATION_LOGICIEL = Etat.OK;
    private static final Etat UPDATED_INSTALLATION_LOGICIEL = Etat.NonRealiser;

    private static final String DEFAULT_COMMENTAIRES = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/suivis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SuiviRepository suiviRepository;

    @Mock
    private SuiviRepository suiviRepositoryMock;

    @Autowired
    private WebTestClient webTestClient;

    private Suivi suivi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suivi createEntity() {
        Suivi suivi = new Suivi()
            .envoiKitAccueil(DEFAULT_ENVOI_KIT_ACCUEIL)
            .documentSigner(DEFAULT_DOCUMENT_SIGNER)
            .commandePCDom(DEFAULT_COMMANDE_PC_DOM)
            .compteSSG(DEFAULT_COMPTE_SSG)
            .listeNTIC(DEFAULT_LISTE_NTIC)
            .accesTeams(DEFAULT_ACCES_TEAMS)
            .accesPulseDGFiP(DEFAULT_ACCES_PULSE_DG_FI_P)
            .profilPCDom(DEFAULT_PROFIL_PC_DOM)
            .commanderPCDGFiP(DEFAULT_COMMANDER_PCDG_FI_P)
            .creationBALPDGFiP(DEFAULT_CREATION_BALPDG_FI_P)
            .creationCompteAD(DEFAULT_CREATION_COMPTE_AD)
            .soclagePC(DEFAULT_SOCLAGE_PC)
            .dmocssIpTT(DEFAULT_DMOCSS_IP_TT)
            .installationLogiciel(DEFAULT_INSTALLATION_LOGICIEL)
            .commentaires(DEFAULT_COMMENTAIRES);
        return suivi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suivi createUpdatedEntity() {
        Suivi suivi = new Suivi()
            .envoiKitAccueil(UPDATED_ENVOI_KIT_ACCUEIL)
            .documentSigner(UPDATED_DOCUMENT_SIGNER)
            .commandePCDom(UPDATED_COMMANDE_PC_DOM)
            .compteSSG(UPDATED_COMPTE_SSG)
            .listeNTIC(UPDATED_LISTE_NTIC)
            .accesTeams(UPDATED_ACCES_TEAMS)
            .accesPulseDGFiP(UPDATED_ACCES_PULSE_DG_FI_P)
            .profilPCDom(UPDATED_PROFIL_PC_DOM)
            .commanderPCDGFiP(UPDATED_COMMANDER_PCDG_FI_P)
            .creationBALPDGFiP(UPDATED_CREATION_BALPDG_FI_P)
            .creationCompteAD(UPDATED_CREATION_COMPTE_AD)
            .soclagePC(UPDATED_SOCLAGE_PC)
            .dmocssIpTT(UPDATED_DMOCSS_IP_TT)
            .installationLogiciel(UPDATED_INSTALLATION_LOGICIEL)
            .commentaires(UPDATED_COMMENTAIRES);
        return suivi;
    }

    @BeforeEach
    public void initTest() {
        suiviRepository.deleteAll().block();
        suivi = createEntity();
    }

    @Test
    void createSuivi() throws Exception {
        int databaseSizeBeforeCreate = suiviRepository.findAll().collectList().block().size();
        // Create the Suivi
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(suivi))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeCreate + 1);
        Suivi testSuivi = suiviList.get(suiviList.size() - 1);
        assertThat(testSuivi.getEnvoiKitAccueil()).isEqualTo(DEFAULT_ENVOI_KIT_ACCUEIL);
        assertThat(testSuivi.getDocumentSigner()).isEqualTo(DEFAULT_DOCUMENT_SIGNER);
        assertThat(testSuivi.getCommandePCDom()).isEqualTo(DEFAULT_COMMANDE_PC_DOM);
        assertThat(testSuivi.getCompteSSG()).isEqualTo(DEFAULT_COMPTE_SSG);
        assertThat(testSuivi.getListeNTIC()).isEqualTo(DEFAULT_LISTE_NTIC);
        assertThat(testSuivi.getAccesTeams()).isEqualTo(DEFAULT_ACCES_TEAMS);
        assertThat(testSuivi.getAccesPulseDGFiP()).isEqualTo(DEFAULT_ACCES_PULSE_DG_FI_P);
        assertThat(testSuivi.getProfilPCDom()).isEqualTo(DEFAULT_PROFIL_PC_DOM);
        assertThat(testSuivi.getCommanderPCDGFiP()).isEqualTo(DEFAULT_COMMANDER_PCDG_FI_P);
        assertThat(testSuivi.getCreationBALPDGFiP()).isEqualTo(DEFAULT_CREATION_BALPDG_FI_P);
        assertThat(testSuivi.getCreationCompteAD()).isEqualTo(DEFAULT_CREATION_COMPTE_AD);
        assertThat(testSuivi.getSoclagePC()).isEqualTo(DEFAULT_SOCLAGE_PC);
        assertThat(testSuivi.getDmocssIpTT()).isEqualTo(DEFAULT_DMOCSS_IP_TT);
        assertThat(testSuivi.getInstallationLogiciel()).isEqualTo(DEFAULT_INSTALLATION_LOGICIEL);
        assertThat(testSuivi.getCommentaires()).isEqualTo(DEFAULT_COMMENTAIRES);
    }

    @Test
    void createSuiviWithExistingId() throws Exception {
        // Create the Suivi with an existing ID
        suivi.setId("existing_id");

        int databaseSizeBeforeCreate = suiviRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(suivi))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSuivisAsStream() {
        // Initialize the database
        suiviRepository.save(suivi).block();

        List<Suivi> suiviList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Suivi.class)
            .getResponseBody()
            .filter(suivi::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(suiviList).isNotNull();
        assertThat(suiviList).hasSize(1);
        Suivi testSuivi = suiviList.get(0);
        assertThat(testSuivi.getEnvoiKitAccueil()).isEqualTo(DEFAULT_ENVOI_KIT_ACCUEIL);
        assertThat(testSuivi.getDocumentSigner()).isEqualTo(DEFAULT_DOCUMENT_SIGNER);
        assertThat(testSuivi.getCommandePCDom()).isEqualTo(DEFAULT_COMMANDE_PC_DOM);
        assertThat(testSuivi.getCompteSSG()).isEqualTo(DEFAULT_COMPTE_SSG);
        assertThat(testSuivi.getListeNTIC()).isEqualTo(DEFAULT_LISTE_NTIC);
        assertThat(testSuivi.getAccesTeams()).isEqualTo(DEFAULT_ACCES_TEAMS);
        assertThat(testSuivi.getAccesPulseDGFiP()).isEqualTo(DEFAULT_ACCES_PULSE_DG_FI_P);
        assertThat(testSuivi.getProfilPCDom()).isEqualTo(DEFAULT_PROFIL_PC_DOM);
        assertThat(testSuivi.getCommanderPCDGFiP()).isEqualTo(DEFAULT_COMMANDER_PCDG_FI_P);
        assertThat(testSuivi.getCreationBALPDGFiP()).isEqualTo(DEFAULT_CREATION_BALPDG_FI_P);
        assertThat(testSuivi.getCreationCompteAD()).isEqualTo(DEFAULT_CREATION_COMPTE_AD);
        assertThat(testSuivi.getSoclagePC()).isEqualTo(DEFAULT_SOCLAGE_PC);
        assertThat(testSuivi.getDmocssIpTT()).isEqualTo(DEFAULT_DMOCSS_IP_TT);
        assertThat(testSuivi.getInstallationLogiciel()).isEqualTo(DEFAULT_INSTALLATION_LOGICIEL);
        assertThat(testSuivi.getCommentaires()).isEqualTo(DEFAULT_COMMENTAIRES);
    }

    @Test
    void getAllSuivis() {
        // Initialize the database
        suiviRepository.save(suivi).block();

        // Get all the suiviList
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
            .value(hasItem(suivi.getId()))
            .jsonPath("$.[*].envoiKitAccueil")
            .value(hasItem(DEFAULT_ENVOI_KIT_ACCUEIL.toString()))
            .jsonPath("$.[*].documentSigner")
            .value(hasItem(DEFAULT_DOCUMENT_SIGNER.toString()))
            .jsonPath("$.[*].commandePCDom")
            .value(hasItem(DEFAULT_COMMANDE_PC_DOM.toString()))
            .jsonPath("$.[*].compteSSG")
            .value(hasItem(DEFAULT_COMPTE_SSG.toString()))
            .jsonPath("$.[*].listeNTIC")
            .value(hasItem(DEFAULT_LISTE_NTIC.toString()))
            .jsonPath("$.[*].accesTeams")
            .value(hasItem(DEFAULT_ACCES_TEAMS.toString()))
            .jsonPath("$.[*].accesPulseDGFiP")
            .value(hasItem(DEFAULT_ACCES_PULSE_DG_FI_P.toString()))
            .jsonPath("$.[*].profilPCDom")
            .value(hasItem(DEFAULT_PROFIL_PC_DOM.toString()))
            .jsonPath("$.[*].commanderPCDGFiP")
            .value(hasItem(DEFAULT_COMMANDER_PCDG_FI_P.toString()))
            .jsonPath("$.[*].creationBALPDGFiP")
            .value(hasItem(DEFAULT_CREATION_BALPDG_FI_P.toString()))
            .jsonPath("$.[*].creationCompteAD")
            .value(hasItem(DEFAULT_CREATION_COMPTE_AD.toString()))
            .jsonPath("$.[*].soclagePC")
            .value(hasItem(DEFAULT_SOCLAGE_PC.toString()))
            .jsonPath("$.[*].dmocssIpTT")
            .value(hasItem(DEFAULT_DMOCSS_IP_TT.toString()))
            .jsonPath("$.[*].installationLogiciel")
            .value(hasItem(DEFAULT_INSTALLATION_LOGICIEL.toString()))
            .jsonPath("$.[*].commentaires")
            .value(hasItem(DEFAULT_COMMENTAIRES));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSuivisWithEagerRelationshipsIsEnabled() {
        when(suiviRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(suiviRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSuivisWithEagerRelationshipsIsNotEnabled() {
        when(suiviRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(suiviRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getSuivi() {
        // Initialize the database
        suiviRepository.save(suivi).block();

        // Get the suivi
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, suivi.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(suivi.getId()))
            .jsonPath("$.envoiKitAccueil")
            .value(is(DEFAULT_ENVOI_KIT_ACCUEIL.toString()))
            .jsonPath("$.documentSigner")
            .value(is(DEFAULT_DOCUMENT_SIGNER.toString()))
            .jsonPath("$.commandePCDom")
            .value(is(DEFAULT_COMMANDE_PC_DOM.toString()))
            .jsonPath("$.compteSSG")
            .value(is(DEFAULT_COMPTE_SSG.toString()))
            .jsonPath("$.listeNTIC")
            .value(is(DEFAULT_LISTE_NTIC.toString()))
            .jsonPath("$.accesTeams")
            .value(is(DEFAULT_ACCES_TEAMS.toString()))
            .jsonPath("$.accesPulseDGFiP")
            .value(is(DEFAULT_ACCES_PULSE_DG_FI_P.toString()))
            .jsonPath("$.profilPCDom")
            .value(is(DEFAULT_PROFIL_PC_DOM.toString()))
            .jsonPath("$.commanderPCDGFiP")
            .value(is(DEFAULT_COMMANDER_PCDG_FI_P.toString()))
            .jsonPath("$.creationBALPDGFiP")
            .value(is(DEFAULT_CREATION_BALPDG_FI_P.toString()))
            .jsonPath("$.creationCompteAD")
            .value(is(DEFAULT_CREATION_COMPTE_AD.toString()))
            .jsonPath("$.soclagePC")
            .value(is(DEFAULT_SOCLAGE_PC.toString()))
            .jsonPath("$.dmocssIpTT")
            .value(is(DEFAULT_DMOCSS_IP_TT.toString()))
            .jsonPath("$.installationLogiciel")
            .value(is(DEFAULT_INSTALLATION_LOGICIEL.toString()))
            .jsonPath("$.commentaires")
            .value(is(DEFAULT_COMMENTAIRES));
    }

    @Test
    void getNonExistingSuivi() {
        // Get the suivi
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingSuivi() throws Exception {
        // Initialize the database
        suiviRepository.save(suivi).block();

        int databaseSizeBeforeUpdate = suiviRepository.findAll().collectList().block().size();

        // Update the suivi
        Suivi updatedSuivi = suiviRepository.findById(suivi.getId()).block();
        updatedSuivi
            .envoiKitAccueil(UPDATED_ENVOI_KIT_ACCUEIL)
            .documentSigner(UPDATED_DOCUMENT_SIGNER)
            .commandePCDom(UPDATED_COMMANDE_PC_DOM)
            .compteSSG(UPDATED_COMPTE_SSG)
            .listeNTIC(UPDATED_LISTE_NTIC)
            .accesTeams(UPDATED_ACCES_TEAMS)
            .accesPulseDGFiP(UPDATED_ACCES_PULSE_DG_FI_P)
            .profilPCDom(UPDATED_PROFIL_PC_DOM)
            .commanderPCDGFiP(UPDATED_COMMANDER_PCDG_FI_P)
            .creationBALPDGFiP(UPDATED_CREATION_BALPDG_FI_P)
            .creationCompteAD(UPDATED_CREATION_COMPTE_AD)
            .soclagePC(UPDATED_SOCLAGE_PC)
            .dmocssIpTT(UPDATED_DMOCSS_IP_TT)
            .installationLogiciel(UPDATED_INSTALLATION_LOGICIEL)
            .commentaires(UPDATED_COMMENTAIRES);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedSuivi.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedSuivi))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeUpdate);
        Suivi testSuivi = suiviList.get(suiviList.size() - 1);
        assertThat(testSuivi.getEnvoiKitAccueil()).isEqualTo(UPDATED_ENVOI_KIT_ACCUEIL);
        assertThat(testSuivi.getDocumentSigner()).isEqualTo(UPDATED_DOCUMENT_SIGNER);
        assertThat(testSuivi.getCommandePCDom()).isEqualTo(UPDATED_COMMANDE_PC_DOM);
        assertThat(testSuivi.getCompteSSG()).isEqualTo(UPDATED_COMPTE_SSG);
        assertThat(testSuivi.getListeNTIC()).isEqualTo(UPDATED_LISTE_NTIC);
        assertThat(testSuivi.getAccesTeams()).isEqualTo(UPDATED_ACCES_TEAMS);
        assertThat(testSuivi.getAccesPulseDGFiP()).isEqualTo(UPDATED_ACCES_PULSE_DG_FI_P);
        assertThat(testSuivi.getProfilPCDom()).isEqualTo(UPDATED_PROFIL_PC_DOM);
        assertThat(testSuivi.getCommanderPCDGFiP()).isEqualTo(UPDATED_COMMANDER_PCDG_FI_P);
        assertThat(testSuivi.getCreationBALPDGFiP()).isEqualTo(UPDATED_CREATION_BALPDG_FI_P);
        assertThat(testSuivi.getCreationCompteAD()).isEqualTo(UPDATED_CREATION_COMPTE_AD);
        assertThat(testSuivi.getSoclagePC()).isEqualTo(UPDATED_SOCLAGE_PC);
        assertThat(testSuivi.getDmocssIpTT()).isEqualTo(UPDATED_DMOCSS_IP_TT);
        assertThat(testSuivi.getInstallationLogiciel()).isEqualTo(UPDATED_INSTALLATION_LOGICIEL);
        assertThat(testSuivi.getCommentaires()).isEqualTo(UPDATED_COMMENTAIRES);
    }

    @Test
    void putNonExistingSuivi() throws Exception {
        int databaseSizeBeforeUpdate = suiviRepository.findAll().collectList().block().size();
        suivi.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, suivi.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(suivi))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSuivi() throws Exception {
        int databaseSizeBeforeUpdate = suiviRepository.findAll().collectList().block().size();
        suivi.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(suivi))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSuivi() throws Exception {
        int databaseSizeBeforeUpdate = suiviRepository.findAll().collectList().block().size();
        suivi.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(suivi))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSuiviWithPatch() throws Exception {
        // Initialize the database
        suiviRepository.save(suivi).block();

        int databaseSizeBeforeUpdate = suiviRepository.findAll().collectList().block().size();

        // Update the suivi using partial update
        Suivi partialUpdatedSuivi = new Suivi();
        partialUpdatedSuivi.setId(suivi.getId());

        partialUpdatedSuivi
            .documentSigner(UPDATED_DOCUMENT_SIGNER)
            .compteSSG(UPDATED_COMPTE_SSG)
            .accesPulseDGFiP(UPDATED_ACCES_PULSE_DG_FI_P)
            .profilPCDom(UPDATED_PROFIL_PC_DOM)
            .creationBALPDGFiP(UPDATED_CREATION_BALPDG_FI_P)
            .creationCompteAD(UPDATED_CREATION_COMPTE_AD);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSuivi.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSuivi))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeUpdate);
        Suivi testSuivi = suiviList.get(suiviList.size() - 1);
        assertThat(testSuivi.getEnvoiKitAccueil()).isEqualTo(DEFAULT_ENVOI_KIT_ACCUEIL);
        assertThat(testSuivi.getDocumentSigner()).isEqualTo(UPDATED_DOCUMENT_SIGNER);
        assertThat(testSuivi.getCommandePCDom()).isEqualTo(DEFAULT_COMMANDE_PC_DOM);
        assertThat(testSuivi.getCompteSSG()).isEqualTo(UPDATED_COMPTE_SSG);
        assertThat(testSuivi.getListeNTIC()).isEqualTo(DEFAULT_LISTE_NTIC);
        assertThat(testSuivi.getAccesTeams()).isEqualTo(DEFAULT_ACCES_TEAMS);
        assertThat(testSuivi.getAccesPulseDGFiP()).isEqualTo(UPDATED_ACCES_PULSE_DG_FI_P);
        assertThat(testSuivi.getProfilPCDom()).isEqualTo(UPDATED_PROFIL_PC_DOM);
        assertThat(testSuivi.getCommanderPCDGFiP()).isEqualTo(DEFAULT_COMMANDER_PCDG_FI_P);
        assertThat(testSuivi.getCreationBALPDGFiP()).isEqualTo(UPDATED_CREATION_BALPDG_FI_P);
        assertThat(testSuivi.getCreationCompteAD()).isEqualTo(UPDATED_CREATION_COMPTE_AD);
        assertThat(testSuivi.getSoclagePC()).isEqualTo(DEFAULT_SOCLAGE_PC);
        assertThat(testSuivi.getDmocssIpTT()).isEqualTo(DEFAULT_DMOCSS_IP_TT);
        assertThat(testSuivi.getInstallationLogiciel()).isEqualTo(DEFAULT_INSTALLATION_LOGICIEL);
        assertThat(testSuivi.getCommentaires()).isEqualTo(DEFAULT_COMMENTAIRES);
    }

    @Test
    void fullUpdateSuiviWithPatch() throws Exception {
        // Initialize the database
        suiviRepository.save(suivi).block();

        int databaseSizeBeforeUpdate = suiviRepository.findAll().collectList().block().size();

        // Update the suivi using partial update
        Suivi partialUpdatedSuivi = new Suivi();
        partialUpdatedSuivi.setId(suivi.getId());

        partialUpdatedSuivi
            .envoiKitAccueil(UPDATED_ENVOI_KIT_ACCUEIL)
            .documentSigner(UPDATED_DOCUMENT_SIGNER)
            .commandePCDom(UPDATED_COMMANDE_PC_DOM)
            .compteSSG(UPDATED_COMPTE_SSG)
            .listeNTIC(UPDATED_LISTE_NTIC)
            .accesTeams(UPDATED_ACCES_TEAMS)
            .accesPulseDGFiP(UPDATED_ACCES_PULSE_DG_FI_P)
            .profilPCDom(UPDATED_PROFIL_PC_DOM)
            .commanderPCDGFiP(UPDATED_COMMANDER_PCDG_FI_P)
            .creationBALPDGFiP(UPDATED_CREATION_BALPDG_FI_P)
            .creationCompteAD(UPDATED_CREATION_COMPTE_AD)
            .soclagePC(UPDATED_SOCLAGE_PC)
            .dmocssIpTT(UPDATED_DMOCSS_IP_TT)
            .installationLogiciel(UPDATED_INSTALLATION_LOGICIEL)
            .commentaires(UPDATED_COMMENTAIRES);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSuivi.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSuivi))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeUpdate);
        Suivi testSuivi = suiviList.get(suiviList.size() - 1);
        assertThat(testSuivi.getEnvoiKitAccueil()).isEqualTo(UPDATED_ENVOI_KIT_ACCUEIL);
        assertThat(testSuivi.getDocumentSigner()).isEqualTo(UPDATED_DOCUMENT_SIGNER);
        assertThat(testSuivi.getCommandePCDom()).isEqualTo(UPDATED_COMMANDE_PC_DOM);
        assertThat(testSuivi.getCompteSSG()).isEqualTo(UPDATED_COMPTE_SSG);
        assertThat(testSuivi.getListeNTIC()).isEqualTo(UPDATED_LISTE_NTIC);
        assertThat(testSuivi.getAccesTeams()).isEqualTo(UPDATED_ACCES_TEAMS);
        assertThat(testSuivi.getAccesPulseDGFiP()).isEqualTo(UPDATED_ACCES_PULSE_DG_FI_P);
        assertThat(testSuivi.getProfilPCDom()).isEqualTo(UPDATED_PROFIL_PC_DOM);
        assertThat(testSuivi.getCommanderPCDGFiP()).isEqualTo(UPDATED_COMMANDER_PCDG_FI_P);
        assertThat(testSuivi.getCreationBALPDGFiP()).isEqualTo(UPDATED_CREATION_BALPDG_FI_P);
        assertThat(testSuivi.getCreationCompteAD()).isEqualTo(UPDATED_CREATION_COMPTE_AD);
        assertThat(testSuivi.getSoclagePC()).isEqualTo(UPDATED_SOCLAGE_PC);
        assertThat(testSuivi.getDmocssIpTT()).isEqualTo(UPDATED_DMOCSS_IP_TT);
        assertThat(testSuivi.getInstallationLogiciel()).isEqualTo(UPDATED_INSTALLATION_LOGICIEL);
        assertThat(testSuivi.getCommentaires()).isEqualTo(UPDATED_COMMENTAIRES);
    }

    @Test
    void patchNonExistingSuivi() throws Exception {
        int databaseSizeBeforeUpdate = suiviRepository.findAll().collectList().block().size();
        suivi.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, suivi.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(suivi))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSuivi() throws Exception {
        int databaseSizeBeforeUpdate = suiviRepository.findAll().collectList().block().size();
        suivi.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(suivi))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSuivi() throws Exception {
        int databaseSizeBeforeUpdate = suiviRepository.findAll().collectList().block().size();
        suivi.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(suivi))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSuivi() {
        // Initialize the database
        suiviRepository.save(suivi).block();

        int databaseSizeBeforeDelete = suiviRepository.findAll().collectList().block().size();

        // Delete the suivi
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, suivi.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Suivi> suiviList = suiviRepository.findAll().collectList().block();
        assertThat(suiviList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
