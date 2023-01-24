package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Collaborateurs;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the Collaborateurs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollaborateursRepository extends ReactiveMongoRepository<Collaborateurs, String> {
    @Query("{}")
    Flux<Collaborateurs> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    Flux<Collaborateurs> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Mono<Collaborateurs> findOneWithEagerRelationships(String id);
}
