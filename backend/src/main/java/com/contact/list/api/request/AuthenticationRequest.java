package com.contact.list.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticationRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
