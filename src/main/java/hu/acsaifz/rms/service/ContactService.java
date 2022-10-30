package hu.acsaifz.rms.service;

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
}
