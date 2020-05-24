package com.desafio.bw2.swapi;

import com.desafio.bw2.swapi.model.Planet;
import com.desafio.bw2.swapi.service.PlanetService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.util.Optional;

@SpringBootTest
class SwapiApplicationTests {

	@Autowired
	PlanetService planetService;

	@Test
	public void create_planet() throws IOException {
		Planet planet = new Planet();
		planet.setName("teste");
		planet.setLand("teste");
		planet.setClimate("teste");
		Planet planetCreated = planetService.create(planet);

		Assert.assertNotNull(planetCreated);
	}

	@Test
	public void check_if_has_more_than_zero() {
		Page<Planet> planets = planetService.getPlanets(0);

		Assert.assertTrue(planets.hasContent());
	}

	@Test
	public void consult_last_insert_id() {
		Optional<Planet> planet = planetService.getLastInsert();

		Assert.assertTrue(planet.isPresent());
	}

	@Test
	public void delete_last_insert_id() {
		Optional<Planet> planet = planetService.getLastInsert();

		Assert.assertTrue(planet.isPresent());

		planetService.delete(planet.get().getId());

	}
}
