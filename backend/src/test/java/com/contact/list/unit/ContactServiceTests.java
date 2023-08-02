package com.contact.list.unit;

import com.contact.list.api.request.UpdateContactRequest;
import com.contact.list.entities.Contact;
import com.contact.list.entities.User;
import com.contact.list.exceptions.ContactNotFoundException;
import com.contact.list.exceptions.EmailAlreadyInUseException;
import com.contact.list.mappers.ContactMapperImpl;
import com.contact.list.mappers.UserMapperImpl;
import com.contact.list.repositories.ContactRepository;
import com.contact.list.repositories.UserRepository;
import com.contact.list.services.ContactService;
import com.contact.list.services.UserService;
import com.contact.list.utils.MockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTests {

    private ContactService contactService;

    @Mock
    ContactRepository contactRepository;

    @BeforeEach
    void setup(){
        contactService = new ContactService(
                contactRepository,
                new ContactMapperImpl()
        );
    }

    @Test
    void getSuccessfully(){
        var authenticatedUser = MockData.createAuthenticatedUser(MockData.ALEX);
        var contact = MockData.createContact(MockData.FABIANA);

        when(contactRepository.findByIdAndUserId(1, authenticatedUser.getId()))
                .thenReturn(Optional.of(contact));

        contactService.get(1, authenticatedUser);
    }

    @Test
    void getNotFound(){
        var authenticatedUser = MockData.createAuthenticatedUser(MockData.ALEX);

        when(contactRepository.findByIdAndUserId(1, authenticatedUser.getId()))
                .thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () -> contactService.get(1, authenticatedUser));
    }

    @Test
    void updateSuccessfully(){
        var authenticatedUser = MockData.createAuthenticatedUser(MockData.ALEX);
        var contact = MockData.createContact(MockData.FABIANA);
        var updateContact = new UpdateContactRequest();
        updateContact.setEmail("updated@gmail.com");

        when(contactRepository.findByIdAndUserId(1, authenticatedUser.getId()))
                .thenReturn(Optional.of(contact));

        when(contactRepository.save(contact))
                .thenReturn(contact);

        contactService.update(updateContact, 1L, authenticatedUser);

        Assertions.assertEquals(contact.getEmail(), updateContact.getEmail());
    }

    @Test
    void updateNotFoundContact(){
        var authenticatedUser = MockData.createAuthenticatedUser(MockData.ALEX);
        var updateContact = new UpdateContactRequest();
        updateContact.setEmail("updated@gmail.com");

        when(contactRepository.findByIdAndUserId(1, authenticatedUser.getId()))
                .thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class,
                () -> contactService.update(updateContact, 1L, authenticatedUser)
        );

    }
}
