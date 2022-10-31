package hu.acsaifz.rms.controller;

import hu.acsaifz.rms.dto.AddressDto;
import hu.acsaifz.rms.dto.ContactDto;
import hu.acsaifz.rms.dto.PersonDto;
import hu.acsaifz.rms.model.Address;
import hu.acsaifz.rms.model.AddressType;
import hu.acsaifz.rms.model.Contact;
import hu.acsaifz.rms.model.Person;
import hu.acsaifz.rms.service.AddressService;
import hu.acsaifz.rms.service.ContactService;
import hu.acsaifz.rms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Controller
public class RmsController {
    private PersonService personService;
    private AddressService addressService;
    private ContactService contactService;

    @Autowired
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = {"/"})
    public String showPersons(Model model){
        List<Person> persons = personService.findAll();
        model.addAttribute("persons", persons);
        return "index";
    }

    @GetMapping(value = {"/person/add"})
    public String showAddPersonForm(Model model){
        model.addAttribute("editPerson", false);
        return "person-add";
    }

    @PostMapping(value = {"/person/add"})
    public String addPerson(PersonDto personDto){
        Person person = personService.save(personDto);
        return "redirect:/person/" + person.getId() + "/edit";
    }

    @GetMapping(value = {"/person/{id}/edit"})
    public String editPerson(@PathVariable long id, Model model){
        Person person = personService.findById(id);
        model.addAttribute("person", person);
        model.addAttribute("editPerson", true);
        return "person-edit";
    }

    @PostMapping(value = {"/person/update"})
    public String updatePerson(PersonDto personDto){
        personService.update(personDto);
        return "redirect:/";
    }

    @PostMapping(value = {"/person/{id}/address/permanent/add"})
    public String addPermanentAddress(AddressDto addressDto, @PathVariable long id){
        addressService.save(id,addressDto, AddressType.PERMANENT);
        return "redirect:/person/" + id + "/edit";
    }

    @PostMapping(value = {"/person/{id}/address/temporary/add"})
    public String addTemporaryAddress(AddressDto addressDto, @PathVariable long id){
        addressService.save(id,addressDto, AddressType.TEMPORARY);
        return "redirect:/person/" + id + "/edit";
    }

    @PostMapping(value = {"/person/address/update"})
    public String updateAddress(AddressDto addressDto){
        Address address = addressService.update(addressDto);
        return "redirect:/person/" + address.getPerson().getId() +
                "/edit?saveAddress=" + address.getAddressType().name().toLowerCase();
    }

    @PostMapping(value = {"/person/{id}/delete"})
    public String deletePerson(@PathVariable long id){
        personService.delete(id);
        return "redirect:/";
    }

    @PostMapping(value = {"/person/address/{id}/delete"})
    public String deleteAddress(@PathVariable long id,
                                @RequestHeader(value = "referer", required = false) String referer){
        System.out.println("-----> Itt lesz majd a törlés");

        addressService.delete(id);
        return "redirect:" + unWrapPathFromUrl(referer);
    }

    private String unWrapPathFromUrl(String url){
        int startIndex = url.indexOf("/",9);
        int endIndex = url.indexOf("?");
        return endIndex < 0 ? url.substring(startIndex) : url.substring(startIndex,endIndex);
    }

    @PostMapping(value = {"/person/address/{id}/contacts/add"})
    public String addContacts(ContactDto contactDto,@PathVariable long id,
            @RequestHeader(value = "referer", required = false) String referer){
        if (isContactDtoBlank(contactDto)){
            return "redirect:" + unWrapPathFromUrl(referer) + "?error=invalidContacts";
        }

        Address address = addressService.findById(id);
        Contact contact = contactService.save(contactDto,address);
        address.setContact(contact);
        addressService.save(address);

        return "redirect:" + unWrapPathFromUrl(referer) + "?success=addContacts";
    }

    @PostMapping(value = {"/person/address/contacts/update"})
    public String updateContacts(ContactDto contactDto,
                                 @RequestHeader(value = "referer", required = false) String referer){
        if (isContactDtoBlank(contactDto)){
            return deleteContacts(contactDto.getId(), referer);
            /*Contact contact = contactService.findById(contactDto.getId());

            Address address = contact.getAddress();
            address.setContact(null);
            addressService.save(address);

            contactService.delete(contact);
            return "redirect:" + unWrapPathFromUrl(referer) + "?success=deleteContacts";*/
        }

        contactService.update(contactDto);

        return "redirect:" + unWrapPathFromUrl(referer) + "?success=saveContacts";
    }

    private boolean isContactDtoBlank(ContactDto contactDto){
        return contactDto.getEmail().isBlank() && contactDto.getPhone().isBlank();
    }

    @PostMapping(value = {"/person/address/contacts/{id}/delete"})
    public String deleteContacts(@PathVariable long id,
                                 @RequestHeader(value = "referer", required = false) String referer){
        Contact contact = contactService.findById(id);

        Address address = contact.getAddress();
        address.setContact(null);
        addressService.save(address);

        contactService.delete(contact);
        return "redirect:" + unWrapPathFromUrl(referer) + "?success=deleteContacts";
    }
}
