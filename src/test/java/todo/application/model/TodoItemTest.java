package todo.application.model;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

class TodoItemTest {
    @Test
    void should_Construct_A_TodoItem_Object() {
        //Arrange
        int id = 1;
        String title = "Do assignment";
        String taskDescription = "Do array and string assignments";
        LocalDate localDate = LocalDate.now().plusDays(7);

        //Act
        TodoItem todoItem = new TodoItem(title, taskDescription, localDate);
        todoItem.setId(id);

        //Assert
        assertAll(
                () -> assertEquals(id, todoItem.getId()),
                () -> assertEquals(title, todoItem.getTitle()),
                () -> assertEquals(taskDescription, todoItem.getTaskDescription()),
                () -> assertEquals(localDate, todoItem.getDeadLine())
        );
    }

    @Test
    void should_Not_Construct_A_TodoItem_Object_With_Null_Or_Empty_Title_Or_DeadLine() {
        //Arrange
        int id = 1;
        String title = "";
        String taskDescription = "Do array and string assignments";
        LocalDate localDate = null;

        //Act
        Executable executable = () -> new TodoItem(title, taskDescription, localDate);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

}