package todo.application.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.application.dao.PersonDAO;
import todo.application.model.Person;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonDAOCollectionTest {

    PersonDAO personDAOCollection;
    Person person_1;
    Person person_2;

    @BeforeEach
    void setup(){
        personDAOCollection = new PersonDAOCollection();

        person_1 = new Person("Chris", "Lucky", "ch@provider.com", null);
        person_2 = new Person("Test", "Testing", "te@provider.com", null);

        personDAOCollection.persist(person_1);
        personDAOCollection.persist(person_2);
    }

    @Test
    void should_Save_A_Person_To_List() {
        //Arrange

        //Act
         //------persist was done in setup------//

        //Assert
        assertAll(
                () -> assertEquals(person_1, personDAOCollection.findAll().get(0)),
                () -> assertEquals(person_2, personDAOCollection.findAll().get(1))
        );
    }

    @Test
    void should_Find_And_Return_A_Person_Through_Person_Id() {
        //Arrange

        //Act
        Person person = person_1;

        //Assert
        assertEquals(person, personDAOCollection.findById(person.getId()));
    }

    @Test
    void should_Find_And_Return_A_Person_Through_Person_Email() {
        //Arrange

        //Act
        Person person = person_1;

        //Assert
        assertEquals(person, personDAOCollection.findByEmail(person.getEmail()));
    }

    @Test
    void should_Find_And_Return_All_Persons_In_Person_List() {
        //Arrange

        //Act
        List<Person> personList = new ArrayList<>();
        personList.add(person_1);
        personList.add(person_2);

        //Assert
        assertEquals(personList, personDAOCollection.findAll());
    }

    @Test
    void should_Remove_A_Person_From_Person_List() {
        //Arrange

        //Act
        List<Person> personList = new ArrayList<>();
        personList.add(person_1);

        personDAOCollection.remove(person_2.getId());

        //Assert
        assertEquals(personList, personDAOCollection.findAll());
    }

}