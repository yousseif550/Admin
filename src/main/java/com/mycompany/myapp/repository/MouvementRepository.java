package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Mouvement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the Mouvement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementRepository extends ReactiveMongoRepository<Mouvement, String> {
    @Query("{}")
    Flux<Mouvement> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    Flux<Mouvement> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Mono<Mouvement> findOneWithEagerRelationships(String id);
}
