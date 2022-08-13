package todo.application;

import todo.application.dao.AppUserDAO;
import todo.application.dao.PersonDAO;
import todo.application.dao.TodoItemDAO;
import todo.application.dao.TodoItemTaskDAO;
import todo.application.dao.impl.AppUserDAOCollection;
import todo.application.dao.impl.PersonDAOCollection;
import todo.application.dao.impl.TodoItemDAOCollection;
import todo.application.dao.impl.TodoItemTaskDAOCollection;
import todo.application.maintenance.SequencerDataLoader;
import todo.application.model.*;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        SequencerDataLoader.loadLatestSequencerValues();

        AppUserDAO appUserDAOCollection = new AppUserDAOCollection();
        appUserDAOCollection.loadCollectionData();

        PersonDAO personDAOCollection = new PersonDAOCollection();
        personDAOCollection.loadCollectionData();

        TodoItemDAO todoItemDAOCollection = new TodoItemDAOCollection();
        todoItemDAOCollection.loadCollectionData();

        TodoItemTaskDAO todoItemTaskDAOCollection = new TodoItemTaskDAOCollection();
        todoItemTaskDAOCollection.loadCollectionData();



        //--------------------Uncomment to demonstrate object creation--------------------//

//        Person person_1 = new Person("Chris", "Chris",  "chris@serviceProvive.com", null);
//        personDAOCollection.persist(person_1);
//
//        AppUser appUser_1 = new AppUser("Chris", "chris123", AppRole.APP_USER);
//        appUserDAOCollection.persist(appUser_1);
//
//        TodoItem todoItem_1 = new TodoItem("Assignment", "Do Undone assignments", false, person_1, LocalDate.parse("2022-08-30"));
//        todoItemDAOCollection.persist(todoItem_1);
//        TodoItem todoItem_2 = new TodoItem("Project", "Work on side projects", false, person_1, LocalDate.parse("2022-09-30"));
//        todoItemDAOCollection.persist(todoItem_2);
//
//        TodoItemTask todoItemTask_1 = new TodoItemTask(todoItem_1, null, false);
//        todoItemTask_1.setAssignee(person_1);
//        todoItemTask_1.setAssigned(person_1);
//        todoItemTaskDAOCollection.persist(todoItemTask_1);
//
//        TodoItemTask todoItemTask_2 = new TodoItemTask(todoItem_2, null, false);
//        todoItemTask_2.setAssignee(person_1);
//        todoItemTask_2.setAssigned(person_1);
//        todoItemTaskDAOCollection.persist(todoItemTask_2);



        for(Person person : personDAOCollection.findAll()){
            System.out.println(person);
        }
        System.out.println("\n");

        for(AppUser appUser : appUserDAOCollection.findAll()){
            System.out.println(appUser);
        }
        System.out.println("\n");

        for(TodoItem todoItem : todoItemDAOCollection.findAll()){
            System.out.println(todoItem);
        }
        System.out.println("\n");

        for(TodoItemTask todoItemTask : todoItemTaskDAOCollection.findAll()){
            System.out.println(todoItemTask);
        }
        System.out.println("\n");

    }
}