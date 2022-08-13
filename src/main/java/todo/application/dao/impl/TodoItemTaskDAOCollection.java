package todo.application.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import todo.application.dao.TodoItemTaskDAO;
import todo.application.maintenance.SequencerDataSaver;
import todo.application.model.TodoItemTask;
import todo.application.sequencer.TodoItemTaskIdSequencer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static todo.application.maintenance.StaticResources.TODO_ITEM_TASK_FILE;

public class TodoItemTaskDAOCollection implements TodoItemTaskDAO {

    private List<TodoItemTask> todoItemTaskList = new ArrayList<>();


    public void setTodoItemTaskList(List<TodoItemTask> todoItemTaskList) {
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

        SequencerDataSaver.saveSequencerValue();
        saveAsJsonToFile();

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
        return todoItemTaskList;
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
                .filter(t -> t.getAssignee() != null)
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


    //Loads Collection Data From File
    public void loadCollectionData(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            todoItemTaskList = objectMapper.readValue(TODO_ITEM_TASK_FILE, new TypeReference<List<TodoItemTask>>() {});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Saves Collection Data As JSON To File
    public void saveAsJsonToFile(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
            objectWriter.writeValue(TODO_ITEM_TASK_FILE, todoItemTaskList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
