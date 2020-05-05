package br.ufpb.dcx.apps4society.educandoapi.testservices;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.ufpb.dcx.apps4society.educapi.EducAPIApplication;
import br.ufpb.dcx.apps4society.educapi.domain.Context;
import br.ufpb.dcx.apps4society.educapi.domain.User;
import br.ufpb.dcx.apps4society.educapi.services.ContextService;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.DataIntegrityException;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.ObjectNotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = EducAPIApplication.class)
@ActiveProfiles("test")
public class TestContextServices {
	
	@Autowired
	ContextService contextService;

	@Test // INSERT CONTEXT IN DATABASE
	public void insertUserIntoDatabaseTest() {
		User criador = new User();
		Context ctxBase = new Context("contexto teste", criador, "urlImagem", "urlSound", "urlVideo");

		contextService.insert(ctxBase);

		Assertions.assertThat(ctxBase.getId()).isNotNull();
	}

	@Test // DELETE CONTEXT FROM THE DATABASE
	public void deleteAndConsultContextFromDatabase() throws ObjectNotFoundException, DataIntegrityException {
		User marcos = new User("Marcos Ludgério", "raimundo.marcos@dcx.ufpb.br", "123345456");

		// FIND ALL CONTEXTS
		List<Context> allContexts = contextService.findAll();
		Assertions.assertThat(allContexts.size()).isEqualTo(2);

		// CREATE NEW CONTEXT
		Context ctxTeste = new Context("contexto teste", marcos, null, null, null);
		// INSERT CONTEXT IN DATABASE
		contextService.insert(ctxTeste);

		// CHECK IF YOU ADDED
		allContexts = contextService.findAll();
		Assertions.assertThat(allContexts.size()).isEqualTo(3);

		// DELETE CONTEXT ADDED
		contextService.delete(ctxTeste.getId());
		allContexts = contextService.findAll();

		// CHECK IF DELETED WITH SUCESS
		Assertions.assertThat(allContexts.size()).isEqualTo(2);
	}

	@Test
	public void updateContextFromDatabase() throws ObjectNotFoundException {
		// CREATE NEW USER TO REGISTER THE CONTEXT
		User marcos = new User("Marcos Ludgério", "raimundo.marcos@dcx.ufpb.br", "123345456");

		// CREATE NEW CONTEXT
		Context ctxTest = new Context("Contexto teste", marcos, null, null, null);

		// INSERT CONTEXT IN DATABASE
		contextService.insert(ctxTest);

		// FIND CONTEXT BY ID
		Context findContext = contextService.find(ctxTest.getId());
		Assertions.assertThat(findContext.getName()).isEqualTo("Contexto teste");

		// CHANGE NAME CONTEXT
		findContext.setName("Contexto com novo nome");
		
		// SAVE NEW OJBECT UPDATED
		contextService.insert(findContext);

		ctxTest = contextService.find(findContext.getId());
		Assertions.assertThat(ctxTest.getName()).isEqualTo("Contexto com novo nome");
	}

}
