package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NumeroInventaire;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the NumeroInventaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumeroInventaireRepository extends ReactiveMongoRepository<NumeroInventaire, String> {}
