package hu.acsaifz.rms.service;

import hu.acsaifz.rms.dto.PersonDto;
import hu.acsaifz.rms.model.Person;
import hu.acsaifz.rms.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private Person person;

    @BeforeEach
    void init(){
        person = new Person("John", "Doe", LocalDate.of(2000,10,15));
    }

    @Test
    void testFindById(){
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        Person actual = personService.findById(1);

        assertThat(actual).usingRecursiveComparison().isEqualTo(person);
        verify(personRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void testFindByIdWhenNotFound(){
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> personService.findById(1));

        assertEquals("No such person: id = 1", exception.getMessage());
        verify(personRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void testFindAll(){
        when(personRepository.findAll()).thenReturn(List.of(new Person(), new Person()));

        assertEquals(2, personService.findAll().size());
        verify(personRepository, times(1)).findAll();
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void testSaveWithPerson(){
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person actual = personService.save(new Person());

        assertThat(actual).usingRecursiveComparison().isEqualTo(person);
        verify(personRepository, times(1)).save(any(Person.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void testSaveWithPersonDto(){
        PersonDto personDto = new PersonDto(person);
        when(personRepository.save(any(Person.class))).thenReturn(personDto.createPerson());

        Person actual = personService.save(personDto);

        assertThat(actual).usingRecursiveComparison().isEqualTo(personDto.createPerson());
        verify(personRepository, times(1)).save(any(Person.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void testUpdate(){
        Person person = new Person();
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDto personDto = new PersonDto(this.person);
        personService.update(personDto);

        assertEquals(personDto.getFirstName(), person.getFirstName());
        assertEquals(personDto.getLastName(), person.getLastName());
        assertEquals(personDto.getBirthDate(), person.getBirthDate());
        verify(personRepository, times(1)).save(any(Person.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void testDelete(){
        doNothing().when(personRepository).deleteById(anyLong());

        personService.delete(1);
        verify(personRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(personRepository);
    }
}