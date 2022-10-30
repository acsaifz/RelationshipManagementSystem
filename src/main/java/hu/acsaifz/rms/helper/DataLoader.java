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
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthDate(LocalDate.of(1988, 5, 17));

        Address permanent = new Address();
        permanent.setPerson(person);
        permanent.setAddressType(AddressType.PERMANENT);
        permanent.setCountry("Magyarország");
        permanent.setPostalCode("2243");
        permanent.setCity("Kóka");
        permanent.setAddress("Szőlő utca 20.");
        person.getAddresses().put(permanent.getAddressType(), permanent);

        Contact contact = new Contact();
        contact.setAddress(permanent);
        contact.setPhone("+36301234567");
        contact.setEmail("john.doe@gmail.com");
        permanent.setContact(contact);

        Address temporary = new Address();
        temporary.setPerson(person);
        temporary.setAddressType(AddressType.TEMPORARY);
        temporary.setCountry("Magyarország");
        temporary.setPostalCode("1111");
        temporary.setCity("Budapest");
        temporary.setAddress("Futrinka utca 1");
        person.getAddresses().put(temporary.getAddressType(), temporary);

        Contact contact2 = new Contact();
        contact2.setAddress(temporary);
        contact2.setPhone("+36203213213");
        contact2.setEmail("john.doe2@gmail.com");
        temporary.setContact(contact2);

        personService.save(person);
    }
}
