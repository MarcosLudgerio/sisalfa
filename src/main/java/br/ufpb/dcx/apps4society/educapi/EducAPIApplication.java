package br.ufpb.dcx.apps4society.educapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class EducAPIApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(EducAPIApplication.class, args);	
	}
	
	@GetMapping("/")
    @ResponseBody
	String index() {
      return "Bem vindo, isso é um clone do EducAPI usada apenas para teste!";
    }
	
	@Override
	public void run(String... args) throws Exception {
		
	}

}
