package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Collaborateurs;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Collaborateurs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollaborateursRepository extends ReactiveMongoRepository<Collaborateurs, String> {}
