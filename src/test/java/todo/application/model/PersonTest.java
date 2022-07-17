package todo.application.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    @Test
    void should_Construct_A_Person_Object() {
        //Arrange
        int id = 1;
        String firstName = "Christopher";
        String lastName = "Ojaide";
        String email = "co@provider.com";

        //Act
        Person person = new Person(firstName, lastName, email);
        person.setId(id);

        //Assert
        assertAll(
                () -> assertEquals(id, person.getId()),
                () -> assertEquals(firstName, person.getFirstName()),
                () -> assertEquals(lastName, person.getLastName()),
                () -> assertEquals(email, person.getEmail())
        );
    }

    @Test
    void should_Not_Construct_A_Person_Object_With_Null_Or_Empty_Names_Or_Email() {
        //Arrange
        int id = 1;
        String firstName = "   ";
        String lastName = null;
        String email = "";

        String objectFirstName = null;
        String objectLastName = "";
        String objectEmail = "";

        //Act
        Executable executable = () -> new Person(firstName, lastName, email);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

}