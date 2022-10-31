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
    private AddressService addressService;

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void delete(Contact contact){
        contactRepository.delete(contact);
    }

    public Contact save(ContactDto contactDto, long addressId){
        Address address = addressService.findById(addressId);
        return contactRepository.save(contactDto.createContact(address));
    }
}
