package com.contact.list.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactResponse {
    private long id;
    private String name;
    private String phone;
    private String email;
}
