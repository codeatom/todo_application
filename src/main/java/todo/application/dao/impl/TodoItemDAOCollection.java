package todo.application.dao.impl;

import todo.application.dao.TodoItemDAO;
import todo.application.model.TodoItem;
import todo.application.sequencer.TodoItemIdSequencer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TodoItemDAOCollection implements TodoItemDAO {
    List<TodoItem> todoItemList;

    public TodoItemDAOCollection(List<TodoItem> todoItemList) {
        this.todoItemList = todoItemList;
    }

    @Override
    public TodoItem persist(TodoItem todoItem) {
        if(todoItem == null){
            throw new IllegalArgumentException("todoItem is null");
        }

        if(todoItemList.contains(todoItem)){
            throw new IllegalArgumentException("The todo with title " + todoItem.getTitle() + " is already assigned");
        }

        todoItem.setId(TodoItemIdSequencer.nextId());
        todoItemList.add(todoItem);

        return todoItem;
    }

    @Override
    public TodoItem findById(int id) {
        if (id == 0) throw new IllegalArgumentException("Id is zero");

        return todoItemList.stream()
                .filter(t -> t.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public List<TodoItem> findAll() {
        return new ArrayList<>(todoItemList);
    }

    @Override
    public List<TodoItem> findAllByDoneStatus(boolean done) {
        return todoItemList.stream()
                .filter(t -> t.isDone() == done)
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoItem> findAllByTitleContains(String title) {
        return todoItemList.stream()
                .filter(t -> t.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoItem> findByPersonId(int id) {
        return todoItemList.stream()
                .filter(t -> t.getCreator().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoItem> findByDeadlineBefore(LocalDate localDate) {
        return todoItemList.stream()
                .filter(t -> t.getDeadLine().isBefore(localDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoItem> findByDeadlineAfter(LocalDate localDate) {
        return todoItemList.stream()
                .filter(t -> t.getDeadLine().isAfter(localDate))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(int id) {
        TodoItem todoItem = findById(id);

        if(todoItem != null){
            todoItemList.remove(todoItem);
        }
    }
}

