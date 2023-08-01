package com.contact.list.services;


import com.contact.list.api.request.CreateContactRequest;
import com.contact.list.api.request.UpdateContactRequest;
import com.contact.list.api.response.ContactResponse;
import com.contact.list.entities.Contact;
import com.contact.list.entities.User;
import com.contact.list.exceptions.ContactNotFoundException;
import com.contact.list.mappers.ContactMapper;
import com.contact.list.repositories.ContactRepository;
import com.contact.list.configuration.security.AuthenticatedUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    public Page<Contact> findContactsFromUser(Pageable pageable, AuthenticatedUser user){
        return contactRepository.findByUserId(pageable, user.getId());
    }

    public ContactResponse get(long id, AuthenticatedUser authenticatedUser){
        return contactRepository.findByIdAndUserId(id, authenticatedUser.getId())
                .map(contactMapper::toContactResponse)
                .orElseThrow(ContactNotFoundException::new);
    }

    public void create(CreateContactRequest contactDto, AuthenticatedUser authenticatedUser) {
        var contact = contactMapper.toContact(contactDto);
        var user = new User();
        user.setId(authenticatedUser.getId());
        contact.setUser(user);
        contactRepository.save(contact);
    }

    public void update(UpdateContactRequest contactDto, Long contactId, AuthenticatedUser authenticatedUser) {
        var contact = contactRepository.findByIdAndUserId(contactId, authenticatedUser.getId()).orElseThrow();
        if(contactDto.getName() != null){
            contact.setName(contactDto.getName());
        }

        if(contactDto.getEmail() != null){
            contact.setEmail(contactDto.getEmail());
        }

        if(contactDto.getPhone() != null){
            contact.setPhone(contactDto.getPhone());
        }

        contactRepository.save(contact);
    }

    @Transactional
    public void delete(long id, AuthenticatedUser authenticatedUser) {
        contactRepository.deleteByIdAndUserId(id, authenticatedUser.getId());
    }

    public Page<ContactResponse> listContacts(Pageable page, AuthenticatedUser authenticatedUser) {
        return contactRepository.findByUserId(page, authenticatedUser.getId())
                .map(contactMapper::toContactResponse);
    }
}
