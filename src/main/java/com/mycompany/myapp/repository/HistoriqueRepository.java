package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Historique;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Historique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriqueRepository extends ReactiveMongoRepository<Historique, String> {}
