package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Mouvement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Mouvement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementRepository extends ReactiveMongoRepository<Mouvement, String> {}
