package br.ufpb.dcx.apps4society.educapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class EducAPIApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(EducAPIApplication.class, args);	
	}
	
	@RequestMapping("/")
    @ResponseBody
	String index() {
      return "Welcome to EducAPI! | VERSION: 0.0.1-SNAPSHOT";
    }
	
	@Override
	public void run(String... args) throws Exception {
		
	}

}
