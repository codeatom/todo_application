package todo.application.maintenance;

import todo.application.sequencer.PersonIdSequencer;
import todo.application.sequencer.TodoItemIdSequencer;
import todo.application.sequencer.TodoItemTaskIdSequencer;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static todo.application.maintenance.StaticResources.SEQUENCERS_FILE;

public class SequencerDataLoader {

    public static void loadLatestSequencerValues(){
        Properties properties = new Properties();

        try(FileReader reader = new FileReader(SEQUENCERS_FILE)){
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String currentPersonId = properties.getProperty("currentPersonId");
        String currentTodoItemId = properties.getProperty("currentTodoItemId");
        String currentTodoItemTaskId = properties.getProperty("currentTodoItemTaskId");

        PersonIdSequencer.setCurrentId(Integer.parseInt(currentPersonId));
        TodoItemIdSequencer.setCurrentId(Integer.parseInt(currentTodoItemId));
        TodoItemTaskIdSequencer.setCurrentId(Integer.parseInt(currentTodoItemTaskId));
    }
}
