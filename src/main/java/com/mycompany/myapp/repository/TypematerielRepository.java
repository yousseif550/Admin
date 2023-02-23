package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Typemateriel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Typemateriel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypematerielRepository extends ReactiveMongoRepository<Typemateriel, String> {}
