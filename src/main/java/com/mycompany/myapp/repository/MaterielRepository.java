package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Materiel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Materiel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterielRepository extends ReactiveMongoRepository<Materiel, String> {}
