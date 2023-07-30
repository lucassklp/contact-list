package com.contact.list.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateContactRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    @Email
    private String email;
}
