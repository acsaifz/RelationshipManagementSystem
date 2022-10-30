package hu.acsaifz.rms.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "person", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "address_type")
    private Map<AddressType, Address> addresses = new EnumMap<>(AddressType.class);

    public Person(){

    }

    public Person(String firstName, String lastName, LocalDate birthDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Person(long id, String firstName, String lastName, LocalDate birthDate){
        this(firstName, lastName, birthDate);
        this.id = id;
    }


    public Address getPermanentAddress(){
        return addresses.get(AddressType.PERMANENT);
    }

    public Address getTemporaryAddress(){
        return addresses.get(AddressType.TEMPORARY);
    }

    public void setPermanentAddress(Address address){
        if(address.getAddressType() != AddressType.PERMANENT){
            throw new IllegalArgumentException("Not permanent address.");
        }

        addresses.put(address.getAddressType(), address);
    }

    public void setTemporaryAddress(Address address){
        if (address.getAddressType() != AddressType.TEMPORARY){
            throw new IllegalArgumentException("Not temporary address.");
        }

        addresses.put(address.getAddressType(), address);
    }

    public boolean hasPermanentAddress(){
        return getPermanentAddress() != null;
    }

    public boolean hasTemporaryAddress(){
        return getTemporaryAddress() != null;
    }

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
