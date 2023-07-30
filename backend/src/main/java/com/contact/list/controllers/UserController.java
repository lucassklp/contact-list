package com.contact.list.controllers;

import com.contact.list.api.request.UserRegisterRequest;
import com.contact.list.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public void register(@RequestBody @Validated UserRegisterRequest userRegister){
        userService.register(userRegister);
    }

}
