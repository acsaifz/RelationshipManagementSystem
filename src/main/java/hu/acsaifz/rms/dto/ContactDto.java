package hu.acsaifz.rms.dto;

import hu.acsaifz.rms.model.Address;
import hu.acsaifz.rms.model.Contact;

public class ContactDto {
    private long id;
    private String phone;
    private String email;

    public ContactDto() {

    }

    public ContactDto(Contact contact) {
        this.id = contact.getId();
        this.phone = contact.getPhone();
        this.email = contact.getEmail();
    }

    public Contact createContact(Address address){
        return new Contact(address, this.phone, this.email);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
