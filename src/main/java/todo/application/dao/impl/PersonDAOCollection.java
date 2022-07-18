package todo.application.dao.impl;

import todo.application.dao.PersonDAO;
import todo.application.model.Person;
import todo.application.sequencer.PersonIdSequencer;

import java.util.ArrayList;
import java.util.List;

public class PersonDAOCollection implements PersonDAO {
    List<Person> personList;
    List<String> emailList;

    public PersonDAOCollection() {
        this.personList = new ArrayList<>();
        this.emailList = new ArrayList<>();
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

        return null;
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
        return new ArrayList<>(personList);
    }

    @Override
    public void remove(int id) {
        Person person = findById(id);

        if(person != null){
            emailList.remove(person.getEmail());
            personList.remove(person);
        }
    }
}
