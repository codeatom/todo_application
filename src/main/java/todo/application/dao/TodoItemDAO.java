package todo.application.dao;

import todo.application.model.TodoItem;
import java.time.LocalDate;
import java.util.List;

public interface TodoItemDAO {

    TodoItem persist(TodoItem todoItem);

    TodoItem findById(int id);

    List<TodoItem> findAll();

    List<TodoItem> findAllByDoneStatus(boolean done);

    List<TodoItem> findAllByTitleContains(String title);

    List<TodoItem> findByPersonId(int id);

    List<TodoItem> findByDeadlineBefore(LocalDate localDate);

    List<TodoItem> findByDeadlineAfter(LocalDate localDate);

    void remove(int id);

    void loadCollectionData();

    void saveAsJsonToFile();

}
