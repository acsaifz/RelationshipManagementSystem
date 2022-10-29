package hu.acsaifz.rms.model;

import java.time.LocalDate;
import java.util.Map;

public class Person {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Map<AddressType, Address> addresses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Map<AddressType, Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Map<AddressType, Address> addresses) {
        this.addresses = addresses;
    }
}
