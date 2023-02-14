package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Ticket;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.repository.TicketRepository;
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
 * Integration tests for the {@link TicketResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TicketResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Etat DEFAULT_STATUT = Etat.OK;
    private static final Etat UPDATED_STATUT = Etat.NonRealiser;

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/tickets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Ticket ticket;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ticket createEntity() {
        Ticket ticket = new Ticket()
            .type(DEFAULT_TYPE)
            .statut(DEFAULT_STATUT)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateFin(DEFAULT_DATE_FIN);
        return ticket;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ticket createUpdatedEntity() {
        Ticket ticket = new Ticket()
            .type(UPDATED_TYPE)
            .statut(UPDATED_STATUT)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateFin(UPDATED_DATE_FIN);
        return ticket;
    }

    @BeforeEach
    public void initTest() {
        ticketRepository.deleteAll().block();
        ticket = createEntity();
    }

    @Test
    void createTicket() throws Exception {
        int databaseSizeBeforeCreate = ticketRepository.findAll().collectList().block().size();
        // Create the Ticket
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(ticket))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeCreate + 1);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTicket.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testTicket.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testTicket.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    void createTicketWithExistingId() throws Exception {
        // Create the Ticket with an existing ID
        ticket.setId("existing_id");

        int databaseSizeBeforeCreate = ticketRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(ticket))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTicketsAsStream() {
        // Initialize the database
        ticketRepository.save(ticket).block();

        List<Ticket> ticketList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Ticket.class)
            .getResponseBody()
            .filter(ticket::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(ticketList).isNotNull();
        assertThat(ticketList).hasSize(1);
        Ticket testTicket = ticketList.get(0);
        assertThat(testTicket.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTicket.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testTicket.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testTicket.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    void getAllTickets() {
        // Initialize the database
        ticketRepository.save(ticket).block();

        // Get all the ticketList
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
            .value(hasItem(ticket.getId()))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE))
            .jsonPath("$.[*].statut")
            .value(hasItem(DEFAULT_STATUT.toString()))
            .jsonPath("$.[*].dateCreation")
            .value(hasItem(DEFAULT_DATE_CREATION.toString()))
            .jsonPath("$.[*].dateFin")
            .value(hasItem(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    void getTicket() {
        // Initialize the database
        ticketRepository.save(ticket).block();

        // Get the ticket
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, ticket.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(ticket.getId()))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE))
            .jsonPath("$.statut")
            .value(is(DEFAULT_STATUT.toString()))
            .jsonPath("$.dateCreation")
            .value(is(DEFAULT_DATE_CREATION.toString()))
            .jsonPath("$.dateFin")
            .value(is(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    void getNonExistingTicket() {
        // Get the ticket
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTicket() throws Exception {
        // Initialize the database
        ticketRepository.save(ticket).block();

        int databaseSizeBeforeUpdate = ticketRepository.findAll().collectList().block().size();

        // Update the ticket
        Ticket updatedTicket = ticketRepository.findById(ticket.getId()).block();
        updatedTicket.type(UPDATED_TYPE).statut(UPDATED_STATUT).dateCreation(UPDATED_DATE_CREATION).dateFin(UPDATED_DATE_FIN);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedTicket.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedTicket))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTicket.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testTicket.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testTicket.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    void putNonExistingTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().collectList().block().size();
        ticket.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, ticket.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(ticket))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().collectList().block().size();
        ticket.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(ticket))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().collectList().block().size();
        ticket.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(ticket))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTicketWithPatch() throws Exception {
        // Initialize the database
        ticketRepository.save(ticket).block();

        int databaseSizeBeforeUpdate = ticketRepository.findAll().collectList().block().size();

        // Update the ticket using partial update
        Ticket partialUpdatedTicket = new Ticket();
        partialUpdatedTicket.setId(ticket.getId());

        partialUpdatedTicket.dateCreation(UPDATED_DATE_CREATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTicket.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTicket))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTicket.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testTicket.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testTicket.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    void fullUpdateTicketWithPatch() throws Exception {
        // Initialize the database
        ticketRepository.save(ticket).block();

        int databaseSizeBeforeUpdate = ticketRepository.findAll().collectList().block().size();

        // Update the ticket using partial update
        Ticket partialUpdatedTicket = new Ticket();
        partialUpdatedTicket.setId(ticket.getId());

        partialUpdatedTicket.type(UPDATED_TYPE).statut(UPDATED_STATUT).dateCreation(UPDATED_DATE_CREATION).dateFin(UPDATED_DATE_FIN);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTicket.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTicket))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTicket.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testTicket.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testTicket.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    void patchNonExistingTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().collectList().block().size();
        ticket.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, ticket.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(ticket))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().collectList().block().size();
        ticket.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(ticket))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().collectList().block().size();
        ticket.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(ticket))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTicket() {
        // Initialize the database
        ticketRepository.save(ticket).block();

        int databaseSizeBeforeDelete = ticketRepository.findAll().collectList().block().size();

        // Delete the ticket
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, ticket.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Ticket> ticketList = ticketRepository.findAll().collectList().block();
        assertThat(ticketList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
