package com.contact.list.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactResponse {
    private String name;
    private String phone;
    private String email;
}
