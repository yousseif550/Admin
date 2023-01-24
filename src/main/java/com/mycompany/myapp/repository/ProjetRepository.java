package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Projet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Projet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjetRepository extends ReactiveMongoRepository<Projet, String> {}
