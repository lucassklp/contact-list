package com.contact.list.mappers;

import com.contact.list.api.request.CreateContactRequest;
import com.contact.list.api.response.ContactResponse;
import com.contact.list.entities.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ContactMapper {

    public abstract Contact toContact(CreateContactRequest dto);
    public abstract ContactResponse toContactResponse(Contact entity);

}
