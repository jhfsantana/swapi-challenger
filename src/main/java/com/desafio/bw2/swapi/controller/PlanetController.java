package com.desafio.bw2.swapi.controller;

import com.desafio.bw2.swapi.model.Planet;
import com.desafio.bw2.swapi.service.PlanetService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/planets")
public class PlanetController extends BaseController {

    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping(value = {"", "/{q}"}, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap> index(@PathVariable(required = false) String q,
                                @RequestParam(value = "page", required=false) Integer page) {
        HashMap response = new HashMap<>();

        if(q == null) {

            Page<Planet> planets = planetService.getPlanets(page);
            response.put("data", planets.getContent());
            response.put("count", planets.getTotalElements());

            if(planets.hasNext())
                response.put("next", "/api/v1/planets?page="+(planets.getPageable().getPageNumber()+2));

        } else {

            Optional<Planet> planet = planetService.findById(q);

            if(planet.isPresent()) {
                response.put("data", planet);
            } else {
                response.put("data", planetService.findAllByName(q));
            }
        }

        return ResponseEntity.ok().body(response);
    }


    @PostMapping(value = {""}, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Planet> create( @RequestBody @Valid Planet planet) throws IOException {
        Planet planetCreated = planetService.create(planet);
        return ResponseEntity.ok().body(planetCreated);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handle(@PathVariable String id) {
        planetService.delete(id);
    }
}
