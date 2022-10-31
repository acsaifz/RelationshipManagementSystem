package hu.acsaifz.rms.service;

import hu.acsaifz.rms.dto.AddressDto;
import hu.acsaifz.rms.model.Address;
import hu.acsaifz.rms.model.AddressType;
import hu.acsaifz.rms.model.Contact;
import hu.acsaifz.rms.model.Person;
import hu.acsaifz.rms.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AddressService {
    private AddressRepository addressRepository;
    private PersonService personService;
    private ContactService contactService;

    @Autowired
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void save(long personId, AddressDto addressDto, AddressType addressType){
        Person person = personService.findById(personId);
        addressRepository.save(addressDto.createAddress(person, addressType));
    }

    public Address update(AddressDto addressDto){
        Address address = this.findById(addressDto.getId());

        address.setCountry(addressDto.getCountry());
        address.setPostalCode(addressDto.getPostalCode());
        address.setCity(addressDto.getCity());
        address.setAddress(addressDto.getAddress());

        return addressRepository.save(address);
    }

    @Transactional
    public void delete(long id) {
        Address address = this.findById(id);
        Contact contact = address.getContact();

        address.setPerson(null);
        address.setContact(null);

        address = addressRepository.save(address);

        if (contact != null) {
            contactService.delete(contact);
        }

        addressRepository.delete(address);
    }

    public Address findById(long id){
        return addressRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No such address: id = " + id)
        );
    }
}
