package hu.acsaifz.rms.dto;

import hu.acsaifz.rms.model.Address;
import hu.acsaifz.rms.model.AddressType;
import hu.acsaifz.rms.model.Person;

public class AddressDto {
    private long id;
    private String country;
    private String postalCode;
    private String city;
    private String address;

    public AddressDto() {

    }

    public AddressDto(Address address) {
        this.id = address.getId();
        this.country = address.getCountry();
        this.postalCode = address.getPostalCode();
        this.city = address.getCity();
        this.address = address.getAddress();
    }

    public Address createAddress(Person person, AddressType addressType){
        Address newAddress = new Address(person,addressType, this.country, this.postalCode,this.city, this.address);

        if (addressType == AddressType.PERMANENT){
            person.setPermanentAddress(newAddress);
        } else {
            person.setTemporaryAddress(newAddress);
        }

        return newAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
