package todo.application.dao.impl;

import todo.application.dao.TodoItemTaskDAO;
import todo.application.model.TodoItemTask;
import todo.application.sequencer.TodoItemTaskIdSequencer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TodoItemTaskDAOCollection implements TodoItemTaskDAO {
    List<TodoItemTask> todoItemTaskList;

    public TodoItemTaskDAOCollection(List<TodoItemTask> todoItemTaskList) {
        this.todoItemTaskList = todoItemTaskList;
    }

    @Override
    public TodoItemTask persist(TodoItemTask todoItemTask) {
        if(todoItemTask == null){
            throw new IllegalArgumentException("todoItemTask is null");
        }

        if(todoItemTaskList.contains(todoItemTask)){
            throw new IllegalArgumentException("Todo item task already exist");
        }

        todoItemTask.setId(TodoItemTaskIdSequencer.nextId());
        todoItemTaskList.add(todoItemTask);

        return todoItemTask;
    }

    @Override
    public TodoItemTask findById(int id) {
        if (id == 0) throw new IllegalArgumentException("Id is zero");

        return todoItemTaskList.stream()
                .filter(t -> t.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public List<TodoItemTask> findAll() {
        return new ArrayList<>(todoItemTaskList);
    }

    @Override
    public List<TodoItemTask> findByAssignedStatus(boolean status) {
        return todoItemTaskList.stream()
                .filter(t -> t.isAssigned() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoItemTask> findByPersonId(int id) {
        return todoItemTaskList.stream()
                .filter(t -> t.getAssignee().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(int id) {
        TodoItemTask todoItemTask = findById(id);

        if(todoItemTask != null){
            todoItemTaskList.remove(todoItemTask);
        }
    }
}
