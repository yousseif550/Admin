package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Suivi;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the Suivi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuiviRepository extends ReactiveMongoRepository<Suivi, String> {
    @Query("{}")
    Flux<Suivi> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    Flux<Suivi> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Mono<Suivi> findOneWithEagerRelationships(String id);
}
