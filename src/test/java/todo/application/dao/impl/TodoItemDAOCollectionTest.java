package todo.application.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.application.dao.TodoItemDAO;
import todo.application.model.Person;
import todo.application.model.TodoItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoItemDAOCollectionTest {

    TodoItemDAO todoItemDAOCollection;
    TodoItem todoItem_1;
    TodoItem todoItem_2;
    Person person;

    @BeforeEach
    void setup(){
        todoItemDAOCollection = new TodoItemDAOCollection();

        person = new Person("Chris", "Chris",  "chris@serviceProvive.com", null);
        person.setId(1);

        todoItem_1 = new TodoItem("Assignment", "Do Undone assignments", true, null, LocalDate.parse("2022-08-30"));
        todoItem_2 = new TodoItem("Project", "Work on side projects", false, null, LocalDate.parse("2022-09-30"));

        todoItem_1.setCreator(person);

        todoItemDAOCollection.persist(todoItem_1);
        todoItemDAOCollection.persist(todoItem_2);
    }

    @Test
    void should_Save_A_TodoItem_To_List() {
        //Arrange

        //Act
        //------persist was done in setup------//

        //Assert
        assertAll(
                () -> assertEquals(todoItem_1, todoItemDAOCollection.findAll().get(0)),
                () -> assertEquals(todoItem_2, todoItemDAOCollection.findAll().get(1))
        );
    }

    @Test
    void should_Find_And_Return_A_TodoItem_Through_TodoItem_Id() {
        //Arrange

        //Act
        TodoItem todoItem = todoItem_1;

        //Assert
        assertEquals(todoItem, todoItemDAOCollection.findById(todoItem.getId()));
    }

    @Test
    void should_Find_And_Return_All_TodoItems_In_TodoItem_List() {
        //Arrange

        //Act
        List<TodoItem> todoItemList = new ArrayList<>();
        todoItemList.add(todoItem_1);
        todoItemList.add(todoItem_2);

        //Assert
        assertEquals(todoItemList, todoItemDAOCollection.findAll());
    }

    @Test
    void should_Find_And_Return_All_TodoItems_In_TodoItem_List_With_A_Given_Done_Status() {
        //Arrange

        //Act
        List<TodoItem> todoItemList = new ArrayList<>();
        for(TodoItem todoItem : todoItemDAOCollection.findAll()){
            if(todoItem.isDone()){
                todoItemList.add(todoItem);
            }
        }

        //Assert
        assertEquals(todoItemList, todoItemDAOCollection.findAllByDoneStatus(true));
    }

    @Test
    void should_Find_And_Return_All_TodoItems_In_TodoItem_List_Using_A_Given_Title_String() {
        //Arrange

        //Act
        List<TodoItem> todoItemList = new ArrayList<>();
        todoItemList.add(todoItem_1);

        //Assert
        assertEquals(todoItemList, todoItemDAOCollection.findAllByTitleContains("Assignment"));
    }

    @Test
    void should_Find_And_Return_All_TodoItems_In_TodoItem_List_Using_A_Given_Person_Id() {
        //Arrange

        //Act
        List<TodoItem> todoItemList = new ArrayList<>();
        todoItemList.add(todoItem_1);

        //Assert
        assertEquals(todoItemList, todoItemDAOCollection.findByPersonId(1));
    }

    @Test
    void should_Find_And_Return_All_TodoItems_In_TodoItem_List_Using_A_Given_Deadline() {
        //Arrange

        //Act
        List<TodoItem> todoItemList = new ArrayList<>();

        //Assert
        assertEquals(todoItemList, todoItemDAOCollection.findByDeadlineBefore(LocalDate.parse("2022-08-10")));
    }

    @Test
    void should_Find_And_Return_All_TodoItems_In_TodoItem_List_Using_A_Given_Past_Deadline() {
        //Arrange

        //Act
        List<TodoItem> todoItemList = new ArrayList<>();
        todoItemList.add(todoItem_1);
        todoItemList.add(todoItem_2);

        //Assert
        assertEquals(todoItemList, todoItemDAOCollection.findByDeadlineAfter(LocalDate.parse("2022-08-10")));
    }

    @Test
    void should_Remove_A_TodoItem_From_TodoItem_List() {
        //Arrange

        //Act
        List<TodoItem> todoItemList = new ArrayList<>();
        todoItemList.add(todoItem_1);

        todoItemDAOCollection.remove(todoItem_2.getId());

        //Assert
        assertEquals(todoItemList, todoItemDAOCollection.findAll());
    }

}