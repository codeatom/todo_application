package todo.application.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.application.dao.TodoItemTaskDAO;
import todo.application.model.Person;
import todo.application.model.TodoItem;
import todo.application.model.TodoItemTask;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoItemTaskDAOCollectionTest {

    TodoItemTaskDAO todoItemTaskDAOCollection;
    TodoItemTask todoItemTask_1;
    TodoItemTask todoItemTask_2;
    TodoItem todoItem_1;
    TodoItem todoItem_2;
    Person person;

    @BeforeEach
    void setup(){
        todoItemTaskDAOCollection = new TodoItemTaskDAOCollection();

        person = new Person("Chris", "Chris",  "chris@serviceProvive.com", null);
        person.setId(1);

        todoItem_1 = new TodoItem("Assignment", "Do Undone assignments", false, person, LocalDate.parse("2022-08-30"));
        todoItem_1.setId(1);

        todoItem_2 = new TodoItem("Project", "Work on side projects", false, person, LocalDate.parse("2022-09-30"));
        todoItem_2.setId(2);

        todoItemTask_1 = new TodoItemTask(todoItem_1, null, true);
        todoItemTaskDAOCollection.persist(todoItemTask_1);

        todoItemTask_2 = new TodoItemTask(todoItem_2, person, false);
        todoItemTaskDAOCollection.persist(todoItemTask_2);
    }

    @Test
    void should_Save_A_TodoItemTask_To_List() {
        //Arrange

        //Act
        //------persist was done in setup------//

        //Assert
        assertAll(
                () -> assertEquals(todoItemTask_1, todoItemTaskDAOCollection.findAll().get(0)),
                () -> assertEquals(todoItemTask_2, todoItemTaskDAOCollection.findAll().get(1))
        );
    }

    @Test
    void should_Find_And_Return_A_TodoItemTask_Through_TodoItem_Id() {
        //Arrange

        //Act
        TodoItemTask todoItemTask = todoItemTask_1;

        //Assert
        assertEquals(todoItemTask, todoItemTaskDAOCollection.findById(todoItemTask.getId()));
    }

    @Test
    void should_Find_And_Return_All_TodoItemTasks_In_TodoItemTask_List() {
        //Arrange

        //Act
        List<TodoItemTask> todoItemTaskList = new ArrayList<>();
        todoItemTaskList.add(todoItemTask_1);
        todoItemTaskList.add(todoItemTask_2);

        //Assert
        assertEquals(todoItemTaskList, todoItemTaskDAOCollection.findAll());
    }

    @Test
    void should_Find_And_Return_All_TodoItemTasks_In_TodoItemTask_List_With_A_Given_Assigned_Status() {
        //Arrange

        //Act
        List<TodoItemTask> todoItemTaskList = new ArrayList<>();

        for(TodoItemTask todoItemTask : todoItemTaskDAOCollection.findAll()){
            if(todoItemTask.isAssigned()){
                todoItemTaskList.add(todoItemTask);
            }
        }

        //Assert
        assertEquals(todoItemTaskList, todoItemTaskDAOCollection.findByAssignedStatus(true));
    }

    @Test
    void should_Find_And_Return_All_TodoItemTasks_In_TodoItemTask_List_Using_A_Given_Person_Id() {
        //Arrange

        //Act
        List<TodoItemTask> todoItemTaskList = new ArrayList<>();
        todoItemTaskList.add(todoItemTask_2);

        //Assert
        assertEquals(todoItemTaskList, todoItemTaskDAOCollection.findByPersonId(person.getId()));
    }

    @Test
    void should_Remove_A_TodoItemTask_From_TodoItemTask_List() {
        //Arrange

        //Act
        List<TodoItemTask> todoItemTaskList = new ArrayList<>();
        todoItemTaskList.add(todoItemTask_1);

        todoItemTaskDAOCollection.remove(todoItemTask_2.getId());

        //Assert
        assertEquals(todoItemTaskList, todoItemTaskDAOCollection.findAll());
    }

}