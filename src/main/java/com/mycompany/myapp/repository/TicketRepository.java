package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the Ticket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketRepository extends ReactiveMongoRepository<Ticket, String> {
    @Query("{}")
    Flux<Ticket> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    Flux<Ticket> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Mono<Ticket> findOneWithEagerRelationships(String id);
}
