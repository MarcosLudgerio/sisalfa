package br.ufpb.dcx.apps4society.educandoapi.testservices;

import br.ufpb.dcx.apps4society.educapi.EducAPIApplication;
import br.ufpb.dcx.apps4society.educapi.domain.User;
import br.ufpb.dcx.apps4society.educapi.services.UserService;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.DataIntegrityException;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.ObjectNotFoundException;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = EducAPIApplication.class)
@ActiveProfiles("test")
public class TestUserServices {

    @Autowired
    UserService userService;

    @Test // INSERT USER FROM DATABASE
    public void insertUserIntoDatabaseTest() {
        User marcos = new User("Marcos Ludgério", "raimundo.marcos@dcx.ufpb.br", "123345456");
        userService.insert(marcos);
        assertThat(marcos.getId()).isNotNull();
        //marcos.getId());
        assertThat(marcos.getName()).isEqualTo("Marcos Ludgério");
        assertThat(marcos.getEmail()).isEqualTo("raimundo.marcos@dcx.ufpb.br");
        assertThat(marcos.getPassword()).isEqualTo("123345456");
    }

    @Test // FIND USER IN DATABASE
    public void findUserInDatabaseTest() throws ObjectNotFoundException {
        User marcos = new User("Marcos Ludgério", "raimundo.marcos@dcx.ufpb.br", "123345456");
        userService.insert(marcos);
        User userFind = userService.find(marcos.getId());
        assertThat(userFind.getName()).isEqualTo("Marcos Ludgério");
        assertThat(userFind.getEmail()).isEqualTo("raimundo.marcos@dcx.ufpb.br");
        assertThat(userFind.getPassword()).isEqualTo("123345456");
    }

    @Test // FIND USER WHERE NOT IN DATABASE
    public void findUserWhereNotInDatabaseTest() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            userService.find(20L);
        });
    }

    @Test // FIND USER WHERE NOT IN DATABASE
    public void updateUserWhenIdNotInDatabaseTest() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            User u = new User("nome", "email", "senha");
            userService.update(u);
        });
    }

    @Test // DELETE USER WHEN NOT IN DATABASE
    public void deleteeUserWhenIdNotInDatabaseTest() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            userService.delete(2L);
        });
    }

    @Test // DELETE AND FIND USER WHEN NOT IN DATABASE
    public void deleteAndConsultUserFromDatabase() throws ObjectNotFoundException, DataIntegrityException {
        // FIND ALL USERS
        List<User> users = userService.findAll();
        assertThat(users.size()).isEqualTo(1);

        // CREATE NEW USER
        User marcos = new User("Marcos Ludgério", "raimundo.marcos@dcx.ufpb.br", "123345456");
        // INSERT USER IN DATABASE
        userService.insert(marcos);

        // CHECK IF YOU ADDED
        List<User> users2 = userService.findAll();
        assertThat(users2.size()).isEqualTo(2);

        // DELETE USER ADDED
        userService.delete(marcos.getId());
        List<User> users3 = userService.findAll();

        // CHECK IF DELETED WITH SUCESS
        assertThat(users3.size()).isEqualTo(1);
    }

    @Test // UPDATE USER WITH CONSULTS AND ALTER FROM DATABASE
    public void updateUserFromDatabase() throws ObjectNotFoundException {
        // CREATE NEW USER
        User marcos = new User("Marcos Ludgério", "raimundo.marcos@dcx.ufpb.br", "123345456");
        // INSERT USER IN DATABASE
        userService.insert(marcos);

        // FIND USER BY ID
        User findUser = userService.find(marcos.getId());
        assertThat(findUser.getName()).isEqualTo("Marcos Ludgério");

        // CHANGE NAM
        findUser.setName("Raimundo");
        // SAVE NEW OJBECT UPDATED
        userService.insert(findUser);

        marcos = userService.find(findUser.getId());
        assertThat(marcos.getName()).isEqualTo("Raimundo");
    }

    @Test // UPDATE USER USING THE METHOD UPDATE DATA
    public void updateUserUsingTheMethodUpdateData() throws ObjectNotFoundException {
        // CREATE USER
        User marcos = new User("Marcos Ludgério", "raimundo.marcos@dcx.ufpb.br", "123345456");

        // INSERT USER IN DATABASE
        userService.insert(marcos);

        //MODIFIER THE USER
        marcos.setName("Raimundo Marcos");

        //UPDATE DATA
        User updated = userService.update(marcos);
        assertThat(updated.getName()).isEqualTo("Raimundo Marcos");
    }
}
