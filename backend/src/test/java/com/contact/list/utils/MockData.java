package com.contact.list.utils;

import com.contact.list.api.request.AuthenticationRequest;
import com.contact.list.api.request.UserRegisterRequest;
import com.contact.list.configuration.security.AuthenticatedUser;
import com.contact.list.entities.Contact;

public class MockData {
    public static String ALEX = "Alex";
    public static String ANA = "Ana";
    public static String CARLOS = "Carlos";
    public static String FABIANA = "Fabiana";
    private static String PASSWORD = "123456";

    private static String createEmail(String name){
        return name.toLowerCase() + "@teste.com";
    }

    public static UserRegisterRequest createUserRequest(String name){
        var user = new UserRegisterRequest();
        user.setName(name);
        user.setEmail(createEmail(name));
        user.setPassword(PASSWORD);
        return user;
    }

    public static AuthenticationRequest getValidCredential(String name){
        var credential = new AuthenticationRequest();
        credential.setEmail(createEmail(name));
        credential.setPassword(PASSWORD);
        return credential;
    }

    public static Contact createContact(String name){
        var contact = new Contact();
        contact.setEmail(createEmail(name));
        contact.setName(name);
        contact.setPhone("81999999999");
        return contact;
    }

    public static AuthenticatedUser createAuthenticatedUser(String name) {
        return new AuthenticatedUser(1, name, PASSWORD);
    }

}
