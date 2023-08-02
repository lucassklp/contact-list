package com.contact.list.api;

import com.contact.list.api.response.TokenResponse;
import com.contact.list.utils.MockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EndpointTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createUserWithoutEmail() throws Exception {
        var user = MockData.createUserRequest(MockData.ALEX);
        user.setEmail(null);

        mvc.perform(post("/api/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithIncorrectEmail() throws Exception {
        var user = MockData.createUserRequest(MockData.ALEX);
        user.setEmail("not a valid email");

        mvc.perform(post("/api/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithoutName() throws Exception {
        var user = MockData.createUserRequest(MockData.ALEX);
        user.setName(null);

        mvc.perform(post("/api/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Order(1)
    public void createUserSuccessfully() throws Exception {
        var user = MockData.createUserRequest(MockData.ALEX);

        mvc.perform(post("/api/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void createUserDuplicated() throws Exception {
        var user = MockData.createUserRequest(MockData.ALEX);

        mvc.perform(post("/api/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    public void loginTest() throws Exception {
        var credential = MockData.getValidCredential(MockData.ALEX);
        mvc.perform(post("/api/auth")
                        .content(objectMapper.writeValueAsString(credential))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void loginWithIncorrectPasswordTest() throws Exception {
        var credential = MockData.getValidCredential(MockData.ALEX);
        credential.setPassword("incorrect-password");

        mvc.perform(post("/api/auth")
                        .content(objectMapper.writeValueAsString(credential))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @Order(5)
    public void createContactSuccessfully() throws Exception {
        var token = getAccessToken(MockData.ALEX);
        var contact = MockData.createContact(MockData.ANA);

        mvc.perform(post("/api/contacts")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(contact))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    public void createContactUnauthenticated() throws Exception {
        var contact = MockData.createContact(MockData.ANA);

        mvc.perform(post("/api/contacts")
                        .content(objectMapper.writeValueAsString(contact))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @Order(7)
    public void getContactRegistered() throws Exception {
        var token = getAccessToken(MockData.ALEX);
        var contact = MockData.createContact(MockData.ANA);

        mvc.perform(get("/api/contacts/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(contact))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(contact.getName())))
                .andExpect(jsonPath("$.email", is(contact.getEmail())))
                .andExpect(jsonPath("$.phone", is(contact.getPhone())));
    }

    @Test
    @Order(9)
    public void getContacts() throws Exception {
        var token = getAccessToken(MockData.ALEX);
        var contact = MockData.createContact(MockData.ANA);

        mvc.perform(get("/api/contacts")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is(contact.getName())))
                .andExpect(jsonPath("$.content[0].email", is(contact.getEmail())))
                .andExpect(jsonPath("$.content[0].phone", is(contact.getPhone())));
    }

    @Test
    @Order(10)
    public void notAllowToAccessPrivateData() throws Exception {
        //Register Carlos
        //Assign Fabiana to Carlo's contact
        //Login as Alex
        //Alex try to get Fabiana's contact (Not allowed)

        var user = MockData.createUserRequest(MockData.CARLOS);

        mvc.perform(post("/api/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var carlosToken = getAccessToken(MockData.CARLOS);

        var fabianaContact = MockData.createContact(MockData.FABIANA);

        mvc.perform(post("/api/contacts")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + carlosToken)
                        .content(objectMapper.writeValueAsString(fabianaContact))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        var token = getAccessToken(MockData.ALEX);

        mvc.perform(get("/api/contacts/2")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @SneakyThrows
    private String getAccessToken(String name){
        var credential = MockData.getValidCredential(name);

        var response = mvc.perform(post("/api/auth")
                        .content(objectMapper.writeValueAsString(credential))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var tokenResponse = objectMapper.readValue(response, TokenResponse.class);

        return tokenResponse.getToken();
    }
}
