package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Historique;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the Historique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriqueRepository extends ReactiveMongoRepository<Historique, String> {
    @Query("{}")
    Flux<Historique> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    Flux<Historique> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Mono<Historique> findOneWithEagerRelationships(String id);
}
