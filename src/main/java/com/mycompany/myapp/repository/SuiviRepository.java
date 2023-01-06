package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Suivi;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Suivi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuiviRepository extends ReactiveMongoRepository<Suivi, String> {}
