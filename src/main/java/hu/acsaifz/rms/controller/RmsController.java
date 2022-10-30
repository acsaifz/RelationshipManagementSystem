package hu.acsaifz.rms.controller;

import hu.acsaifz.rms.model.Person;
import hu.acsaifz.rms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RmsController {
    private PersonService personService;

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
        return "person";
    }
}
