package hu.acsaifz.rms.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    Person person;
    Address permanent;
    Address temporary;

    @BeforeEach
    void init(){
        person = new Person();

        permanent = new Address();
        permanent.setCountry("Magyarország");
        permanent.setPostalCode("1234");
        permanent.setCity("Budapest");
        permanent.setAddress("Futrinka utca 1.");
        permanent.setAddressType(AddressType.PERMANENT);


        temporary = new Address();
        temporary.setCountry("Magyarország");
        temporary.setPostalCode("4321");
        temporary.setCity("Győr");
        temporary.setAddress("Alsórakpart utca 15.");
        temporary.setAddressType(AddressType.TEMPORARY);
    }


    @Test
    void testSetPermanentAddress(){
        person.setPermanentAddress(permanent);

        assertTrue(person.hasPermanentAddress());
        assertEquals("1234", person.getPermanentAddress().getPostalCode());
    }

    @Test
    void testSetPermanentAddressWhenNotValid(){
        Exception exception = assertThrows(IllegalArgumentException.class,() -> person.setPermanentAddress(temporary));

        assertFalse(person.hasPermanentAddress());
        assertEquals("Not permanent address.", exception.getMessage());
    }

    @Test
    void testSetTemporaryAddress(){
        person.setTemporaryAddress(temporary);

        assertTrue(person.hasTemporaryAddress());
        assertEquals("4321", person.getTemporaryAddress().getPostalCode());
    }

    @Test
    void testSetTemporaryAddressWhenNotValid(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> person.setTemporaryAddress(permanent));

        assertFalse(person.hasTemporaryAddress());
        assertEquals("Not temporary address.", exception.getMessage());
    }
}