package hu.acsaifz.rms.service;

import hu.acsaifz.rms.dto.AddressDto;
import hu.acsaifz.rms.model.Address;
import hu.acsaifz.rms.model.AddressType;
import hu.acsaifz.rms.model.Person;
import hu.acsaifz.rms.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private PersonService personService;
    @Mock
    private ContactService contactService;

    @InjectMocks
    private AddressService addressService;

    @Test
    void testSaveWithPersonIdAddressDtoAddressType(){
        when(personService.findById(anyLong())).thenReturn(new Person());

        addressService.save(1l,new AddressDto(), AddressType.PERMANENT);

        verify(addressRepository, times(1)).save(any(Address.class));
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    void testSaveWithAddress(){
        addressService.save(new Address());

        verify(addressRepository, times(1)).save(any(Address.class));
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    void testUpdate(){
        Address address = new Address();
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class)))
                .thenReturn(address);

        AddressDto addressDto = new AddressDto(new Address(new Person(),AddressType.PERMANENT, "Magyarország",
                "1111","Budapest","Futrinka utca"));

        Address actual = addressService.update(addressDto);

        assertEquals(addressDto.getCountry(), actual.getCountry());
        assertEquals(addressDto.getPostalCode(), actual.getPostalCode());
        assertEquals(addressDto.getCity(), actual.getCity());
        assertEquals(addressDto.getAddress(), actual.getAddress());

        verify(addressRepository, times(1)).save(any(Address.class));
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    void testDelete(){
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(new Address()));
        when(addressRepository.save(any(Address.class))).thenReturn(new Address());

        doNothing().when(addressRepository).delete(any(Address.class));

        addressService.delete(1);

        verify(addressRepository, times(1)).delete(any(Address.class));
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    void testFindById(){
        Address address = new Address(new Person(),AddressType.PERMANENT, "Magyarország",
                "1111","Budapest","Futrinka utca");
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));

        Address actual = addressService.findById(1);

        assertThat(actual).usingRecursiveComparison().isEqualTo(address);
        verify(addressRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    void testFindByIdWhenNotFound(){
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.findById(1));
        assertEquals("No such address: id = 1", exception.getMessage());
        verify(addressRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(addressRepository);
    }
}