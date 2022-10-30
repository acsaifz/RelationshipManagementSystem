package hu.acsaifz.rms.service;

import hu.acsaifz.rms.dto.AddressDto;
import hu.acsaifz.rms.model.Address;
import hu.acsaifz.rms.model.AddressType;
import hu.acsaifz.rms.model.Person;
import hu.acsaifz.rms.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private AddressRepository addressRepository;
    private PersonService personService;

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
        Address address = addressRepository.findById(addressDto.getId()).orElseThrow(
                () -> new IllegalArgumentException("No such address: id = " + addressDto.getId())
        );

        address.setCountry(addressDto.getCountry());
        address.setPostalCode(addressDto.getPostalCode());
        address.setCity(addressDto.getCity());
        address.setAddress(addressDto.getAddress());

        return addressRepository.save(address);
    }
}
