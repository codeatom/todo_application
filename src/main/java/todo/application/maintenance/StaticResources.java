package todo.application.maintenance;

import java.io.File;
import java.nio.file.Paths;

public class StaticResources {
    public static final File SEQUENCERS_FILE = new File("src/main/resources/sequencers.properties");
    public static final File PERSON_FILE = Paths.get("JSON/person.json").toFile();
    public static final File EMAIL_FILE = Paths.get("JSON/email.json").toFile();
    public static final File TODO_ITEM_FILE = Paths.get("JSON/todoItem.json").toFile();
    public static final File TODO_ITEM_TASK_FILE = Paths.get("JSON/todoItemTask.json").toFile();
    public static final File APP_USER_FILE = Paths.get("JSON/appUser.json").toFile();
    public static final File APP_USER_NAME_FILE = Paths.get("JSON/appUserName.json").toFile();
}
