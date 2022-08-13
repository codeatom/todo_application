package todo.application.maintenance;

import todo.application.sequencer.PersonIdSequencer;
import todo.application.sequencer.TodoItemIdSequencer;
import todo.application.sequencer.TodoItemTaskIdSequencer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static todo.application.maintenance.StaticResources.SEQUENCERS_FILE;

public class SequencerDataSaver {

    public static void saveSequencerValue(){
        Properties properties = new Properties();
        properties.setProperty("currentPersonId", String.valueOf(PersonIdSequencer.getCurrentId()));
        properties.setProperty("currentTodoItemId", String.valueOf(TodoItemIdSequencer.getCurrentId()));
        properties.setProperty("currentTodoItemTaskId", String.valueOf(TodoItemTaskIdSequencer.getCurrentId()));

        try(FileWriter writer = new FileWriter(SEQUENCERS_FILE)){
            properties.store(writer, "Latest sequencer values");
        }catch (IOException ex){
            ex.getStackTrace();
        }
    }

}
