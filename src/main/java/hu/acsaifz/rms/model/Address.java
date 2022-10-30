package hu.acsaifz.rms.model;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private AddressType addressType;
    private String country;
    private String postalCode;
    private String city;
    private String address;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "contact_id")
    private Contact contact;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Address(){

    }

    public Address(Person person, AddressType addressType, String country, String postalCode, String city, String address){
        this.person = person;
        this.addressType = addressType;
        this.country = country;
        this.postalCode = postalCode;
        this.city = city;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
