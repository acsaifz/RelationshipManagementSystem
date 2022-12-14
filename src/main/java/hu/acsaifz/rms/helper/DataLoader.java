package hu.acsaifz.rms.helper;

import hu.acsaifz.rms.model.Address;
import hu.acsaifz.rms.model.AddressType;
import hu.acsaifz.rms.model.Contact;
import hu.acsaifz.rms.model.Person;
import hu.acsaifz.rms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements ApplicationRunner {
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        generatePerson();
    }

    private void generatePerson() {
        Person person = new Person("John", "Doe", LocalDate.of(1988, 11, 9));

        Address permanent = new Address(person, AddressType.PERMANENT, "Magyarország", "2243", "Kóka", "Szőlő utca 20.");
        person.setPermanentAddress(permanent);

        Contact permanentContact = new Contact(permanent, "+36 30 123 4567", "john.doe@gmail.com");
        permanent.setContact(permanentContact);

        Address temporary = new Address(person, AddressType.TEMPORARY, "Magyarország", "1111", "Budapest", "Futrinka utca 1.");
        person.setTemporaryAddress(temporary);

        Contact temporaryContact = new Contact(temporary, "+36 20 765 4321", "john.doe2@gmail.com");
        temporary.setContact(temporaryContact);

        personService.save(person);

        Person person2 = new Person("Jane", "Doe", LocalDate.of(1990, 1,25));

        Address permanent2 = new Address(person2, AddressType.PERMANENT, "Magyarország", "3214", "Sülysáp", "Vasút sor 19.");
        person2.setPermanentAddress(permanent2);

        Contact permanentContact2 = new Contact(permanent2, "+36 30 303 0303","jane.doe@gmail.com");
        permanent2.setContact(permanentContact2);

        personService.save(person2);

        Person person3 = new Person("Lajos", "Skywalker", LocalDate.of(1975,4,1));

        Address permanent3 = new Address(person3, AddressType.PERMANENT, "Magyarország", "5555", "Lajosmizse", "Fürdő utca 6.");
        person3.setPermanentAddress(permanent3);

        Contact permanentContact3 = new Contact(permanent3, "+36 70 777 7777", null);
        permanent3.setContact(permanentContact3);

        Address temporary3 = new Address(person3, AddressType.TEMPORARY, "Magyarország", "6485", "Eger", "Vár köz 4.");
        person3.setTemporaryAddress(temporary3);

        personService.save(person3);
    }
}
