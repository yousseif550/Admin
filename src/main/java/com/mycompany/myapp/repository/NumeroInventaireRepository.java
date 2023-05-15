package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NumeroInventaire;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the NumeroInventaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumeroInventaireRepository extends ReactiveMongoRepository<NumeroInventaire, String> {
    @Query("{}")
    Flux<NumeroInventaire> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    Flux<NumeroInventaire> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Mono<NumeroInventaire> findOneWithEagerRelationships(String id);
}
