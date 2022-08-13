package todo.application.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import todo.application.dao.PersonDAO;
import todo.application.maintenance.SequencerDataSaver;
import todo.application.model.Person;
import todo.application.sequencer.PersonIdSequencer;

import java.util.ArrayList;
import java.util.List;

import static todo.application.maintenance.StaticResources.EMAIL_FILE;
import static todo.application.maintenance.StaticResources.PERSON_FILE;

public class PersonDAOCollection implements PersonDAO {

    private List<Person> personList = new ArrayList<>();
    private List<String> emailList = new ArrayList<>();


    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    @Override
    public Person persist(Person person) {
        if (person == null){
            throw new IllegalArgumentException("Person is null");
        }

        if(emailList.contains(person.getEmail())){
            throw new IllegalArgumentException(person.getEmail() + " is already taken");
        }

        person.setId(PersonIdSequencer.nextId());
        emailList.add(person.getEmail());
        personList.add(person);

        SequencerDataSaver.saveSequencerValue();
        saveAsJsonToFile();

        return person;
    }

    @Override
    public Person findById(int id) {
        if (id == 0) throw new IllegalArgumentException("Id is zero");

        return personList.stream()
                .filter(p -> p.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public Person findByEmail(String email) {
        if (email == null) throw new IllegalArgumentException("Email is null");

        return personList.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Person> findAll() {
        return personList;
    }

    @Override
    public void remove(int id) {
        Person person = findById(id);

        if(person != null){
            emailList.remove(person.getEmail());
            personList.remove(person);
        }
    }


    //Loads Collection Data From File
    public void loadCollectionData(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            personList = objectMapper.readValue(PERSON_FILE, new TypeReference<List<Person>>() {});
            emailList = objectMapper.readValue(EMAIL_FILE, new TypeReference<List<String>>() {});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Saves Collection Data As JSON To File
    public void saveAsJsonToFile(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
            objectWriter.writeValue(PERSON_FILE, personList);
            objectWriter.writeValue(EMAIL_FILE, emailList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}