package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.InformationsTech;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the InformationsTech entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformationsTechRepository extends ReactiveMongoRepository<InformationsTech, String> {
    @Query("{}")
    Flux<InformationsTech> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    Flux<InformationsTech> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Mono<InformationsTech> findOneWithEagerRelationships(String id);
}
