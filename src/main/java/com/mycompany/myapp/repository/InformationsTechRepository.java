package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.InformationsTech;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the InformationsTech entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformationsTechRepository extends ReactiveMongoRepository<InformationsTech, String> {}
