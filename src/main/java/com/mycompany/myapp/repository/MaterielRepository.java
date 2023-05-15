package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Materiel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the Materiel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterielRepository extends ReactiveMongoRepository<Materiel, String> {
    @Query("{}")
    Flux<Materiel> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    Flux<Materiel> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Mono<Materiel> findOneWithEagerRelationships(String id);
}
