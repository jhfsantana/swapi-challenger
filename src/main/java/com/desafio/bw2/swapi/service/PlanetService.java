package com.desafio.bw2.swapi.service;

import com.desafio.bw2.swapi.client.SwapiClient;
import com.desafio.bw2.swapi.model.Film;
import com.desafio.bw2.swapi.model.Planet;
import com.desafio.bw2.swapi.model.PlanetResponse;
import com.desafio.bw2.swapi.repository.PlanetRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    SwapiClient swapiClient;

    public Planet create(Planet planet) throws IOException {
        Gson gson = new Gson();
        String responseJsonString = swapiClient.request("https://swapi.dev/api/planets/?search=" + planet.getName());

        PlanetResponse response = gson.fromJson(responseJsonString, PlanetResponse.class);

        if (response != null) {
            if(response.getCount() == 1) {
                List<String> filmsUrls = response.getResults().get(0).getFilms();

                List<String> titleFilms = new ArrayList<>();

                for(String filmUrl : filmsUrls) {
                    String finalUrl = filmUrl + "?format=json";
                    String jsonString = swapiClient.request(finalUrl);
                    Film film = gson.fromJson(jsonString, Film.class);
                    titleFilms.add(film.getTitle());
                }

                planet.setFilms(titleFilms);
            }
        }

        return planetRepository.save(planet);
    }

    public Optional<Planet> findById(String id) {
        return planetRepository.findById(id);
    }

    public List<Planet> findAllByName(String name) {
        return planetRepository.findAllByNameIgnoreCase(name);
    }

    public Page<Planet> getPlanets(Integer page) {
        int offset = 0;
        int size = 100;

        if(page != null && page > 0)
            offset = page-1;


        Pageable pageRequest =  PageRequest.of(offset, size, Sort.Direction.ASC, "name");
        return planetRepository.findAll(pageRequest);
    }

    public void delete(String id) {
        planetRepository.deleteById(id);
    }

    public Optional<Planet> getLastInsert() {
        return planetRepository.findTopByOrderByIdDesc();
    }
}
