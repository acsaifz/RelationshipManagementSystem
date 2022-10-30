package hu.acsaifz.rms.service;

import hu.acsaifz.rms.dto.PersonDto;
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

    public Person save(Person person){
        return personRepository.save(person);
    }

    public Person save(PersonDto personDto){
        return personRepository.save(personDto.createPerson());
    }

    public void update(PersonDto personDto){
        Person person = findById(personDto.getId());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setBirthDate(personDto.getBirthDate());

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
