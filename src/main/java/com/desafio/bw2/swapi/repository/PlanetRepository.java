package com.desafio.bw2.swapi.repository;

import com.desafio.bw2.swapi.model.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> {

    Optional<Planet> findById(String id);
    List<Planet> findAllByNameIgnoreCase(String name);
    List<Planet> findAll();
    Optional<Planet> findTopByOrderByIdDesc();
}
