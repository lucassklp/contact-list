package com.contact.list.controllers;

import com.contact.list.api.request.CreateContactRequest;
import com.contact.list.api.request.UpdateContactRequest;
import com.contact.list.api.response.ContactResponse;
import com.contact.list.services.AuthenticationService;
import com.contact.list.services.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public void create(@RequestBody @Validated CreateContactRequest contactDto){
        var authenticatedUser = authenticationService.getAuthenticatedUser();
        contactService.create(contactDto, authenticatedUser);
    }

    @GetMapping
    public Page<ContactResponse> getContacts(Pageable page){
        var authenticatedUser = authenticationService.getAuthenticatedUser();
        return contactService.listContacts(page, authenticatedUser);
    }

    @PutMapping("{id}")
    public void update(@PathVariable Long id, @RequestBody UpdateContactRequest updateContactRequest){
        var authenticatedUser = authenticationService.getAuthenticatedUser();
        contactService.update(updateContactRequest, id, authenticatedUser);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        var authenticatedUser = authenticationService.getAuthenticatedUser();
        contactService.delete(id, authenticatedUser);
    }
}
