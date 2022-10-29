package hu.acsaifz.rms.service;

import hu.acsaifz.rms.model.Person;
import hu.acsaifz.rms.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
