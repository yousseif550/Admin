package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ExtracDMOCSS;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the ExtracDMOCSS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtracDMOCSSRepository extends ReactiveMongoRepository<ExtracDMOCSS, String> {
    @Query("{}")
    Flux<ExtracDMOCSS> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    Flux<ExtracDMOCSS> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Mono<ExtracDMOCSS> findOneWithEagerRelationships(String id);
}
