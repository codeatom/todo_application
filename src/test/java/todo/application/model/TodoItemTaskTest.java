package todo.application.model;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

class TodoItemTaskTest {
    @Test
    void should_Construct_A_TodoItemTask_Object() {
        //Arrange
        int id = 1;
        LocalDate todoDeadline = LocalDate.now();
        TodoItem todoItem = new TodoItem("add test to assignment", "add test to array and string assignment", false, null, todoDeadline);
        todoItem.setId(id);

        //Act
        TodoItemTask todoItemTask = new TodoItemTask(todoItem, null, false);
        todoItemTask.setId(id);

        //Assert
        assertAll(
                () -> assertEquals(id, todoItemTask.getId()),
                () -> assertEquals(todoItem, todoItemTask.getTodoItem())
        );
    }

    @Test
    void should_Not_Construct_A_TodoItemTask_Object_With_Null_TodoItem() {
        //Arrange
        int id = 1;
        LocalDate todoDeadline = LocalDate.now();
        TodoItem todoItem = null;

        //Act
        Executable executable = () -> new TodoItemTask(todoItem, null, false);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

}