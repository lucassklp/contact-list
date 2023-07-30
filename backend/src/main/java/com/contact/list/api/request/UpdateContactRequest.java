package com.contact.list.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateContactRequest {
    private String name;
    private String phone;
    private String email;
}
