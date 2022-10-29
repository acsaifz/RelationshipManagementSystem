package hu.acsaifz.rms.service;

import hu.acsaifz.rms.model.Person;
import hu.acsaifz.rms.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private PersonRepository personRepository;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void save(Person person){
        personRepository.save(person);
    }

    public List<Person> findAll(){
        return (List<Person>) personRepository.findAll();
    }

    public Person findById(long id){
        return personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such person: id = " + id));
    }

    public void delete(Person person){
        personRepository.delete(person);
    }
}
