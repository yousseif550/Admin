package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Projet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the Projet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjetRepository extends ReactiveMongoRepository<Projet, String> {
    @Query("{}")
    Flux<Projet> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    Flux<Projet> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Mono<Projet> findOneWithEagerRelationships(String id);
}
