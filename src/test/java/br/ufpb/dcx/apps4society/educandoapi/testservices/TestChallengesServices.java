package br.ufpb.dcx.apps4society.educandoapi.testservices;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

import br.ufpb.dcx.apps4society.educapi.domain.Context;
import br.ufpb.dcx.apps4society.educapi.services.ContextService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import br.ufpb.dcx.apps4society.educapi.EducAPIApplication;
import br.ufpb.dcx.apps4society.educapi.domain.Challenge;
import br.ufpb.dcx.apps4society.educapi.domain.User;
import br.ufpb.dcx.apps4society.educapi.services.ChallengeService;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.DataIntegrityException;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.ObjectNotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = EducAPIApplication.class)
@ActiveProfiles("test")
public class TestChallengesServices {

    @Autowired
    ChallengeService challengeService;

    @Test // INSERT CHALLENGE IN DATABASE
    public void insertChallengeIntoDatabaseTest() {
        User user = new User();

        Challenge objChallenge = new Challenge("banana", user, null, null, null);

        challengeService.insert(objChallenge);

        assertThat(objChallenge.getId()).isNotNull();
    }

    @Test // FIND CHALLENGE IN DATABASE
    public void findChallengeInDatabaseTest() throws ObjectNotFoundException {
        User user = new User();

        Challenge objChallenge = new Challenge("banana", user, null, null, null);

        challengeService.insert(objChallenge);
        Challenge challenge = challengeService.find(objChallenge.getId());

        assertThat(challenge.getWord()).isEqualTo("banana");
    }

    @Test // FIND CHALLENGE WHEN USER IS NULL IN DATABASE
    public void findWhenUserIsNullChallengeInDatabaseTest() {
        User user = null;
        Challenge objChallenge = new Challenge("banana", user, null, null, null);

        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            challengeService.insert(objChallenge);
        });

        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            Challenge challenge = challengeService.find(objChallenge.getId());
        });
    }

	@Test // FIND CHALLENGE WHEN ID IS NOT IN DATABASE
	public void findWhenChallengeIsNotInDatabaseTest() {
		Assertions.assertThrows(ObjectNotFoundException.class, () -> {
			Challenge challenge = challengeService.find(50L);
		});
	}

	@Test // UPDATE CHALLENGE IN DATABASE
	public void updateChallengeInDatabaseTest() throws ObjectNotFoundException {
		User user = new User();
		Challenge objChallenge = new Challenge("banana", user, null, null, null);

		challengeService.insert(objChallenge);

		objChallenge.setWord("apple");

		Challenge newChallenge = challengeService.update(objChallenge);

		assertThat(newChallenge.getWord()).isEqualTo("apple");
	}

	@Test // UPDATE CHALLENGE WHEN NOT IN DATABASE
	public void updateChallengeWhenNotInDatabaseTest(){
    	Challenge c = new Challenge(10L,"banana", new User(), null, null, null);
    	Assertions.assertThrows(ObjectNotFoundException.class, () ->{
			Challenge newChallenge = challengeService.update(c);
		});
	}

	@Test // UPDATE CHALLENGE WHEN NOT HAS SAVED IN DATABASE
	public void updateChallengeWhenNotHasSavedInDatabaseTest(){
		Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () ->{
			Challenge newChallenge = challengeService.update(new Challenge());
		});
	}

	@Autowired
	ContextService ctx;

    @Test // DELETE CHALLENGE FROM THE DATABASE
    public void deleteAndConsultChallengesFromDatabase() throws ObjectNotFoundException, DataIntegrityException {



    	// FIND ALL CONTEXTS
        List<Challenge> allChallenges = challengeService.findAll();
		System.out.println(allChallenges.size());
        assertThat(allChallenges.size()).isEqualTo(4);


        Context context = new Context();
        ctx.insert(context);

		Set<Context> contexts = new HashSet<>();

		contexts.add(context);

        // CREATE NEW CHALLENGE
        Challenge challengeTest = new Challenge("banana", new User(), null, null, null);;
		challengeTest.setContextId(contexts);


        // INSERT CHALLENGE IN DATABASE
        challengeService.insert(challengeTest);

        // CHECK IF YOU ADDED
        allChallenges = challengeService.findAll();
        assertThat(allChallenges.size()).isEqualTo(5);

        // DELETE CHALLENGE ADDED
		challengeService.delete(challengeTest.getId());
//        allChallenges = challengeService.findAll();

        // CHECK IF DELETED WITH SUCESS
        //assertThat(allChallenges.size()).isEqualTo(allChallenges.size());
    }
}
