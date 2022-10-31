package hu.acsaifz.rms.service;

import hu.acsaifz.rms.dto.ContactDto;
import hu.acsaifz.rms.model.Address;
import hu.acsaifz.rms.model.Contact;
import hu.acsaifz.rms.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {
    @Mock
    private ContactRepository contactRepository;
    @InjectMocks
    private ContactService contactService;

    private Contact contact;

    @BeforeEach
    void init(){
        contact = new Contact(new Address(), "+3630123456", "mail@mail.com");
    }

    @Test
    void testSave(){
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        final Contact actual = contactService.save(new ContactDto(),new Address());

        assertThat(actual).usingRecursiveComparison().isEqualTo(contact);
        verify(contactRepository, times(1)).save(any(Contact.class));
        verifyNoMoreInteractions(contactRepository);
    }

    @Test
    void testDelete(){
        doNothing().when(contactRepository).delete(any(Contact.class));

        contactService.delete(new Contact());
        verify(contactRepository, times(1)).delete(any(Contact.class));
        verifyNoMoreInteractions(contactRepository);
    }

    @Test
    void testFindById(){
        when(contactRepository.findById(anyLong())).thenReturn(Optional.of(contact));

        Contact actual = contactService.findById(1);

        assertThat(actual).usingRecursiveComparison().isEqualTo(contact);
        verify(contactRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(contactRepository);
    }

    @Test
    void testFindByIdWhenNotFound(){
        when(contactRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> contactService.findById(1));

        assertEquals("No such contact: id = 1", exception.getMessage());
        verify(contactRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(contactRepository);
    }

    @Test
    void testUpdate(){
        when(contactRepository.findById(anyLong())).thenReturn(Optional.of(contact));

        contactService.update(new ContactDto());

        verify(contactRepository, times(1)).save(any(Contact.class));
        verifyNoMoreInteractions(contactRepository);
    }

}