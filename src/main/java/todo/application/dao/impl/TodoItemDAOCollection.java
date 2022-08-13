package todo.application.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import todo.application.dao.TodoItemDAO;
import todo.application.maintenance.SequencerDataSaver;
import todo.application.model.TodoItem;
import todo.application.sequencer.TodoItemIdSequencer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static todo.application.maintenance.StaticResources.TODO_ITEM_FILE;

public class TodoItemDAOCollection implements TodoItemDAO {

    private List<TodoItem> todoItemList = new ArrayList<>();


    public void setTodoItemList(List<TodoItem> todoItemList) {
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

        SequencerDataSaver.saveSequencerValue();
        saveAsJsonToFile();

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
        return todoItemList;
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
                .filter(t -> t.getCreator() != null)
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


    //Loads Collection Data From File
    public void loadCollectionData(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            todoItemList = objectMapper.readValue(TODO_ITEM_FILE, new TypeReference<List<TodoItem>>() {});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Saves Collection Data As JSON To File
    public void saveAsJsonToFile(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
            objectWriter.writeValue(TODO_ITEM_FILE, todoItemList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
