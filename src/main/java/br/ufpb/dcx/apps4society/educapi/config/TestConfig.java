package br.ufpb.dcx.apps4society.educapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.ufpb.dcx.apps4society.educapi.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() {
		dbService.instantiateDatabase();
		return true;
	}
}
