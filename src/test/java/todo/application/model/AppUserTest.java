package todo.application.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {
    @Test
    void should_Construct_An_AppUser_Object() {
        //Arrange
        String username = "Chris";
        String password = "passWord";

        //Act
        AppUser appUser = new AppUser(username, password);

        //Assert
        assertAll(
                () -> assertEquals(username, appUser.getUsername()),
                () -> assertEquals(password, appUser.getPassword())
        );
    }

    @Test
    void should_Not_Construct_An_AppUser_Object_With_Null_Or_Empty_Username_Or_Password() {
        //Arrange
        String username = "user_1";

        //Act
        Executable executable = () -> new AppUser(null, null);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

}