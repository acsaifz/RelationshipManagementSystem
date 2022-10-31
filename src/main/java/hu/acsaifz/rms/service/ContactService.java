package hu.acsaifz.rms.service;

import hu.acsaifz.rms.dto.ContactDto;
import hu.acsaifz.rms.model.Address;
import hu.acsaifz.rms.model.Contact;
import hu.acsaifz.rms.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private ContactRepository contactRepository;


    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void delete(Contact contact){
        contactRepository.delete(contact);
    }

    public Contact save(ContactDto contactDto, Address address){
        return contactRepository.save(contactDto.createContact(address));
    }

    public Contact findById(long id){
        return contactRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No such contact: id = " + id)
        );
    }

    public void update(ContactDto contactDto){
        Contact contact = findById(contactDto.getId());

        contact.setPhone(contactDto.getPhone());
        contact.setEmail(contactDto.getEmail());

        contactRepository.save(contact);
    }
}
