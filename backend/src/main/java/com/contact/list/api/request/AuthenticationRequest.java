package com.contact.list.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthenticationRequest {

    @Email
    private String email;

    @NotEmpty
    private String password;
}
