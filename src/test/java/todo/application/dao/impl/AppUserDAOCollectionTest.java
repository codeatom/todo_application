package todo.application.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.application.dao.AppUserDAO;
import todo.application.model.AppRole;
import todo.application.model.AppUser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppUserDAOCollectionTest {

    AppUserDAO appUserDAOCollection;
    AppUser appUser_1;
    AppUser appUser_2;

    @BeforeEach
    void setup(){
        appUserDAOCollection = new AppUserDAOCollection();

        appUser_1 = new AppUser("Chris", "chris123", AppRole.APP_USER);
        appUser_2 = new AppUser("Lucky", "lucky123", AppRole.APP_USER);

        appUserDAOCollection.persist(appUser_1);
        appUserDAOCollection.persist(appUser_2);
    }

    @Test
    void should_Save_An_AppUser_To_List() {
        //Arrange

        //Act
        //------persist was done in setup------//

        //Assert
        assertAll(
                () -> assertEquals(appUser_1, appUserDAOCollection.findAll().get(0)),
                () -> assertEquals(appUser_1, appUserDAOCollection.findAll().get(0))
        );
    }

    @Test
    void should_Find_And_Return_An_AppUser() {
        //Arrange

        //Act
        AppUser appUser = appUser_1;

        //Assert
        assertEquals(appUser, appUserDAOCollection.findByUsername(appUser_1.getUsername()));
    }

    @Test
    void should_Find_And_Return_All_AppUser() {
        //Arrange

        //Act
        List<AppUser> appUserList = new ArrayList<>();
        appUserList.add(appUser_1);
        appUserList.add(appUser_2);

        //Assert
        assertEquals(appUserList, appUserDAOCollection.findAll());
    }

    @Test
    void should_Remove_An_AppUser_From_AppUser_List() {
        //Arrange

        //Act
        List<AppUser> appUserList = new ArrayList<>();
        appUserList.add(appUser_2);

        appUserDAOCollection.remove(appUser_1.getUsername());

        //Assert
        assertEquals(appUserList, appUserDAOCollection.findAll());
    }

}