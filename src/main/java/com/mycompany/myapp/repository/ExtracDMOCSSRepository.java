package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ExtracDMOCSS;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the ExtracDMOCSS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtracDMOCSSRepository extends ReactiveMongoRepository<ExtracDMOCSS, String> {}
